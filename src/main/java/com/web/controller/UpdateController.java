package com.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.model.dd.OfferFactory;
import com.model.dd.ResultDict;
import com.model.member.Member;
import com.service.MemberService;
import com.service.NoticeService;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.InfoManager;
import com.tpadsz.uic.user.api.QueryManager;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.utils.Constants;
import com.web.vo.UserVo;

@Controller("updateController")
@RequestMapping("/update")
public class UpdateController extends BaseDecodedController{
	
	private Logger logger = LoggerFactory.getLogger(UpdateController.class);
	
	private QueryManager queryManager;
	private MemberService memberService;
	private InfoManager infoManager;
	private NoticeService noticeService;
	
	@RequestMapping(value="/version/35", method=RequestMethod.POST)
	public String update35(@ModelAttribute("decodedParams")JSONObject params, ModelMap model)  {
		try {
			String loginName = params.getString("loginName");
			String type = params.getString("type");
			Member member = memberService.generateByLoginName(loginName, type);
			if(member != null && StringUtils.isNotBlank(member.getUid())){
				AppUser appUser = queryManager.findByIdWithUsefulToken(member.getUid());
				if(appUser != null){
					try {
						AppUser submittedAppUser = infoManager.remoteSubmit(OfferFactory.generateLoginedOffer(appUser.getId(), appUser.getToken()), generateAppUser(member));
						submittedAppUser.setToken(appUser.getToken());
						try {
							noticeService.pushMessage(Constants.NOTICE_WELCOME_NAME, String.format(Constants.NOTICE_WELCOME_CONTENT, appUser.getSerialno()), Constants.NOTICE_TYPE_SYSTEM, appUser.getId(), Constants.NOTICE_DTYPE_NATIVE);
						} catch (Exception e) {
							logger.error("", e);
						}
						model.put("user", UserVo.convert(submittedAppUser));
						model.put("result", ResultDict.SUCCESS.getCode());
					} catch (Exception e) {
						logger.error("bean:infoManager,method:remoteSubmit", e);
						model.put("result", ResultDict.SYSTEM_ERROR.getCode());
					}
				}else {
					model.put("result", ResultDict.ACCOUNT_NOT_EXISTED.getCode());
				}
			}else {
				model.put("result", ResultDict.ACCOUNT_NOT_EXISTED.getCode());
			}
		} catch (SystemAlgorithmException e) {
			logger.error("bean:updateController, method:update35", e);
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		}
		return null;
	}
	
	private AppUser generateAppUser(Member member) {
		AppUser appUser = new AppUser();
		appUser.setNickname(member.getNickname());
		appUser.setIcon(member.getPic());
		return appUser;
	}

	@Autowired
	public void setQueryManager(QueryManager queryManager) {
		this.queryManager = queryManager;
	}

	@Autowired
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Autowired
	public void setInfoManager(InfoManager infoManager) {
		this.infoManager = infoManager;
	}

	@Autowired
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

}
