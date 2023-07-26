package simple;

import org.junit.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrepareStatementTest {
    @Test
    public void testInsert() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///stuGrade", "root", "12305dg");
        String sql = "insert into user values(null,?,?,?);";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "李四");
        preparedStatement.setString(2, "123456");
        preparedStatement.setString(3, "123@123.com");
        int update = preparedStatement.executeUpdate();
        if (update > 0) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testUpdate() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///stuGrade", "root", "12305dg");
        String sql = "update user set username =? where id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, "wangwu");
        preparedStatement.setInt(2, 10003);
        int update = preparedStatement.executeUpdate();
        if (update > 0) {
            System.out.println("修改成功");
        } else {
            System.out.println("修改失败");
        }
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testDelete() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///stuGrade", "root", "12305dg");
        String sql = "delete from user where id =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 10003);
        int update = preparedStatement.executeUpdate();
        if (update > 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("删除失败");
        }
        preparedStatement.close();
        connection.close();
    }

    @Test
    public void testQuery() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql:///stuGrade", "root", "12305dg");
        String sql = "select * from user";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Map> list = new ArrayList<Map>();
        //获取元数据
        ResultSetMetaData metaData = resultSet.getMetaData();
        while (resultSet.next()) {
            Map<String, Object> map = new HashMap<>();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                //使用获取标签名是因为可能在SQL中使用到了别名，使用标签名的话可以直接获取到SQL中的别名
                String columnLabel = metaData.getColumnLabel(i);
                //获取索引列上的值
                Object object = resultSet.getObject(i);
                map.put(columnLabel, object);
            }
            list.add(map);
        }
        System.out.println(list);
        preparedStatement.close();
        connection.close();
    }
}
