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
        System.out.println("start getUserInfoById");
        Map<String, Object> map = thirdLoginDao.getUserInfoById(third.getId());
        System.out.println("end getUserInfoById");
        com.tpadsz.uic.user.api.vo.AppUser appUser = new com.tpadsz.uic.user
                .api.vo.AppUser();
        TpadUser tpadUser = new TpadUser();
        appUser.setTpadUser(tpadUser);
        appUser.setId((String) map.get("id"));
        if (map.get("icon") != null) {
            appUser.setIcon((String) map.get("icon"));
        }
        if (map.get("nickname") != null) {
            appUser.setNickname((String) map.get("nickname"));
        }
        if (map.get("birthday") != null) {
            appUser.getTpadUser().setBirthday((Integer) map.get("birthday"));
        }
        if (map.get("birthmonth") != null) {
            appUser.getTpadUser().setBirthmonth((Integer) map.get
                    ("birthmonth"));
        }
        if (map.get("birthyear") != null) {
            appUser.getTpadUser().setBirthyear((Integer) map.get("birthyear"));
        }
        if (map.get("login_name") != null) {
            appUser.setLoginName(map.get("login_name").toString());
        }
        if (map.get("mobile") != null) {
            appUser.getTpadUser().setMobile((String) map.get("mobile"));
        }

        if (map.get("prov") != null) {
            appUser.getTpadUser().setProv((Integer) map.get("prov"));
        }
        if (map.get("gender") != null) {
            appUser.getTpadUser().setGender((Integer) map.get("gender"));
        }
        if (map.get("serialno") != null) {
            appUser.setSerialno((String) map.get("serialno"));
        }

        //查询f_app_user_third记录
        System.out.println("start getUserThidInfoById");
        ThirdLogin thirdLogin = thirdLoginDao.getUserThidInfoById(third.getId());
        System.out.println("end getUserThidInfoById");
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

    public void SaveOrUpdateThirdInfo(ThirdLogin thirdLogin, ThirdLogin third) {

        if (thirdLogin == null) {
            System.out.println("start saveUserThirdInfo");
            thirdLoginDao.saveUserThirdInfo(third);
            System.out.println("end saveUserThirdInfo");
        } else {
            //有记录
            if (StringUtils.isNotBlank(third.getQq())) {
                //补全qq信息
                System.out.println("start updateUserQQ");
                thirdLoginDao.updateUserQQ(third);
                System.out.println("end updateUserQQ");
            } else if (StringUtils.isNotBlank(third.getWx())) {
                //补全微信信息
                System.out.println("start updateUserWx");
                thirdLoginDao.updateUserWx(third);
                System.out.println("end updateUserWx");
            }
        }
    }
}