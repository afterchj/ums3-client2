package com.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.service.ThirdLoginSerive;
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

    ThirdLoginSerive thirdLoginSerive = act.getBean("thirdLoginSerive",ThirdLoginSerive.class);
    @Test
    public void test() {

        String str = "{\"birthday\":3,\"birthmonth\":3,\"birthyear\":1993," +
                "\"communityLoginSuc\":true," +
                "\"communityUid\":\"5a13d0b53421466d17870ea8\",\"gender\":1," +
                "\"icon\":\"http:\\/\\/www.uichange" +
                ".com\\/ums3-share\\/user\\/4e057826899d430e8ba99f0e2526d9c1.jpg\",\"id\":\"4e057826899d430e8ba99f0e2526d9c1\",\"loginName\":\"19331979\",\"mobile\":\"15850511136\",\"mode\":1,\"mode2\":1,\"nickname\":\"小肥猫爱吃鱼\",\"token\":\"74cd65e9806a4c0d8232086db556c265\",\"uid\":\"4e057826899d430e8ba99f0e2526d9c1\",\"firmware\":\"{\\\"clientVersion\\\":\\\"4.1.3\\\",\\\"fm\\\":\\\"\\\",\\\"imei\\\":\\\"869409023502324\\\",\\\"imsi\\\":\\\"460110148404955\\\",\\\"model\\\":\\\"OPPO_A33\\\",\\\"netEnv\\\":\\\"WIFI\\\",\\\"operators\\\":\\\"DX\\\",\\\"os\\\":\\\"android-5.1.1\\\",\\\"pkg\\\":\\\"com.change.unlock\\\",\\\"resolution\\\":\\\"540*960\\\"}\"}";
        JSONObject params = parseObject(str);

        Map<String,Object> map = JSON.parseObject(params.toJSONString());
//        for (Map.Entry<String,Object> entry : map.entrySet()) {
//            System.out.println("key= " + entry.getKey() + " and value= "
//                    + entry.getValue());
//        }
        String uid = map.get("uid").toString();
        System.out.println(uid);
        String tpad_id = thirdLoginSerive.getTpadIdByUid(uid);
        map.put("tpad_id",tpad_id);
        thirdLoginSerive.updateTpadUser(map);
        thirdLoginSerive.updateAppUser(map);

//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println("key= " + entry.getKey() + " and value= "
//                    + entry.getValue());
//        }
    }
}
