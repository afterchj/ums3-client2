package com.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cache.MemcachedObjectType;
import com.cache.XMemcachedClient;
import com.dao.MemberDao;
import com.mapper.BeanMapper;
import com.model.dd.OfferFactory;
import com.model.member.Member;
import com.service.CountService;
import com.service.MemberService;
import com.service.WorkService;
import com.tpadsz.exception.MemcachedNotResponsedException;
import com.tpadsz.exception.NotExecutedDbException;
import com.tpadsz.uic.user.api.AccountManager;
import com.tpadsz.uic.user.api.InfoManager;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.utils.Constants;
import com.utils.DateUtil;
import com.utils.LoggerUtils;
import com.utils.StringUtil;
import com.web.vo.LoginVo;
import com.web.vo.MemberVo;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

	private MemberDao memberDao;
	private Logger systemLogger = LoggerUtils.SYSTEM;
	private Logger loginLogger = LoggerUtils.LOGIN;
	private Logger dayLogger = LoggerUtils.DAYLOG;
	private WorkService workService;
	private XMemcachedClient memcachedClient;
	private CountService countService;
	private AccountManager accountManager;
	private InfoManager infoManager;
	public static final long PAGE_SIZE = 500;
	private ExecutorService threadPool = Executors.newFixedThreadPool(1);

	@Autowired
	public void setMemberDao(MemberDao memberDao) {
		this.memberDao = memberDao;
	}

	@Override
	public LoginVo generate(String params) {
		LoginVo loginVo = null;
		try {
			// byte[] data = AESCoder.decrypt(Encodes.decodeBase64(params),
			// Constants.CLIENT_KEY);
			loginVo = LoginVo.parseFrom(params);
		} catch (Exception e) {
			systemLogger.error("LoginVo generate : " + params, e);
		}
		return loginVo;
	}

	@Override
	public void update(Member member) throws NotExecutedDbException {
		try {
			memberDao.update(member);
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:memberDao, method:update", e);
		}
	}

	private void updateWorkAddingAppUser(Member member) {
		try {
			workService.updateUid(member.getId(), member.getUid());
		} catch (Exception e) {
			systemLogger.error("bean:workService,method:updateUid", e);
		}
	}

	@Override
	public void updateMemberByFillingUid() {
		threadPool.execute(new UpdateMemberThread());
	}

	class UpdateMemberThread implements Runnable {

		@Override
		public void run() {
			dayLogger.warn("updateMemberByFillingUid start.");
			Long total = memberDao.getTotalCountWithoutUid();
			if (total <= 0) {
				dayLogger.warn("updateMemberByFillingUid data is null.");
				return;
			}
			for (long pageNo = 0; pageNo <= total / PAGE_SIZE; pageNo++) {
				List<Member> members = memberDao.getAllWithPageWithoutUid(
						pageNo * PAGE_SIZE, PAGE_SIZE);
				if (CollectionUtils.isNotEmpty(members)) {
					for (Member member : members) {
						generateAppUser(member);
					}

				}
			}
			dayLogger.warn("updateMemberByFillingUid end.");
		}

	}

	@Override
	public MemberVo getById(String memberId) {
		if (StringUtils.isBlank(memberId)) {
			throw new RuntimeException("memberId is needed!");
		}
		Member member = memberDao.getById(memberId);
		return BeanMapper.map(member, MemberVo.class);
	}

	@Override
	public LoginVo login(LoginVo loginVo) {
		if (loginVo == null || StringUtils.isBlank(loginVo.getLoginName())) {
			return null;
		}
		String loginName = loginVo.getLoginName();
		String type = loginVo.getType();
		Member member = null;
		synchronized (loginName + type) {
			member = generateByLoginName(StringUtils.trim(loginVo.getLoginName()), StringUtils.trim(loginVo.getType()));
			if (member == null) {
				member = getNewMemberByLoginVo(loginVo);
				try {
					memberDao.save(member);
				} catch (Exception e) {
					try {
						member.setNickname("天天");
						memberDao.save(member);
					} catch (Exception e1) {
						systemLogger.warn("bean:memberDao, method:save, origin:" + e.getMessage() + ",member:" + ToStringBuilder.reflectionToString(member));
						member = generateByLoginName(StringUtils.trim(loginVo.getLoginName()), StringUtils.trim(loginVo.getType()));
						if(member == null){
							return null;
						}
					}
				}
				member = generateAppUser(member);
			} else {
				autoUpdateMemberByLoginVo(member, loginVo);
				try {
					memberDao.update(member);
				} catch (Exception e) {
					try {
						member.setNickname(null);
						member.setDescr(null);
						memberDao.update(member);
					} catch (Exception e1) {
						systemLogger.error("bean:memberDao, method:update", e);
					}
				}
			}
		}
		LoginVo result = getLoginResult(member);
		loginLogger.warn(result.toString());
		return result;
	}

	@Override
	public Member generateAppUser(Member member) {
		if (member == null || StringUtils.isBlank(member.getId())
				|| StringUtils.isNotBlank(member.getUid())) {
			return member;
		}
		synchronized (member.getId()) {
			try {
				AppUser appUser = accountManager.registerHandily(OfferFactory.generateOffer());
				member.setUid(appUser.getId());
				updateUid(member);
				updateWorkAddingAppUser(member);
				infoManager.patch(OfferFactory.generateLoginedOffer(
						member.getUid(), appUser.getToken()),
						generateAppUserInfo(member));
			} catch (Exception e) {
				systemLogger.error(
						"bean:memberService, method:generateAppUser", e);
			}
		}
		return member;
	}

	private void updateUid(Member member) throws NotExecutedDbException {
		try {
			memberDao.updateUid(member);
		} catch (Exception e) {
			throw new NotExecutedDbException(
					"bean:memberDao, method:updateUid", e);
		}
	}

	private AppUser generateAppUserInfo(Member member) {
		AppUser appUser = new AppUser();
		appUser.setIcon(member.getPic());
		appUser.setNickname(member.getNickname());
		appUser.setDescr(member.getDescr());
		return appUser;
	}

	private void autoUpdateMemberByLoginVo(Member member, LoginVo loginVo) {
		member.setLoginDate(new Date());
		member.setPic(loginVo.getPic());
		member.setNickname(loginVo.getNickname());
		member.setDescr(loginVo.getTip());
	}

	@Override
	public Member generateByLoginName(String loginName, String type) {
		Member member = memberDao.getByLoginName(loginName, type);
		member = generateAppUser(member);
		return member;
	}

	private Member getNewMemberByLoginVo(LoginVo loginVo) {
		Member member = new Member();
		member.setId(StringUtil.getUUID());
		member.setBirthday(loginVo.getBirthday());
		member.setCreateDate(new Date());
		member.setLoginDate(new Date());
		member.setDescr(loginVo.getTip());
		member.setEmail(loginVo.getEmail());
		member.setGender(loginVo.getGender());
		member.setLoginName(loginVo.getLoginName());
		member.setMobile(loginVo.getMobile());
		member.setNickname(loginVo.getNickname());
		member.setType(loginVo.getType());
		member.setQq(loginVo.getQq());
		member.setRealName(loginVo.getRealName());
		member.setPic(loginVo.getPic());
		member.setAllowComment(Constants.ENABLED);
		member.setAllowPost(Constants.ENABLED);
		return member;
	}

	private LoginVo getLoginResult(Member member) {
		LoginVo result = BeanMapper.map(member, LoginVo.class);
		String createDate = DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss");
		result.setCreateDate(createDate);
		result.setVisitor(searchMemberVisitors(member.getId()));
		return result;
	}

	@Autowired
	public void setWorkService(WorkService workService) {
		this.workService = workService;
	}

	@Override
	public boolean update(LoginVo loginVo) {
		if (loginVo == null || !loginVo.hasId()) {
			return false;
		}
		Member member = memberDao.getByLoginName(loginVo.getLoginName(),
				loginVo.getType());
		if (member == null) {
			return false;
		}
		updateMemberByLoginVo(member, loginVo);
		generateAppUser(member);
		boolean succ = false;
		try {
			succ = memberDao.update(member);
		} catch (Exception e) {
			systemLogger.error("memberService update .", e);
		}
		return succ;
	}

	private void updateMemberByLoginVo(Member member, LoginVo loginVo) {
		member.setBirthday(loginVo.getBirthday());
		member.setLoginDate(new Date());
		member.setEmail(loginVo.getEmail());
		member.setGender(loginVo.getGender());
		member.setMobile(loginVo.getMobile());
		member.setQq(loginVo.getQq());
		member.setRealName(loginVo.getRealName());
	}

	@Override
	// @Cacheable(value = "memberService", key =
	// "'searchMemberVisitors' + #memberId ")
	public Long searchMemberVisitors(String memberId) {
		Long visitors = null;
		try {
			visitors = memcachedClient.get(MemcachedObjectType.COUNTER_MEMBER_VISITOR.getPrefix() + memberId);
			if (visitors == null || visitors < 0) {
				visitors = countService.countMemberVisitors(memberId);
				memcachedClient.set(MemcachedObjectType.COUNTER_MEMBER_VISITOR.getPrefix()	+ memberId,	MemcachedObjectType.COUNTER_MEMBER_VISITOR.getExpiredTime(), visitors);
			}
		} catch (MemcachedNotResponsedException e) {
			systemLogger.equals(e);
		}
		return visitors;
	}

	@Autowired
	public void setMemcachedClient(XMemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Autowired
	public void setCountService(CountService countService) {
		this.countService = countService;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@Autowired
	public void setInfoManager(InfoManager infoManager) {
		this.infoManager = infoManager;
	}

}
