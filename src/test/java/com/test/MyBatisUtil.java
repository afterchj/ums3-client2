//package com.test;
//
//import com.dao.SPFileDao;
//import com.dao.SPFileDownloadDao;
//import com.model.SPFile;
//import org.apache.ibatis.session.SqlSession;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import java.util.List;
//
//
//public class MyBatisUtil {
//    private static SqlSessionFactory sessionFactory;
//
//
//    static {
//        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:conf/beans.xml");
//        sessionFactory = (SqlSessionFactory) act.getBean("sqlSessionFactory");
//    }
//
//    public static SqlSession getSess() {
//        return sessionFactory.openSession();
//    }
//
//    public static void main(String[] args) {
//        SqlSession session = getSess();
//        session.getMapper(SPFileDownloadDao.class).updateTotalDownload(1362, "2018-07-18");
//        List<SPFile> list = session.getMapper(SPFileDao.class).getHottestPage("", "", 0, 24);
//        System.out.println(list.size());
//    }
//}
