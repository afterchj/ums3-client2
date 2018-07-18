/*
package com.test;

import com.utils.convert.DBUtils;

import java.util.*;


public class Test {

    */
/**
     * @param args
     *//*

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        DBUtils db = DBUtils.getInstance();
        db.getConnection();
        String sql = "SELECT icon FROM uic.f_app_user where id=?";

        String updateSql="UPDATE t_user set user_name = ? where id = ?";

        List<Object> list0=new ArrayList<Object>();
        list0.add("张三丰");
        list0.add("1");
        try {
//            db.executeUpdate(updateSql,list0);
            System.out.println("icon="+db.getIcon(sql,"14715689"));
//            List<Map<String,Object>> list=  db.executeQuery(sql,null);
//            for (int i=0;i<list.size();i++){
//                Map<String, Object> map = list.get(i);
//                for (Map.Entry<String, Object> entry : map.entrySet()) {
//                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//                }
//            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            db.closeDB();
        }
    }

}*/
