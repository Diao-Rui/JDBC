package simple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class LoginStatement {
    public static void main(String[] args) throws Exception {
        //前置条件
        Scanner cin = new Scanner(System.in);
        System.out.println("please input username:");
        String username = cin.nextLine();
        System.out.println("please input password:");
        String password = cin.nextLine();
        //注册驱动（将类加载到内存中通过反射的方法）
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///stugrade", "root", "12305dg");
        //创建Statement对象
        Statement statement = connection.createStatement();
        //执行SQL语句
        String sql = "select * from user where username = '" + username + "' and password = '" + password + "';";
        System.out.println(sql);
        //执行查询
        ResultSet resultSet = statement.executeQuery(sql);
        if (resultSet.next()) {
            System.out.println("login success!");
        } else {
            System.out.println("login failure!");
        }
        //关闭资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
