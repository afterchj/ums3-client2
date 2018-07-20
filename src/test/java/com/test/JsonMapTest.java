package com.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.model.ThirdLogin;
import com.model.dd.ResultDict;
import com.service.ThirdLoginSerive;
import com.tpadsz.uic.user.api.vo.AppUser;
import com.tpadsz.uic.user.api.vo.TpadUser;
import com.utils.Digests;
import com.utils.Encodes;
import com.utils.MapUtil;
import com.web.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

import static com.alibaba.fastjson.JSON.parseObject;

/**
 * Created by nannan.li on 2018/7/18.
 */
public class JsonMapTest {

    private static final ClassPathXmlApplicationContext act;

    static {
        act = new ClassPathXmlApplicationContext("classpath:conf/beans.xml");
    }

    ThirdLoginSerive thirdLoginSerive = act.getBean("thirdLoginSerive",
            ThirdLoginSerive.class);

    @Test
    public void test() {

        String str = "{\"birthday\":3,\"birthmonth\":3,\"birthyear\":1993," +
                "\"communityLoginSuc\":true," +
                "\"communityUid\":\"5a13d0b53421466d17870ea8\",\"gender\":1," +
                "\"icon\":\"http:\\/\\/www.uichange" +
                ".com\\/ums3-share\\/user\\/4e057826899d430e8ba99f0e2526d9c1.jpg\",\"id\":\"4e057826899d430e8ba99f0e2526d9c1\",\"loginName\":\"19331979\",\"mobile\":\"15850511136\",\"mode\":1,\"mode2\":1,\"nickname\":\"小肥猫爱吃鱼\",\"token\":\"74cd65e9806a4c0d8232086db556c265\",\"uid\":\"4e057826899d430e8ba99f0e2526d9c1\",\"firmware\":\"{\\\"clientVersion\\\":\\\"4.1.3\\\",\\\"fm\\\":\\\"\\\",\\\"imei\\\":\\\"869409023502324\\\",\\\"imsi\\\":\\\"460110148404955\\\",\\\"model\\\":\\\"OPPO_A33\\\",\\\"netEnv\\\":\\\"WIFI\\\",\\\"operators\\\":\\\"DX\\\",\\\"os\\\":\\\"android-5.1.1\\\",\\\"pkg\\\":\\\"com.change.unlock\\\",\\\"resolution\\\":\\\"540*960\\\"}\"}";
        JSONObject params = parseObject(str);

        Map<String, Object> map = JSON.parseObject(params.toJSONString());
//        for (Map.Entry<String,Object> entry : map.entrySet()) {
//            System.out.println("key= " + entry.getKey() + " and value= "
//                    + entry.getValue());
//        }
        String uid = map.get("uid").toString();
        System.out.println(uid);
        String tpad_id = thirdLoginSerive.getTpadIdByUid(uid);
        map.put("tpad_id", tpad_id);
        thirdLoginSerive.updateTpadUser(map);
        thirdLoginSerive.updateAppUser(map);

//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println("key= " + entry.getKey() + " and value= "
//                    + entry.getValue());
//        }
    }

    @Test
    public void test2() {
        String uid = "4e057826899d430e8ba99f0e2526d9c1";
        Map<String, Object> map = thirdLoginSerive.getAppUserByUid(uid);
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println("key= " + entry.getKey() + " and value= "
//                    + entry.getValue());
//        }
        System.out.println(JSON.toJSON(map));
    }

    @Test
    public void test3() {
        String mobile = "15995873260";
        String pwd = "e10adc3949ba59abbe56e057f20f883e";
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(pwd)) {
            System.out.println(ResultDict.PARAMS_BLANK.getCode());
        } else {

            //验证账号
            Map<String, Object> map = thirdLoginSerive.findByMobile(mobile);
            if (MapUtil.isBlank(map)) {
                System.out.println(ResultDict.ACCOUNT_NOT_CORRECT.getCode());
            }

            //验证密码 空
            boolean isCorrect = checkPassword(pwd, map.get("password")
                    .toString(), map.get("salt").toString());
            if (!isCorrect) {
                System.out.println(ResultDict.ACCOUNT_NOT_CORRECT.getCode());
            }
            Map<String, Object> map1 = thirdLoginSerive.findByTpad(map.get
                    ("id").toString());
            if (StringUtils.isNotBlank(map1.get("status").toString()) &&
                    StringUtils.isNotBlank(map.get("status").toString())) {
                if (Integer.valueOf(map1.get("status").toString()) == 0 || Integer.valueOf(map.get("status").toString()) == 0) {
                    System.out.println(ResultDict.AUTHORITY_NOT_ALLOWED.getCode());
                }
            }
            AppUser appUser = mapToAppUser(map, map1);
            System.out.println(ResultDict.SUCCESS.getCode());
            System.out.println(appUser.getId()   );
        }
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

    @Test
    public void test4(){
        String qq = "E92A8DF534D9DB20A994A7C3B2213097";
        String wx = "";
        String uid = "9f0045b0aaa343e49fa7d26c0d23d435";
//        String qq_nickname = "qq_nick1234";
        String qq_nickname = "凯凯王";
        String qq_image_url = "http://thirdqq.qlogo.cn/qqapp/100735153/E92A8DF534D9DB20A994A7C3B2213097/100";
        String wx_nickname = "";
        String wx_image_url = "";
        ThirdLogin third = new ThirdLogin();
        third.setId(uid);
        third.setQq(qq);
        third.setQq_nickname(qq_nickname);
        third.setQq_image_url(qq_image_url);
        third.setWx(wx);
        third.setWx_nickname(wx_nickname);
        third.setWx_image_url(wx_image_url);
        AppUser appUsers = thirdLoginSerive.getUserInfoById(third);
        System.out.println( UserVo.convert(appUsers));

        System.out.println(appUsers.getIcon());
    }
}
