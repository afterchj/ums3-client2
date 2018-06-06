package com.service.impl;


import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.cache.MemcachedObjectType;
import com.cache.XMemcachedClient;
import com.dao.WorkDao;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mapper.BeanMapper;
import com.model.member.Member;
import com.model.member.Work;
import com.service.CountService;
import com.service.MemberService;
import com.service.WorkService;
import com.tpadsz.exception.DisabledException;
import com.tpadsz.exception.MemcachedNotResponsedException;
import com.tpadsz.exception.NotExecutedDbException;
import com.tpadsz.exception.ParamBlankException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.QueryManager;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.utils.CollectionUtil;
import com.utils.Constants;
import com.utils.DateUtil;
import com.utils.LoggerUtils;
import com.utils.StringUtil;
import com.utils.convert.Convert;
import com.web.vo.WorkNewUploadVo;
import com.web.vo.WorkUploadVo;
import com.web.vo.WorkVo;

@Service("workService")
public class WorkServiceImpl implements WorkService{
	public static final Long pageSize = 10l;
	private WorkDao workDao;
	private XMemcachedClient memcachedClient;
	private CountService countService;
	private Logger system = LoggerUtils.SYSTEM;
	private MemberService memberService;
	private QueryManager queryManager;

	@Autowired
	public void setWorkDao(WorkDao workDao) {
		this.workDao = workDao;
	}

	@Override
//	@Cacheable(value = "workService", key = "'getAllWithPage' + #pageNo")
	public List<WorkVo> getAllByMemberWithPage(Long pageNo) {
		List<Work> works = workDao.getAllWithPage((pageNo - 1) * pageSize, pageSize);
		List<WorkVo> workVos = convert(works);
		return workVos;
	}
	
	@Override
	public List<WorkVo> getAllByUserWithPage(Long pageNo) {
		List<Work> works = workDao.getAllByUserWithPage((pageNo - 1) * pageSize, pageSize);
		List<WorkVo> workVos = convert(works);
		return workVos;
	}
	
	@Override
	public void completeWorkVo(List<WorkVo> workVos){
		searchWorkVistors(workVos);
		searchWorkPraisers(workVos);
	}

	private List<WorkVo> convert(List<Work> works) {
		if(CollectionUtil.isBlank(works)){
			return Lists.newArrayList();
		}
		List<WorkVo> workVos = BeanMapper.mapList(works, new Convert<WorkVo>() {

			@Override
			public void convert(Object source, WorkVo destinationObject) {
				Work work = (Work)source;
				destinationObject.setCreateDate(DateUtil.format(work.getCreateDate(), "yyyy-MM-dd"));
			}
		});
		return workVos;
	}

//	@Cacheable(value = "workService", key = "'getByMemberWithPage' + #memberId + '@' + #pageNo")
	public List<WorkVo> getByMemberWithPage(String memberId, Long pageNo) {
		List<Work> works = workDao.getByMemberWithPage(memberId, (pageNo - 1) * pageSize, pageSize);
		List<WorkVo> workVos = convert(works);
		return workVos;
	}
	
	@Override
	public List<Work> findByMemberWithPageWithoutUid(String memberId, Long pageNo){
		return workDao.getByMemberWithPageWithoutUid(memberId, (pageNo - 1) * pageSize, pageSize);
	}
	
	@Override
	public List<WorkVo> getByUserWithPage(String uid, Long pageNo) {
		List<Work> works = workDao.getByUserWithPage(uid, (pageNo - 1) * pageSize, pageSize);
		List<WorkVo> workVos = convert(works);
		return workVos;
	}

	private void searchWorkVistors(List<WorkVo> workVos) {
		List<String> keys = Lists.newArrayListWithCapacity(workVos.size());
		for(WorkVo workVo : workVos){
			keys.add(MemcachedObjectType.COUNTER_WORK_VISITOR.getPrefix() + workVo.getId());
		}
		Map<String, Object> cacheVisitors = null;
		boolean isGet = true;
		try {
			cacheVisitors = memcachedClient.getBulk(keys);
		} catch (MemcachedNotResponsedException e) {
			system.error(e);
			isGet = false;
		}
		if(MapUtils.isEmpty(cacheVisitors)){
			cacheVisitors = Maps.newHashMap();
		}
		for(WorkVo workVo : workVos){
			Long visitors = (Long)cacheVisitors.get(MemcachedObjectType.COUNTER_WORK_VISITOR.getPrefix() + workVo.getId());
			if(visitors == null || visitors < 0){
				visitors = countService.countWorkVisitors(workVo.getId());
				if(isGet){
					try {
						memcachedClient.set(MemcachedObjectType.COUNTER_WORK_VISITOR.getPrefix() + workVo.getId(), MemcachedObjectType.COUNTER_WORK_VISITOR.getExpiredTime(), visitors);
					} catch (MemcachedNotResponsedException e) {
						system.error(e);
					}
				}
			}
			workVo.setVisitor(visitors);
		}
	}
	
