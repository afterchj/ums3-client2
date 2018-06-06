package com.web.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.model.dd.OfferFactory;
import com.model.dd.ResultDict;
import com.service.NoticeService;
import com.service.UserService;
import com.tpadsz.exception.AccountNotCorrectException;
import com.tpadsz.exception.ApplicationNotFoundException;
import com.tpadsz.exception.DisabledException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.AccountManager;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.tpadsz.uic.user.api.vo.Firmware;
import com.tpadsz.vo.Offer;
import com.utils.Constants;
import com.web.vo.UserVo;

@Controller("accountTpadController")
@RequestMapping("/account/tpad")
public class AccountTpadController extends BaseDecodedController{
	
	private AccountManager accountManager;
	private NoticeService noticeService;
	private UserService userService;
	private Logger logger = Logger.getLogger(AccountTpadController.class);

	@RequestMapping(value="/handy-register", method=RequestMethod.POST)
	public String handyRegister(@ModelAttribute("decodedParams")JSONObject params, ModelMap model)  {
		try {
			Offer offer = OfferFactory.generateOffer();
			try {
				Firmware firmware =  JSONObject.parseObject(params.getString("firmware"), Firmware.class);
				if(firmware != null){
					offer.putValue("firmware", firmware);
				}
			} catch (Exception e1) {
				logger.error("JSONObject.parseObject", e1);
			}
			AppUser user = accountManager.registerHandily(offer);
			try {
				noticeService.pushMessage(Constants.NOTICE_WELCOME_NAME, String.format(Constants.NOTICE_WELCOME_CONTENT, user.getSerialno()), Constants.NOTICE_TYPE_SYSTEM, user.getId(), Constants.NOTICE_DTYPE_NATIVE);
			} catch (Exception e) {
				logger.error("noticeService pushMessage", e);
			}
//测试代码, 注册默认送100元
//try {
//	coinAddManager.addCoins(user.getId(), Constants.APP_ID, "170", "123", UUID.randomUUID().toString(), 100000);
//} catch (CheckNotAllowedException e) {
//	e.printStackTrace();
//}
			UserVo userVo = convert(user);
			model.put("restriction", userService.selectModeByFirmware(params.getObject("firmware", Firmware.class)));
			model.put("user", userVo);
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (SystemAlgorithmException | ApplicationNotFoundException e) {
			system.error(e);
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		}
		return null;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@ModelAttribute("decodedParams")JSONObject params, ModelMap model)  {
		ResultDict result = ResultDict.SUCCESS;
		try {
			String mobile = params.getString("name");
			String pwd = params.getString("pwd");
			if(StringUtils.isBlank(mobile) || StringUtils.isBlank(pwd)){
				result = ResultDict.PARAMS_BLANK;
			}else {
				AppUser appUser = accountManager.loginByTpad(OfferFactory.generateUserLoginOffer(mobile, pwd));
				model.put("user", UserVo.convert(appUser));
			}
		} catch (DisabledException e) {
			result = ResultDict.AUTHORITY_NOT_ALLOWED;
		} catch (AccountNotCorrectException e) {
			result = ResultDict.ACCOUNT_NOT_CORRECT;
		} catch (SystemAlgorithmException e) {
			system.error(e);
			result = ResultDict.SYSTEM_ERROR;
		}
		model.put("result", result.getCode());
		return null;
	}
	
	private UserVo convert(AppUser user) {
		return UserVo.convert(user);
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	
}
