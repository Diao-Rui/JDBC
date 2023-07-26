package simple;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class LoginPrepareStatement {
    public static void main(String[] args) throws Exception {
        //前置条件
        Scanner cin = new Scanner(System.in);
        System.out.println("请输入用户名:");
        String username = cin.nextLine();
        System.out.println("请输入密码:");
        String password = cin.nextLine();
        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql:///stugrade", "root", "12305dg");
        //编写带占位符的SQL语句
        String sql = "select * from user where username = ? and password = ?;";
        //创建PrepareStatement对象
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        //设置参数（与SQL语句中的占位符一一对应，并且在SQL中所有的参数都是从1开始）
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        //执行SQL语句
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            System.out.println("登录成功");
        } else {
            System.out.println("登录失败");
        }
        //关闭资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
