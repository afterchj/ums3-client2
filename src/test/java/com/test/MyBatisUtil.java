package com.test;

import com.model.ThirdLogin;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



public class MyBatisUtil {
    private static SqlSessionFactory sessionFactory;


    static {
        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:conf/beans.xml");
        sessionFactory = (SqlSessionFactory) act.getBean("sqlSessionFactory2");
    }
    public static SqlSession getSess() {
        return sessionFactory.openSession();
    }

    public static void main(String[] args) {
  SqlSession session = getSess();
        ThirdLogin thirdLogin = new ThirdLogin();
        thirdLogin.setId("44");

        //thirdLogin.setQq_image_url("33");
 thirdLogin.setWx_nickname("44");
        thirdLogin.setWx("44");
        session.getMapper(ThirdLoginDao.class).updateUserWx(thirdLogin);


    }
}
