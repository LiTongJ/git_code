package com.tong.jdbc;

import com.mysql.jdbc.Driver;

import java.sql.*;

/**
 * 解耦:
 *  1.反射
 *  2.xml配置文件
 */
public class JdbcDemo {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //1.注册驱动   2选其一
        //DriverManager.registerDriver(new Driver());
        Class.forName("com.mysql.jdbc.Driver");
        //2.获取链接
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dir","root","67981");
        //3.获取数据库的预处理对象'
        PreparedStatement ps = con.prepareStatement("select * from account");
        //4.执行sql,得到结果集
        ResultSet rs = ps.executeQuery();
        //5.遍历结果集
        while(rs.next()){
            System.out.println(rs.getString("uid"));
        }
        //6.关闭链接
        rs.close();
        ps.close();
        con.close();
    }
}