	@Override
//	@Cacheable(value = "workService", key = "'searchMemberPraisers' + #memberId ")
	public Long searchWorkPraisers(String workId) {
		Long praisers = null;
		boolean isGet = true;
		try {
			praisers = memcachedClient.get(MemcachedObjectType.COUNTER_WORK_PRAISER.getPrefix() + workId);
		} catch (MemcachedNotResponsedException e) {
			system.error(e);
			isGet = false;
		}
		if(praisers == null || praisers < 0){
			praisers = countService.countWorkPraisers(workId);
			if(isGet){
				try {
					memcachedClient.set(MemcachedObjectType.COUNTER_WORK_PRAISER.getPrefix() + workId, MemcachedObjectType.COUNTER_WORK_PRAISER.getExpiredTime(), praisers);
				} catch (MemcachedNotResponsedException e) {
					system.error(e);
				}
			}
		}
		return praisers;
	}
	
	@Override
	public void searchWorkPraisers(List<WorkVo> workVos) {
		List<String> keys = Lists.newArrayListWithCapacity(workVos.size());
		for(WorkVo workVo : workVos){
			keys.add(MemcachedObjectType.COUNTER_WORK_PRAISER.getPrefix() + workVo.getId());
		}
		Map<String, Object> cacheVisitors = null;
		boolean isGet = true;
		try {
			cacheVisitors = memcachedClient.getBulk(keys);
		} catch (MemcachedNotResponsedException e) {
			system.error(e);
			isGet = false;
		}
		if(cacheVisitors == null){
			cacheVisitors = Maps.newHashMap();
		}
		for(WorkVo workVo : workVos){
			Long praisers = (Long)cacheVisitors.get(MemcachedObjectType.COUNTER_WORK_PRAISER.getPrefix() + workVo.getId());
			if(praisers == null || praisers < 0){
				praisers = countService.countWorkPraisers(workVo.getId());
				if(isGet){
					try {
						memcachedClient.set(MemcachedObjectType.COUNTER_WORK_PRAISER.getPrefix() + workVo.getId(), MemcachedObjectType.COUNTER_WORK_PRAISER.getExpiredTime(), praisers);
					} catch (MemcachedNotResponsedException e) {
						system.error(e);
					}
				}
			}
			workVo.setPraiser(praisers);
		}
	}
	
