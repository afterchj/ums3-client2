package com.web.controller;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import com.model.ThirdLogin;
import com.model.dd.OfferFactory;
import com.model.dd.ResultDict;
import com.service.NoticeService;
import com.service.ThirdLoginSerive;
import com.tpadsz.ctc.api.UserManager;
import com.tpadsz.ctc.exception.TaskRepeatException;
import com.tpadsz.exception.*;
import com.tpadsz.uic.user.api.AccountManager;
import com.tpadsz.uic.user.api.exception.InvalidValidationCodeException;
import com.tpadsz.uic.user.api.exception.MobileAlreadyExistedException;
import com.tpadsz.uic.user.api.exception.TokenNotEffectiveException;
import com.tpadsz.uic.user.api.exception.UserNotFoundException;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.tpadsz.uic.user.api.vo.TpadUser;
import com.utils.*;
import com.utils.convert.DBUtils;
import com.web.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static sun.java2d.cmm.ColorTransform.In;

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

    @RequestMapping(value = "/login2", method = RequestMethod.POST)
    public String change2(@ModelAttribute("decodedParams") JSONObject params,
                          ModelMap model) {
        ResultDict result = ResultDict.SUCCESS;
        String mobile = params.getString("mobile");
        String pwd = params.getString("pwd");
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(pwd)) {
            model.put("result", result.PARAMS_BLANK.getCode());
            return null;
        } else {

            //验证账号
            Map<String, Object> map = thirdLoginSerive.findByMobile(mobile);
            if (MapUtil.isBlank(map)) {
                model.put("result", result.ACCOUNT_NOT_CORRECT.getCode());
                return null;
            }
            //验证密码 空
            boolean isCorrect = checkPassword(pwd, map.get("password").toString(), map.get("salt").toString());
            if (!isCorrect) {
                model.put("result", result.ACCOUNT_NOT_CORRECT.getCode());
                return null;
            }
            Map<String,Object> map1 = thirdLoginSerive.findByTpad(map.get("id").toString());
            if (StringUtils.isNotBlank(map1.get("status").toString()) &&
                    StringUtils.isNotBlank(map.get("status").toString())) {
                if (map1.get("status") == 0 || map.get("status") == 0) {
                    model.put("result", result.AUTHORITY_NOT_ALLOWED.getCode());
                    return null;
                }
            }
            AppUser appUser = mapToAppUser(map,map1);
            model.put("result", result.SUCCESS.getCode());
            model.put("user", UserVo.convert(appUser));
        }
        return null;
    }

    private AppUser mapToAppUser(Map<String, Object> map, Map<String, Object> map1) {
        AppUser appUser = new AppUser();
        TpadUser tpadUser = new TpadUser();
        appUser.setTpadUser(tpadUser);
        appUser.setId((String) map.get("id"));
        if (StringUtils.isNotBlank(map.get("gender").toString())) {
            appUser.getTpadUser().setGender(Integer.valueOf(map.get("gender").toString()));
        }
        if (StringUtils.isNotBlank(map.get("prov").toString())){
            appUser.getTpadUser().setProv(Integer.valueOf(map.get("prov").toString()));
        }
        if (StringUtils.isNotBlank(map.get("birthday").toString())) {
            appUser.getTpadUser().setBirthday(Integer.valueOf(map.get
                    ("birthday").toString()));
        }
        if (StringUtils.isNotBlank(map.get("birthmonth").toString())) {
            appUser.getTpadUser().setBirthmonth((Integer) map.get
                    ("birthmonth"));
        }
        if (StringUtils.isNotBlank(map.get("birthyear").toString())) {
            appUser.getTpadUser().setBirthyear((Integer) map.get("birthyear"));
        }
        if (StringUtils.isNotBlank(map.get("mobile").toString())) {
            appUser.getTpadUser().setMobile((String) map.get("mobile"));
        }
        if (StringUtils.isNotBlank(map.get("legal_name").toString())){
            appUser.getTpadUser().setLegalName(map.get("legal_name").toString());
        }
        if (StringUtils.isNotBlank(map1.get("icon").toString())) {
            appUser.setIcon((String) map1.get("icon"));
        }
        if (StringUtils.isNotBlank(map1.get("nickname").toString())) {
            appUser.setNickname((String) map1.get("nickname"));
        }
        if (StringUtils.isNotBlank(map1.get("login_name").toString())) {
            appUser.setLoginName(map1.get("login_name").toString());
        }
        if (StringUtils.isNotBlank(map1.get("serialno").toString())) {
            appUser.setSerialno((String) map1.get("serialno"));
        }
        if (StringUtils.isNotBlank(map1.get("communityUid").toString())){
            appUser.setCommunityUid(map1.get("communityUid").toString());
        }
        return appUser;
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

    @Resource(name = "dataSource2")
    DruidDataSource druidDataSource;

    public static void main(String[] args) {
        DruidDataSource druidDataSource = new DruidDataSource();
        System.out.println(druidDataSource.dump());
    }

    //Test
    @RequestMapping(value = "/thirdLogin2", method = RequestMethod.POST)
    public String ThirdLogin3(@ModelAttribute("decodedParams") JSONObject
                                      params, ModelMap model) throws
            SQLException {
        ResultDict result = ResultDict.SUCCESS;
        String qq = params.getString("qq");
        String wx = params.getString("wx");
        String uid = params.getString("uid");
        String qq_nickname = params.getString("qq_nickname");
        String qq_image_url = params.getString("qq_image_url");
        String wx_nickname = params.getString("wx_nickname");
        String wx_image_url = params.getString("wx_image_url");
        DBUtils db = DBUtils.getInstance();
        db.getConnection();
        List listUid = new ArrayList();
        listUid.add(uid);
        String sql = "SELECT " +
                " a.id,a.login_name,a.nickname,t.mobile,a.icon,t.birthyear, " +
                " t.birthmonth,t.birthday,t.prov,t.gender,a.serialno " +
                " FROM " +
                " f_app_user a, " +
                " f_tpad_user t " +
                " WHERE " +
                " a.tpad_id = t.id " +
                " AND a.id = ? and app_id=1 ";
        List<Map<String, Object>> mapList = null;

        mapList = db.executeQuery(sql, listUid);
        if (mapList.size() == 0) {
            result = ResultDict.PARAMS_NOT_PARSED;
            model.put("result", result.getCode());
            model.put("msg", result.getValue());
            return null;
        }

        Map<String, Object> map = mapList.get(0);
        com.tpadsz.uic.user.api.vo.AppUser appUser = new com.tpadsz.uic.user
                .api.vo.AppUser();
        TpadUser tpadUser = new TpadUser();
        appUser.setTpadUser(tpadUser);
        appUser.setId((String) map.get("id"));
        if (StringUtils.isNotBlank(map.get("icon").toString())) {
            appUser.setIcon((String) map.get("icon"));
        }
        if (StringUtils.isNotBlank(map.get("nickname").toString())) {
            appUser.setNickname((String) map.get("nickname"));
        }
        if (StringUtils.isNotBlank(map.get("birthday").toString())) {
            appUser.getTpadUser().setBirthday(Integer.valueOf(map.get
                    ("birthday").toString()));
        }
        if (StringUtils.isNotBlank(map.get("birthmonth").toString())) {
            appUser.getTpadUser().setBirthmonth((Integer) map.get
                    ("birthmonth"));
        }
        if (StringUtils.isNotBlank(map.get("birthyear").toString())) {
            appUser.getTpadUser().setBirthyear((Integer) map.get("birthyear"));
        }
        if (StringUtils.isNotBlank(map.get("login_name").toString())) {
            appUser.setLoginName(map.get("login_name").toString());
        }
        if (StringUtils.isNotBlank(map.get("mobile").toString())) {
            appUser.getTpadUser().setMobile((String) map.get("mobile"));
        }

        if (StringUtils.isNotBlank(map.get("prov").toString())) {
            appUser.getTpadUser().setProv((Integer) map.get("prov"));
        }
        if (StringUtils.isNotBlank(map.get("gender").toString())) {
            appUser.getTpadUser().setGender((Integer) map.get("gender"));
        }
        if (StringUtils.isNotBlank(map.get("serialno").toString())) {
            appUser.setSerialno((String) map.get("serialno"));
        }

        String sql2 = "SELECT * FROM f_app_user_third WHERE id =?";
        List<Map<String, Object>> getUserThidInfoById =
                null;
        try {
            getUserThidInfoById = db.executeQuery(sql2, listUid);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        if (getUserThidInfoById == null || getUserThidInfoById.isEmpty()) {
            String sql3 = " Insert INTO f_app_user_third (id, qq, " +
                    " qq_nickname, qq_image_url, wx, wx_nickname, " +
                    " wx_image_url) " +
                    " VALUES(?,?,?,?,?,?,?) ";
            List list = new ArrayList();
            list.add(uid);
            list.add(qq);
            list.add(qq_nickname);
            list.add(qq_image_url);
            list.add(wx);
            list.add(wx_nickname);
            list.add(wx_image_url);

            try {
                db.executeUpdate(sql3, list);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } else {
            if (StringUtils.isNotBlank(qq)) {
                String sql4 = "UPDATE f_app_user_third set " +
                        " qq = ?," +
                        " qq_nickname = ?, " +
                        " qq_image_url = ? " +
                        " WHERE id = ? ";
                List list = new ArrayList();
                list.add(qq);
                list.add(qq_nickname);
                list.add(qq_image_url);
                list.add(uid);
                db.executeUpdate(sql4, list);
            } else if (StringUtils.isNotBlank(wx)) {
                String sql5 = "UPDATE f_app_user_third set " +
                        " wx = ?, " +
                        " wx_nickname = ?, " +
                        " wx_image_url = ? " +
                        " WHERE id = ? ";
                List list = new ArrayList();
                list.add(wx);
                list.add(wx_nickname);
                list.add(wx_image_url);
                list.add(uid);
                db.executeUpdate(sql5, list);
            }
        }

        String sql6 = "SELECT * FROM f_app_user_third WHERE id =?";
        List<Map<String, Object>> getUserThidInfoById2 =
                db.executeQuery(sql6, listUid);
        Map<String, Object> mapThirdInfo2 = getUserThidInfoById2.get(0);
        if (StringUtils.isNotBlank(qq)) {
            if (StringUtils.isBlank(appUser.getIcon()) &&
                    StringUtils.isNotBlank(mapThirdInfo2.get("qq_image_url")
                            .toString())) {

                appUser.setIcon(mapThirdInfo2.get("qq_image_url").toString());

            }
            if (StringUtils.isBlank(appUser.getNickname()) &&
                    StringUtils.isNotBlank(mapThirdInfo2.get("qq_nickname")
                            .toString())) {
                appUser.setNickname(mapThirdInfo2.get("qq_nickname")
                        .toString());
            }
        } else if (StringUtils
                .isNotBlank(wx)) {
            if (StringUtils.isBlank(appUser.getIcon()) &&
                    StringUtils.isNotBlank(mapThirdInfo2.get("wx_image_url")
                            .toString())) {
                appUser.setIcon(mapThirdInfo2.get("wx_image_url").toString());
            }
            if (StringUtils.isBlank(appUser.getNickname()) &&
                    StringUtils.isNotBlank(mapThirdInfo2.get("wx_nickname")
                            .toString())) {
                appUser.setNickname(mapThirdInfo2.get("wx_nickname").toString
                        ());
            }
        }

        model.put("user", UserVo.convert(appUser));
        model.put("result", result.getCode());

        db.closeDB();
        return null;
    }

    public boolean checkPassword(String actual, String expected, String salt) {
        if (StringUtils.isBlank(actual) || StringUtils.isBlank(expected) ||
                StringUtils.isBlank(salt)) {
            return false;
        }
        String actualEncodingPassword = encrypt(actual, Encodes.decodeHex
                (salt));
        if (StringUtils.equals(actualEncodingPassword, expected)) {
            return true;
        } else {
            return false;
        }
    }

    private static final int INTERATIONS = 1024;

    private String encrypt(String actual, byte[] salt) {
        byte[] hashPassword = Digests.sha1(actual.getBytes(), salt,
                INTERATIONS);
        return Encodes.encodeHex(hashPassword);
    }

}
