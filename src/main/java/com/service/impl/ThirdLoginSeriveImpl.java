package com.service.impl;

import com.cache.XMemcachedClient;
import com.model.DAppUser;
import com.model.DTpadUser;
import com.model.MNumValidation;
import com.model.ThirdLogin;
import com.service.ThirdLoginSerive;
import com.tpadsz.exception.ApplicationNotCorrectException;
import com.tpadsz.exception.MemcachedNotResponsedException;
import com.tpadsz.exception.NotExecutedDbException;
import com.tpadsz.exception.SystemAlgorithmException;
import com.tpadsz.uic.user.api.exception.InvalidValidationCodeException;
import com.tpadsz.uic.user.api.exception.UserNotFoundException;
import com.tpadsz.uic.user.api.vo.MemcachedObjectType;
import com.tpadsz.uic.user.api.vo.TpadUser;
import com.uicdao.ThirdLoginDao;
import com.web.vo.MobileVerifyType;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by nannan.li on 2018/7/11.
 */
@Service("thirdLoginSerive")
public class ThirdLoginSeriveImpl implements ThirdLoginSerive {

    @Resource
    private ThirdLoginDao thirdLoginDao;

    private XMemcachedClient client;

    private Logger logger = Logger.getLogger(ThirdLoginSeriveImpl.class);

    @Override
    public com.tpadsz.uic.user.api.vo.AppUser getUserInfoById(ThirdLogin third) {
        Map<String, Object> map = thirdLoginDao.getUserInfoById(third.getId());
        com.tpadsz.uic.user.api.vo.AppUser appUser = new com.tpadsz.uic.user
                .api.vo.AppUser();
        TpadUser tpadUser = new TpadUser();
        appUser.setTpadUser(tpadUser);
        appUser.setId((String) map.get("id"));
        /*if (StringUtils.isNotBlank((CharSequence) map.get("icon"))) {
            appUser.setIcon((String) map.get("icon"));
        }
        if (StringUtils.isNotBlank((CharSequence) map.get("nickname"))) {
            appUser.setNickname((String) map.get("nickname"));
        }
        if (StringUtils.isNotBlank((CharSequence)map.get("birthday"))) {
            appUser.getTpadUser().setBirthday(Integer.valueOf(map.get
                    ("birthday").toString()));
        }
        if (StringUtils.isNotBlank((CharSequence)map.get("birthmonth"))) {
            appUser.getTpadUser().setBirthmonth((Integer) map.get
                    ("birthmonth"));
        }
        if (StringUtils.isNotBlank((CharSequence) map.get("birthyear"))) {
            appUser.getTpadUser().setBirthyear((Integer) map.get("birthyear"));
        }
        if (StringUtils.isNotBlank((CharSequence) map.get("login_name"))) {
            appUser.setLoginName(map.get("login_name").toString());
        }
        if (StringUtils.isNotBlank((CharSequence) map.get("mobile"))) {
            appUser.getTpadUser().setMobile((String) map.get("mobile"));
        }

        if (StringUtils.isNotBlank((CharSequence) map.get("prov"))) {
            appUser.getTpadUser().setProv((Integer) map.get("prov"));
        }
        if (StringUtils.isNotBlank(map.get("gender").toString())) {
            appUser.getTpadUser().setGender(Integer.valueOf(map.get("gender").toString()));
        }
        if (StringUtils.isNotBlank((CharSequence) map.get("serialno"))) {
            appUser.setSerialno((String) map.get("serialno"));
        }*/

        if (map.get("icon")!=null) {
            appUser.setIcon((String) map.get("icon"));
        }
        if (map.get("nickname")!=null) {
            appUser.setNickname((String) map.get("nickname"));
        }
        if (map.get("birthday")!=null) {
            appUser.getTpadUser().setBirthday(Integer.valueOf(map.get
                    ("birthday").toString()));
        }
        if (map.get("birthmonth")!=null) {
            appUser.getTpadUser().setBirthmonth((Integer) map.get
                    ("birthmonth"));
        }
        if ( map.get("birthyear")!=null) {
            appUser.getTpadUser().setBirthyear((Integer) map.get("birthyear"));
        }
        if (map.get("login_name")!=null) {
            appUser.setLoginName(map.get("login_name").toString());
        }
        if (map.get("mobile")!=null) {
            appUser.getTpadUser().setMobile((String) map.get("mobile"));
        }

        if (map.get("prov")!=null) {
            appUser.getTpadUser().setProv((Integer) map.get("prov"));
        }
        if (map.get("gender")!=null) {
            appUser.getTpadUser().setGender(Integer.valueOf(map.get("gender").toString()));
        }
        if ( map.get("serialno")!=null) {
            appUser.setSerialno((String) map.get("serialno"));
        }
        //查询f_app_user_third记录
        ThirdLogin thirdLogin = thirdLoginDao.getUserThidInfoById(third.getId());
        //保存或修改f_app_user_third的qq或微信信息
        SaveOrUpdateThirdInfo(thirdLogin, third);
        //修改f_app_user字段nickname，icon的值
//        AppUser appUser1 = SaveNickNameOrIcon(third, appUser);
        ThirdLogin thirdLogin1 = thirdLoginDao.getUserThidInfoById(third.getId());

        if (StringUtils.isNotBlank(third.getQq())) {
            if (StringUtils.isNotBlank(thirdLogin1.getQq_image_url())) {
                appUser.setIcon(thirdLogin1.getQq_image_url());
            }
            if (StringUtils.isNotBlank(thirdLogin1.getQq_nickname())) {
                appUser.setNickname(thirdLogin1.getQq_nickname());
            }
        } else if (StringUtils.isNotBlank(third.getWx())) {
            if (StringUtils.isNotBlank(thirdLogin1.getWx_image_url())) {
                appUser.setIcon(thirdLogin1.getWx_image_url());
            }
            if (StringUtils.isNotBlank(thirdLogin1.getWx_nickname())) {
                appUser.setNickname(thirdLogin1.getWx_nickname());
            }
        }
        return appUser;
    }