	@Autowired
	public void setMemcachedClient(XMemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@Autowired
	public void setCountService(CountService countService) {
		this.countService = countService;
	}

	@Override
//	@Cacheable(value = "workService", key = "'searchMemberVisitors' + #memberId ")
	public Long searchWorkVisitors(String memberId) {
		Long visitors = null;
		boolean isGet = true;
		try {
			visitors = memcachedClient.get(MemcachedObjectType.COUNTER_WORK_VISITOR.getPrefix() + memberId);
		} catch (MemcachedNotResponsedException e) {
			system.error(e);
			isGet = false;
		}
		if(visitors == null || visitors < 0){
			visitors = countService.countMemberVisitors(memberId);
			try {
				if(isGet){
					memcachedClient.set(MemcachedObjectType.COUNTER_WORK_VISITOR.getPrefix() + memberId, MemcachedObjectType.COUNTER_WORK_VISITOR.getExpiredTime(), visitors);
				}
			} catch (MemcachedNotResponsedException e) {
				system.error(e);
			}
		}
		return visitors;
	}
	
	@Override
	public WorkUploadVo generateUpload(String params) {
		WorkUploadVo uploadVo = null;
		try {
//			byte[] data = AESCoder.decrypt(Encodes.decodeBase64(params), Constants.CLIENT_KEY);
			uploadVo = WorkUploadVo.parseFrom(params);
		} catch (Exception e) {
			system.error("WorkServiceImpl generateUpload : " + params, e);
		}
		return uploadVo;
	}
	
	@Override
	public WorkNewUploadVo generateNewUpload(JSONObject params) {
		WorkNewUploadVo uploadVo = null;
		try {
			uploadVo = WorkNewUploadVo.parseFrom(params);
		} catch (Exception e) {
			system.error("WorkServiceImpl generateNewUpload : " + params, e);
		}
		return uploadVo;
	}

	@Override
	public String save(WorkUploadVo uploadVo, MultipartFile file) {
		if(!uploadVo.hasMemberId()){
			return "004";
		}
		if(!uploadVo.hasDescr()){
			return "005";
		}
		if(!uploadVo.hasNickname()){
			return "006";
		}
		Work work = getWorkByUploadVo(uploadVo);
		try {
			File location = new File(Constants.WORK_PIC, "/" + work.getId() + "/icon.jpg");
			if(!location.getParentFile().exists()){
				location.getParentFile().mkdirs();
			}
			file.transferTo(location);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			return "200";
		}
		
		Member member = memberService.generateByLoginName(uploadVo.getLoginName(), uploadVo.getType());
		if(member == null){
			return "007";
		}
		if(member.getAllowPost() == Constants.DENIED){
			return "008";	
		}
		work.setMemberId(member.getId());
		work.setUid(member.getUid());
		try {
			workDao.save(work);
		} catch (Exception e) {
			try {
				work.setDescr("");
				work.setNickname(member.getNickname());
				workDao.save(work);
			} catch (Exception e1) {
				system.error("workDao save", e);
				return "100";
			}
		}
		return "000";
	}

	private Work getWorkByUploadVo(WorkUploadVo uploadVo) {
		Work work = BeanMapper.map(uploadVo, Work.class);
		work.setCreateDate(new Date());
		work.setStatus(0);
		return work;
	}
	
	private Work getWorkByUploadVo(WorkNewUploadVo uploadVo) {
		Work work = BeanMapper.map(uploadVo, Work.class);
		work.setCreateDate(new Date());
		work.setStatus(0);
		return work;
	}

	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public List<WorkVo> getByMemberWithPage(String loginName, String type,
			Long pageNo) {
		List<Work> works = workDao.getByLoginNameWithPage(loginName, type, (pageNo - 1) * pageSize, pageSize);
		List<WorkVo> workVos = convert(works);
		searchWorkVistors(workVos);
		return workVos;
	}

	@Override
	public String save(WorkNewUploadVo uploadVo, MultipartFile file) throws DisabledException, ParamBlankException, SystemAlgorithmException {
		if(StringUtils.isBlank(uploadVo.getDescr()) || StringUtils.isBlank(uploadVo.getUid())){
			throw new ParamBlankException();
		}
		Work work = getWorkByUploadVo(uploadVo);
		AppUser appUser = queryManager.findById(uploadVo.getUid());
		if(appUser.getStatus() == Constants.DENIED){
			throw new DisabledException();
		}
		work.setNickname(StringUtils.isNotBlank(appUser.getNickname()) ? appUser.getNickname() : appUser.getSerialno());
		save(work);
		try {
			File location = new File(Constants.WORK_PIC, "/" + work.getId() + "/icon.jpg");
			if(!location.getParentFile().exists()){
				location.getParentFile().mkdirs();
			}
			file.transferTo(location);
		} catch (IllegalStateException | IOException e) {
			throw new SystemAlgorithmException("bean:workService, method:save", e);
		}
		return work.getId();
	}
	
	public void save(Work work) throws SystemAlgorithmException{
		String descr = work.getDescr();
		try {
			work.setId(StringUtil.getUUID());
			workDao.save(work);
		} catch (Exception e) {
			try {
				work.setDescr("");
				workDao.save(work);
			} catch (Exception e1) {
				work.setDescr(descr);
				work.setNickname("");
				try {
					workDao.save(work);
				} catch (Exception e2) {
					work.setDescr("");
					work.setNickname("");
					try {
						workDao.save(work);
					} catch (Exception e3) {
						throw new SystemAlgorithmException("workDao save error, work=" + ToStringBuilder.reflectionToString(work), e);
					}
				}
			}
		}
	}

	@Autowired
	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	@Override
	public void updateUid(String mid, String uid) throws NotExecutedDbException {
		try {
			workDao.updateUid(mid, uid);
		} catch (Exception e) {
			throw new NotExecutedDbException("bean:workDao, method:update", e);
		}
	}

}
