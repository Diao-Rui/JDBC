package medium;

import java.sql.*;

public class PSGenerateKeys {
    public static void main(String[] args) throws Exception {
        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //创建连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/stuGrade?user=root&password=12305dg");
        //创建Statement
        String sql = "insert into user(username,password,email) values(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        //设置参数
        preparedStatement.setString(1, "heihei");
        preparedStatement.setString(2, "123456");
        preparedStatement.setString(3, "332@123.com");
        //执行SQL
        int rows = preparedStatement.executeUpdate();
        if (rows > 0) {
            System.out.println("插入成功");
            //获取结果集
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            //游标下移
            generatedKeys.next();
            //获取主键
            Object object = generatedKeys.getObject(1);
            System.out.println(object);
        }
        //关闭资源
        preparedStatement.close();
        connection.close();
    }
}
