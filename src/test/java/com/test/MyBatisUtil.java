//package com.test;
//
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//
//public class MyBatisUtil {
//    private static SqlSessionFactory sessionFactory;
//
//
//    static {
//        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:conf/beans.xml");
//        sessionFactory = (SqlSessionFactory) act.getBean("sqlSessionFactory2");
//    }
//
//    public static SqlSession getSess() {
//        return sessionFactory.openSession();
//    }
//
//    public static void main(String[] args) {
//
//    }
//}