    @Override
    public void updateTpadUser(Map<String, Object> map) {
        thirdLoginDao.updateTpadUser(map);
    }

    @Override
    public void updateAppUser(Map<String, Object> map) {
         thirdLoginDao.updateAppUser(map);
    }

    @Override
    public String getTpadIdByUid(String map) {

        try {
            return thirdLoginDao.getTpadIdByUid(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Object> getAppUserByUid(String uid) {
        return thirdLoginDao.getAppUserByUid(uid);
    }

    @Override
    public Map<String, Object> findByMobile(String mobile) {
        return thirdLoginDao.findByMobile(mobile);
    }

    @Override
    public Map<String, Object> findByTpad(String tpadId) {
        if (StringUtils.isBlank(tpadId)){
            return null;
        }
        Map<String,Object> map = thirdLoginDao.findByTpad(tpadId);
        return map;
    }

    @Override
    public DTpadUser getTpadUser(String uid) throws SystemAlgorithmException, UserNotFoundException,ApplicationNotCorrectException {
        DAppUser appUser = getAppUser(uid);
        DTpadUser tpadUser = appUser.getTpadUser();
        return tpadUser;
    }

    @Override
    public boolean checkValidation(String code, String mobile, MobileVerifyType type) throws InvalidValidationCodeException, MemcachedNotResponsedException {
        Object expected = client.get(String.format(MemcachedObjectType.CACHE_MESSAGE_VERIFICATION.getPrefix(),
                type.getValue() + "_" + mobile));
        if(expected == null || !(expected instanceof MNumValidation)){
            throw new InvalidValidationCodeException();
        }
        if(!StringUtils.equals(((MNumValidation)expected).getValue(), code)){
            throw new InvalidValidationCodeException();
        }
        return true;
    }

    @Override
    public void deleteValidation(String code, String mobile, MobileVerifyType type) throws MemcachedNotResponsedException {
        client.delete(String.format(MemcachedObjectType.CACHE_MESSAGE_VERIFICATION.getPrefix(), type.getValue() + "_" + mobile));
    }

    @Override
    public void setupMobile(DTpadUser tpadUser, String mobile) throws UserNotFoundException,
            ApplicationNotCorrectException, SystemAlgorithmException {
        tpadUser.setMobile(mobile);
        tpadUser.setIsActivated(1);
        try {
            thirdLoginDao.update(tpadUser);
        } catch (NotExecutedDbException e) {
            logger.error("Db cannot be excuted.",e);
            throw new SystemAlgorithmException();
        }
    }

    private DAppUser getAppUser(String uid) throws SystemAlgorithmException, UserNotFoundException, ApplicationNotCorrectException{
        DAppUser appUser = null;
        try {
            appUser = findById(uid);
        } catch (NotExecutedDbException e1) {
            throw new SystemAlgorithmException("", e1);
        }
        if (appUser == null) {
            throw new UserNotFoundException("uid:" + uid);
        }

        return appUser;
    }

    public DAppUser findById(String uid) throws NotExecutedDbException {
        if (StringUtils.isBlank(uid)) {
            return null;
        }
        try {
            DAppUser app = thirdLoginDao.getById(uid);
            return app;
        } catch (Exception e) {
            throw new NotExecutedDbException("bean:thirdLoginDao, Method:getById", e);
        }
    }

    public void SaveOrUpdateThirdInfo(ThirdLogin thirdLogin, ThirdLogin third) {

        if (thirdLogin == null) {
            thirdLoginDao.saveUserThirdInfo(third);
        } else {
            //有记录
            if (StringUtils.isNotBlank(third.getQq())) {
                //补全qq信息
                thirdLoginDao.updateUserQQ(third);
            } else if (StringUtils.isNotBlank(third.getWx())) {
                //补全微信信息
                thirdLoginDao.updateUserWx(third);
            }
        }
    }
}