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
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.ValidationManager;
import com.tpadsz.uic.user.api.exception.AccountNotFoundException;
import com.tpadsz.uic.user.api.exception.InvalidValidationCodeException;
import com.tpadsz.uic.user.api.exception.UserAlreadyRegisteredException;
import com.tpadsz.uic.user.api.exception.ValidationNotSendedException;
import com.tpadsz.uic.user.api.vo.MobileVerifyType;

@Controller("validationController")
@RequestMapping("/account/verify")
public class ValidationController extends BaseDecodedController{

	private ValidationManager validationManager;

	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@ModelAttribute("decodedParams") JSONObject params,
			ModelMap model) {
		try {
			String mobile = params.getString("mobile");
			validationManager.registerCheckOut(OfferFactory.generateMobileValidateOffer(mobile));
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (SystemAlgorithmException | ValidationNotSendedException e) {
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
			system.error(e);
		} catch (UserAlreadyRegisteredException e) {
			model.put("result", ResultDict.ACCOUNT_ALREADY_REGISTERED.getCode());
		} 
		return null;

	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(@ModelAttribute("decodedParams") JSONObject params,
			ModelMap model) {
		try {
			String mobile = params.getString("mobile");
			validationManager.updateCheckOut(OfferFactory.generateMobileValidateOffer(mobile));
			model.put("result", ResultDict.SUCCESS.getCode());
		} catch (SystemAlgorithmException | ValidationNotSendedException e) {
			system.error(e);
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		} catch (AccountNotFoundException e) {
			model.put("result", ResultDict.ACCOUNT_NOT_EXISTED.getCode());
		} 
		return null;
	}

	@RequestMapping(value="/check/update", method=RequestMethod.POST)
	public String checkUpdate(@ModelAttribute("decodedParams") JSONObject params,
			ModelMap model) {
		try {
			String code = params.getString("code");
			String mobile = params.getString("mobile");
			validationManager.checkValidation(OfferFactory.generateMobileVerifyOffer(mobile, MobileVerifyType.UPDATE, code));
			model.put("result", ResultDict.SUCCESS.getCode());
			model.put("check", true);
		} catch (InvalidValidationCodeException e) {
			model.put("result", ResultDict.SUCCESS.getCode());
			model.put("check", false);
		} catch (SystemAlgorithmException e) {
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		}
		return null;
	}
	
	@RequestMapping(value="/check/register", method=RequestMethod.POST)
	public String checkRegister(@ModelAttribute("decodedParams") JSONObject params,
			ModelMap model) {
		try {
			String code = params.getString("code");
			String mobile = params.getString("mobile");
			validationManager.checkValidation(OfferFactory.generateMobileVerifyOffer(mobile, MobileVerifyType.REGISTER, code));
			model.put("result", ResultDict.SUCCESS.getCode());
			model.put("check", true);
		} catch (InvalidValidationCodeException e) {
			model.put("result", ResultDict.SUCCESS.getCode());
			model.put("check", false);
		} catch (SystemAlgorithmException e) {
			model.put("result", ResultDict.SYSTEM_ERROR.getCode());
		}
		return null;
	}

	@Autowired
	public void setValidationManager(ValidationManager validationManager) {
		this.validationManager = validationManager;
	}

}
