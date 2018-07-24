package com.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.model.ThirdLogin;
import com.model.dd.OfferFactory;
import com.model.dd.ResultDict;
import com.service.NoticeService;
import com.tpadsz.ctc.api.UserManager;
import com.tpadsz.ctc.exception.TaskRepeatException;
import com.tpadsz.exception.*;
import com.tpadsz.uic.user.api.AccountManager;
import com.tpadsz.uic.user.api.exception.InvalidValidationCodeException;
import com.tpadsz.uic.user.api.exception.MobileAlreadyExistedException;
import com.tpadsz.uic.user.api.exception.TokenNotEffectiveException;
import com.tpadsz.uic.user.api.exception.UserNotFoundException;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.utils.Constants;
import com.web.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller("mobileController")
@RequestMapping("/account/mobile")
public class MobileContoller extends BaseDecodedController {

    private AccountManager accountManager;
    private UserManager userManager;
    private NoticeService noticeService;

    @Resource
    private ThirdLoginSerive thirdLoginSerive;

    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public String change(@ModelAttribute("decodedParams") JSONObject params,
                         ModelMap model) {
        try {
            String mobile = params.getString("mobile");
            String code = params.getString("code");
            String uid = params.getString("uid");
            String token = params.getString("token");
            accountManager.changeMobile(OfferFactory
                    .generateLoginedMobileValidateOffer(mobile, code, uid,
                            token));
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (SystemAlgorithmException | ApplicationNotCorrectException e) {
            system.error(e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        } catch (InvalidValidationCodeException e) {
            model.put("result", ResultDict.VALIDATION_CODE_NOT_CORRECT
                    .getCode());
        } catch (UserNotFoundException e) {
            model.put("result", ResultDict.ACCOUNT_NOT_CORRECT.getCode());
        } catch (TokenNotEffectiveException e) {
            model.put("result", ResultDict.TOKEN_NOT_CORRECT.getCode());
        } catch (MobileAlreadyExistedException e) {
            model.put("result", ResultDict.ACCOUNT_ALREADY_REGISTERED.getCode
                    ());
        }
        return null;
    }


    @RequestMapping(value = "/setup", method = RequestMethod.POST)
    public String setup(@ModelAttribute("decodedParams") JSONObject params,
                        ModelMap model) {
        try {
            String mobile = params.getString("mobile");
            String code = params.getString("code");
            String uid = params.getString("uid");
            String token = params.getString("token");
            String password = params.getString("password");
            accountManager.setupMobile(OfferFactory
                    .generateLoginedMobileValidateOffer(mobile, code, uid,
                            token), password);
            submitRegisterTask(uid, token);
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (InvalidValidationCodeException e) {
            model.put("result", ResultDict.VALIDATION_CODE_NOT_CORRECT
                    .getCode());
        } catch (UserNotFoundException e) {
            model.put("result", ResultDict.ACCOUNT_NOT_CORRECT.getCode());
        } catch (SystemAlgorithmException | ApplicationNotCorrectException e) {
            system.error(e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        } catch (TokenNotEffectiveException e) {
            model.put("result", ResultDict.TOKEN_NOT_CORRECT.getCode());
        }
        return null;
    }


    private void submitRegisterTask(String uid, String token) {
        try {
            userManager.submitTask(OfferFactory.generateUserCommitOffer
                    (Constants.TASK_REGISTER, uid, token, "reg" + uid));
//            noticeService.pushMessage(Constants.NOTICE_REGISTER_NAME,
//                    Constants.NOTICE_REGISTER_CONTENT, Constants
//                            .NOTICE_TYPE_SYSTEM, uid, Constants
//                            .NOTICE_DTYPE_NATIVE);
        } catch (TaskRepeatException | CheckNotAllowedException e) {
            system.warn("method:submitRegisterTask,uid:" + uid + "," +
                    "cause:taskRepeatException");
        } catch (Exception e) {
            system.error("method:submitRegisterTask,uid:" + uid, e);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute("decodedParams") JSONObject params,
                        ModelMap model) {
        ResultDict result = ResultDict.SUCCESS;
        try {
            String mobile = params.getString("mobile");
            String pwd = params.getString("pwd");
            if (StringUtils.isBlank(mobile) || StringUtils.isBlank(pwd)) {
                result = ResultDict.PARAMS_BLANK;
            } else {
                AppUser appUser = accountManager.loginByTpad(OfferFactory
                        .generateUserLoginOffer(mobile, pwd));
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

    @RequestMapping(value = "/thirdLogin", method = RequestMethod.POST)
    public String ThirdLogin(@ModelAttribute("decodedParams") JSONObject
                                     params, ModelMap model) {
        ResultDict result = ResultDict.SUCCESS;
        String qq = params.getString("qq");
        String wx = params.getString("wx");
        String uid = params.getString("uid");
        ThirdLogin third = new ThirdLogin();
        third.setId(uid);
        third.setQq(qq);
        third.setQq_nickname(params.getString("qq_nickname"));
        third.setQq_image_url(params.getString("qq_image_url"));
        third.setWx(wx);
        third.setWx_nickname(params.getString("wx_nickname"));
        third.setWx_image_url(params.getString("wx_image_url"));
        AppUser appUsers = thirdLoginSerive.getUserInfoById(third);
        model.put("user", UserVo.convert(appUsers));
        model.put("result", result.getCode());
        return null;
    }

    @Autowired
    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    @Autowired
    public void setNoticeService(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @Autowired
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

}
