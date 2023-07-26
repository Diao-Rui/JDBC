package simple;

import com.mysql.jdbc.Driver;

import java.sql.*;

public class StatementTest {
    public static void main(String[] args) throws Exception {
        //1.注册驱动
        /*方案一（不推荐，因为会调用注册两次）
         * DriverManager.registerDriver(new Driver());
         * 方案二（推荐，使用反射注册驱动）
         * */
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取连接
        /*
         * getConnection(url,user, password)
         * url = "jdbc:mysql://127.0.0.1:3306/数据库名?key=value&key=value"
         * */
        Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/stugrade", "root", "12305dg");
        //创建Statement对象
        Statement statement = connection.createStatement();
        //编写SQL语句
        String sql = "select * from user";
        //执行SQL语句
        ResultSet resultSet = statement.executeQuery(sql);
        //结果解析
        while (resultSet.next()) {
            //因为不清楚具体的数据格式，使用Object最好
            System.out.println(resultSet.getObject(1));
            System.out.println(resultSet.getObject(2));
            System.out.println(resultSet.getObject(3));
        }
        //关闭资源
        resultSet.close();
        statement.close();
        connection.close();
    }
}
