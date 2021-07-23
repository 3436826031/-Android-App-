package com.example.biyelunw;

import com.mysql.jdbc.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DBOpenHelper {
    private static String diver = "com.mysql.jdbc.Driver";
    private static String url = "jdbc:mysql://59.110.231.16:3306/lw?useUnicode=true&characterEncoding=UTF-8";
    private static String user = "root";//用户名
    private static String password = "19991104wW";//密码


    /*
     * 连接数据库
     * */
    public static Connection getConn(){
        Connection conn = null;
        try {
            Class.forName(diver);
            conn = (Connection) DriverManager.getConnection(url,user,password);//获取连接

            System.out.println("数据库链接成功0");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }









}