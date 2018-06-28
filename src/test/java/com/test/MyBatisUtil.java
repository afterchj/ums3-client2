package com.test;

import com.dao.SPFileDownloadDao;
import com.utils.DateUtil;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;

/**
 * Created by hongjian.chen on 2018/6/28.
 */
public class MyBatisUtil {
    private static SqlSessionFactory sessionFactory;


    static {
        ApplicationContext act = new ClassPathXmlApplicationContext("classpath:myapplicationContext.xml");
        sessionFactory = (SqlSessionFactory) act.getBean("sqlSessionFactory");
    }

    public static SqlSession getSess() {
        return sessionFactory.openSession();
    }

    public static void main(String[] args) {
        SqlSession session = getSess();
//        List<SPItem> items = session.getMapper(SPItemDao.class).getParentTypes();
//        System.out.println(items.size());

        Integer fileId =1543;
        String logDate = DateUtil.format(new Date(), "yyyy-MM-dd");
        session.getMapper(SPFileDownloadDao.class).updateTotalDownload(fileId,logDate);
    }
}
