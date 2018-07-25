package com.test;

import com.utils.PropertiesLoader;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by hongjian.chen on 2018/6/12.
 */

public class MyTest {
    private static PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
    @Test
    public void test(){
        String str="test.png";
        String fileName = UUID.randomUUID().toString().replace("-", "");
        System.out.println(str.substring(str.lastIndexOf("."),str.length()));
    }

    public static void main(String[] args) {
        propertiesLoader.setProperties("classpath:/setup.properties", "classpath:/setup.production.properties");
        System.out.println(propertiesLoader.getProperty("head.storage"));
    }
}
