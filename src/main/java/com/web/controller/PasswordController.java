package com.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.model.dd.OfferFactory;
import com.model.dd.ResultDict;
import com.tpadsz.exception.AccountNotCorrectException;
import com.tpadsz.exception.ApplicationNotCorrectException;
import com.tpadsz.exception.DisabledException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.AccountManager;
import com.tpadsz.uic.user.api.exception.AccountNotFoundException;
import com.tpadsz.uic.user.api.exception.InvalidValidationCodeException;
import com.tpadsz.uic.user.api.exception.TokenNotEffectiveException;
import com.tpadsz.uic.user.api.exception.UserNotFoundException;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.tpadsz.uic.user.api.vo.MobileVerifyType;
import com.web.vo.UserVo;

@RequestMapping("/account/pwd")
@Controller("passwordController")
public class PasswordController extends BaseDecodedController{
	
	private AccountManager accountManager;

	@RequestMapping(value="/retrieve", method=RequestMethod.POST)
	public String retrieve(@ModelAttribute("decodedParams")JSONObject params, ModelMap model) {
		try {
			String mobile = params.getString("mobile");
			String code = params.getString("code");
			String password = params.getString("password");
			accountManager.retrieveTpadPassword(OfferFactory.generateMobileVerifyOffer(mobile, MobileVerifyType.UPDATE,  code), password);
			AppUser appUser = accountManager.loginByTpad(OfferFactory.generateUserLoginOffer(mobile, password));
			model.put("user", UserVo.convert(appUser));
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (AccountNotFoundException e) {
			model.put("result", ResultDict.ACCOUNT_NOT_EXISTED.getCode());
		} catch (InvalidValidationCodeException e) {
			model.put("result", ResultDict.VALIDATION_CODE_NOT_CORRECT.getCode());
		} catch (SystemAlgorithmException | AccountNotCorrectException e) {
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		} catch (DisabledException e) {
			model.put("result", ResultDict.AUTHORITY_NOT_ALLOWED.getCode());
		}
		return null;
	}	
	
	@RequestMapping(value="/change", method=RequestMethod.POST)
	public String change(@ModelAttribute("decodedParams")JSONObject params, ModelMap model) {
		try {
			String uid = params.getString("uid");
			String token = params.getString("token");
			String oldPassword = params.getString("pwd");
			String newPassword = params.getString("newpwd");
			accountManager.changeTpadPassword(OfferFactory.generateLoginedOffer(uid, token), oldPassword, newPassword);
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (UserNotFoundException e) {
			model.put("result", ResultDict.ACCOUNT_NOT_CORRECT.getCode());
		} catch (SystemAlgorithmException | ApplicationNotCorrectException e) {
			system.error(e);
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		} catch (TokenNotEffectiveException e) {
			model.put("result", ResultDict.TOKEN_NOT_CORRECT.getCode());
		} catch (AccountNotCorrectException e) {
			model.put("result", ResultDict.PASSWORD_NOT_CORRECT.getCode());
		}
		return null;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}	
}
