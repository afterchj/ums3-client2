package com.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.model.dd.OfferFactory;
import com.model.dd.ResultDict;
import com.service.NoticeService;
import com.tpadsz.ctc.api.UserManager;
import com.tpadsz.ctc.exception.TaskRepeatException;
import com.tpadsz.ctc.vo.Present;
import com.tpadsz.ctc.vo.UserCommitOffer;
import com.tpadsz.exception.*;
import com.tpadsz.uic.user.api.InfoManager;
import com.tpadsz.uic.user.api.QueryManager;
import com.tpadsz.uic.user.api.exception.MobileAlreadyExistedException;
import com.tpadsz.uic.user.api.exception.TokenNotEffectiveException;
import com.tpadsz.uic.user.api.exception.UserNotFoundException;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.tpadsz.uic.user.api.vo.TpadUser;
import com.utils.Constants;
import com.utils.LoggerUtils;
import com.web.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.model.dd.OfferFactory.generateLoginedOffer;

@RequestMapping("/account/info")
@Controller("infoController")
public class InfoController extends BaseDecodedController {

    private Logger system = LoggerUtils.SYSTEM;
    private InfoManager infoManager;
    private UserManager userManager;
    private QueryManager queryManager;
    private NoticeService noticeService;

    @RequestMapping(value = "/complete", method = RequestMethod.POST)
    public String complete(@ModelAttribute("decodedParams") JSONObject
                                   params, ModelMap model) {

        try {
            String uid = params.getString("uid");
            String token = params.getString("token");
            String invitation = params.getString("invition");
            TpadUser tpadUser = generateSimplifyTpadUser(params);
            infoManager.patch(generateLoginedOffer(uid, token),
                    tpadUser);
            if (StringUtils.isNotBlank(invitation) && !StringUtils.equals
                    (invitation, uid)) {
                Present present = submitUserInvitationTask(uid, token,
                        invitation);
                model.put("gain", present == null ? null : present.getGain());
            }
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (SystemAlgorithmException | ApplicationNotCorrectException e) {
            system.error(e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        } catch (UserNotFoundException e) {
            model.put("result", ResultDict.ACCOUNT_NOT_EXISTED.getCode());
        } catch (TokenNotEffectiveException e) {
            model.put("result", ResultDict.TOKEN_NOT_CORRECT.getCode());
        }
        return null;
    }

    private Present submitUserInvitationTask(String uid, String token, String
            invitation) {
        Present present = null;
        try {
            AppUser user = queryManager.findByLoginName(Constants.APP_ID,
                    invitation);
            if (user != null && user.getStatus() != Constants.DISABLED) {
                UserCommitOffer offer = OfferFactory.generateUserCommitOffer
                        (Constants.TASK_RECOMMEND, uid, token, "invi" + uid);
                offer.putValue("recommender", user.getId());
                present = userManager.submitTask(offer);
                noticeService.commitInvitationNotice(user, present);
            }
        } catch (TaskRepeatException | CheckNotAllowedException e) {
            system.warn("method:submitUserInvitation,uid:" + uid + "," +
                    "invitation:" + invitation + ",cause:taskRepeatException");
        } catch (Exception e) {
            system.error("method:submitUserInvitation,uid:" + uid + "," +
                    "invitation:" + invitation, e);
        }
        return present;
    }

    // 1 gender 性格（0: 女 1:男） 1 必须提供
    // 2 birthyear 出生年份 4 必须提供
    // 3 invition 邀请码 10 NULL
    // 4 uid 用户ID 32 必须提供
    // 3 token 用户登录后返回的有效性信息 32 必须提供
    // 4 firmware 手机信息 json 必须提供
    // 5 birthmonth 出生月份 2 NULL
    // 6 birthday 出生日 2 NULL
    private TpadUser generateSimplifyTpadUser(JSONObject params) {
        TpadUser tpadUser = new TpadUser();
        tpadUser.setGender(params.getInteger("gender"));
        tpadUser.setBirthyear(params.getInteger("birthyear"));
        tpadUser.setBirthmonth(params.getInteger("birthmonth"));
        tpadUser.setBirthday(params.getInteger("birthday"));
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("label", params.getString("label"));
        tpadUser.setInfo1(jsonObj.toJSONString());
        return tpadUser;
    }

    //	1	gender	性格（0: 女 1:男）	1	必须提供
//	2	birthyear	出生年份	4	必须提供
//	4	nickname(APP)	昵称	10	NULL
//	5	legalName	真实姓名	10	NULL
//	6	address	住址	30	NULL
//	7	prov	省份（参考附件省份对应表）	2	NULL
//	8	city	城市（参考附件城市对应表）	3	NULL
//	9	uid	用户ID	32	必须提供
//	10	token	用户登录后返回的有效性信息	32	必须提供
//	11	firmware	手机信息	json	必须提供
//	12	birthmonth	出生月份	2	NULL
//	13	birthday	出生日	2	NULL
//	14	icon	用户头像URL地址	255	NULL
    private TpadUser generateFullTpadUser(JSONObject params) {
        TpadUser tpadUser = new TpadUser();
        tpadUser.setGender(params.getInteger("gender"));
        tpadUser.setBirthyear(params.getInteger("birthyear"));
        tpadUser.setBirthmonth(params.getInteger("birthmonth"));
        tpadUser.setBirthday(params.getInteger("birthday"));
        tpadUser.setLegalName(params.getString("legalName"));
        tpadUser.setAddress(params.getString("address"));
        tpadUser.setProv(params.getInteger("prov"));
        tpadUser.setCity(params.getInteger("city"));
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("label", params.getString("label"));
        tpadUser.setInfo1(jsonObj.toJSONString());
        return tpadUser;
    }

    //修改个人资料
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modify(@ModelAttribute("decodedParams") JSONObject params,
                         ModelMap model) {
        try {
            String uid = params.getString("uid");
            String token = params.getString("token");
            TpadUser tpadUser = generateFullTpadUser(params);
            infoManager.patch(generateLoginedOffer(uid, token),
                    tpadUser);
//
            AppUser appUser = generateAppUser(params);
//            appUser.setQq(null);
            infoManager.patch(generateLoginedOffer(uid, token),
                    appUser);
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (SystemAlgorithmException | ApplicationNotCorrectException e) {
            system.error(e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        } catch (UserNotFoundException e) {
            model.put("result", ResultDict.ACCOUNT_NOT_EXISTED.getCode());
        } catch (TokenNotEffectiveException e) {
            model.put("result", ResultDict.TOKEN_NOT_CORRECT.getCode());
        } catch (NotExecutedDbException e) {
            system.error(e);
        } catch (MobileAlreadyExistedException e) {
            system.error(e);
        }
        return null;
    }


    private AppUser generateAppUser(JSONObject params) {
        AppUser appUser = new AppUser();
        appUser.setNickname(params.getString("nickname"));
//		appUser.setIcon(params.getString("icon"));
        appUser.setMode(params.getInteger("mode"));
        appUser.setCommunityUid(params.getString("communityUid"));
        appUser.setCommunityLoginSuc(params.getBooleanValue
                ("communityLoginSuc"));
        return appUser;
    }

    @RequestMapping(value = "/commit", method = RequestMethod.POST)
    public String commit(@ModelAttribute("decodedParams") JSONObject params,
                         ModelMap model) {
        try {
            String uid = params.getString("uid");
            String token = params.getString("token");
            AppUser appUser = generateAppUser(params);
            AppUser submittedAppUser = infoManager.remoteSubmit(
                    generateLoginedOffer(uid, token), appUser);
            model.put("result", ResultDict.SUCCESS.getCode());
            model.put("user", UserVo.convert(submittedAppUser));
        } catch (SystemAlgorithmException | ApplicationNotCorrectException e) {
            system.error(e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        } catch (UserNotFoundException e) {
            model.put("result", ResultDict.ACCOUNT_NOT_EXISTED.getCode());
        } catch (TokenNotEffectiveException e) {
            model.put("result", ResultDict.TOKEN_NOT_CORRECT.getCode());
        } catch (Exception e) {
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
            system.error("params:" + params.toJSONString() + "\t" + e
                    .getMessage());
        }
        return null;
    }

    @RequestMapping(value = "/mode", method = RequestMethod.POST)
    public String changeMode(@ModelAttribute("decodedParams") JSONObject params,
                             ModelMap model) {
        try {
            String uid = params.getString("uid");
            String token = params.getString("token");
            Integer mode = params.getInteger("mode");
            Integer mode2 = params.getInteger("mode2");
            if (mode == null && mode2 == null) {
                throw new ParamBlankException();
            }
            if (mode != null) {
                infoManager.changeMode(generateLoginedOffer(uid,
                        token), mode);
            }
            if (mode2 != null) {
                infoManager.changeMode2(generateLoginedOffer
                        (uid, token), mode2);
            }
            model.put("result", ResultDict.SUCCESS.getCode());
        } catch (SystemAlgorithmException | ApplicationNotCorrectException e) {
            system.error(e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        } catch (UserNotFoundException e) {
            model.put("result", ResultDict.ACCOUNT_NOT_EXISTED.getCode());
        } catch (TokenNotEffectiveException e) {
            model.put("result", ResultDict.TOKEN_NOT_CORRECT.getCode());
        } catch (ParamBlankException e) {
            model.put("result", ResultDict.PARAMS_BLANK.getCode());
        }
        return null;
    }

    //获取个人资料
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String getInfo(@ModelAttribute("decodedParams") JSONObject params,
                          ModelMap model) {
        try {
            String uid = params.getString("uid");
            String token = params.getString("token");
            AppUser appUser = infoManager.getBaseUserInfo(OfferFactory
                    .generateLoginedOffer(uid, token));
            model.put("result", ResultDict.SUCCESS.getCode());
            model.put("user", UserVo.convert(appUser));
        } catch (SystemAlgorithmException | ApplicationNotCorrectException
                e) {
            system.error(e);
            model.put("result", ResultDict.SYSTEM_ERROR.getCode());
        } catch (UserNotFoundException e) {
            model.put("result", ResultDict.ACCOUNT_NOT_EXISTED.getCode());
        } catch (TokenNotEffectiveException e) {
            model.put("result", ResultDict.TOKEN_NOT_CORRECT.getCode());
        }
        return null;
    }
    @Autowired
    public void setInfoManager(InfoManager infoManager) {
        this.infoManager = infoManager;
    }

    @Autowired
    public void setQueryManager(QueryManager queryManager) {
        this.queryManager = queryManager;
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
