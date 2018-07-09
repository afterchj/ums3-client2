//package com.test;
//
//import com.dao.FileStoreInfoDao;
//import com.uicdao.UpdateHeadDao;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
///**
// * Created by hongjian.chen on 2018/6/28.
// */
//public class MyBatisUtil {
//    private static SqlSessionFactory sessionFactory;
//
//
//    static {
//        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:conf/beans.xml");
//        sessionFactory = (SqlSessionFactory) act.getBean("sqlSessionFactory");
//    }
//    public static SqlSession getSess() {
//        return sessionFactory.openSession();
//    }
//
//    public static void main(String[] args) {
//        SqlSession session = getSess();
//        System.out.println("result="+session.getMapper(FileStoreInfoDao.class).getByHottestWithPage("2018-05-01","2018-07-08",1l,10l).size());
//    }
//}
