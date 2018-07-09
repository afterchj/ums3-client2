package com.utils.convert;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by hongjian.chen on 2018/7/9.
 */

public class DBUtils {

    // 表示定义数据库的用户名
    private static String USERNAME = "ccy";
    // 定义数据库的密码
    private static String PASSWORD = "ccy";
    // 定义数据库的驱动信息
    private static String DRIVER = "com.mysql.jdbc.Driver";
    // 定义访问数据库的地址
    private static String URL = "jdbc:mysql://10.10.11.80:3306/uic?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull";

    private static DBUtils per = null;
    // 定义数据库的链接
    private Connection con = null;
    // 定义sql语句的执行对象
    private PreparedStatement pstmt = null;
    // 定义查询返回的结果集合
    private ResultSet resultSet = null;

    private DBUtils() {

    }

    /**
     * 单例模式，获得工具类的一个对象
     *
     * @return
     */
    public static DBUtils getInstance() {
        if (per == null) {
            per = new DBUtils();
            per.registeredDriver();
        }
        return per;
    }

    private void registeredDriver() {
        // TODO Auto-generated method stub
        try {
            Class.forName(DRIVER);
//            System.out.println("注册驱动成功！");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获得数据库的连接
     *
     * @return
     */
    public Connection getConnection() {
        try {
            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        System.out.println("连接数据库成功!!");
        return con;
    }

    /**
     * 完成对数据库的表的添加删除和修改的操作
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public boolean executeUpdate(String sql, List<Object> params) throws SQLException {

        boolean flag = false;
        int result = -1;
        pstmt = con.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            int index=1;
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index, params.get(i));
                index++;
            }
        }
        result = pstmt.executeUpdate();
        flag = result > 0 ? true : false;

        return flag;
    }

    /**
     * 从数据库中查询数据
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public List<Map<String, Object>> executeQuery(String sql, List<Object> params) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        int index = 1;
        pstmt = con.prepareStatement(sql);
        if (params != null && !params.isEmpty()) {
            for (int i = 0; i < params.size(); i++) {
                pstmt.setObject(index++, params.get(i));
            }
        }
        resultSet = pstmt.executeQuery();
        ResultSetMetaData metaData = resultSet.getMetaData();
        int cols_len = metaData.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int i = 0; i < cols_len; i++) {
                String cols_name = metaData.getColumnName(i + 1);
                Object cols_value = resultSet.getObject(cols_name);
                if (cols_value == null) {
                    cols_value = "";
                }
                map.put(cols_name, cols_value);
            }
            list.add(map);
        }
        return list;
    }

    public String getIcon(String sql, String id) throws SQLException{
        String icon="";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                icon=resultSet.getString(1);
            }

        return icon;
    }


    public void closeDB() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
