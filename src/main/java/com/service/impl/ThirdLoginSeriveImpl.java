package com.service.impl;

import com.model.ThirdLogin;
import com.service.ThirdLoginSerive;
import com.tpadsz.uic.user.api.vo.TpadUser;
import com.uicdao.ThirdLoginDao;
import org.apache.commons.lang3.StringUtils;
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

    @Override
    public com.tpadsz.uic.user.api.vo.AppUser getUserInfoById(ThirdLogin third) {
        Map<String, Object> map = thirdLoginDao.getUserInfoById(third.getId());
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