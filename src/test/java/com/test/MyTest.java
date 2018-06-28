package com.test;

import org.junit.Test;

/**
 * Created by hongjian.chen on 2018/6/12.
 */
public class MyTest {

    @Test
    public void test(){
        String str="temp";
        String result="{\"result\":"+str+"}";
        System.out.println(result);
    }
}
