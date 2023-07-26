package medium;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PSInsertTest {
    /*
     * 使用普通方式插入10000条数据
     * */
    @Test
    public void testInsert() throws Exception {
        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取连接
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stuGrade", "root", "12305dg");
        String sql = "insert into user(username,password,email) values(?,?,?)";
        //创建statement
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        //获取当前时间
        long start = System.currentTimeMillis();
        //设置参数
        for (int i = 0; i < 10000; i++) {
            preparedStatement.setString(1, "test" + i);
            preparedStatement.setString(2, "123456" + i);
            preparedStatement.setString(3, i + "123@231.com");
            preparedStatement.executeUpdate();
        }
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为" + (end - start) / 1000);
        //关闭资源
        preparedStatement.close();
        conn.close();
    }

    /*
     * 使用批量插入10000条数据（优化）
     * */
    @Test
    public void testInsertYH() throws Exception {
        //注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        //获取连接（加入允许批量插入）
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/stuGrade?rewriteBatchedStatements=true", "root", "12305dg");
        String sql = "insert into user(username,password,email) values(?,?,?)";
        //创建statement
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        //获取当前时间
        long start = System.currentTimeMillis();
        //设置参数
        for (int i = 0; i < 10000; i++) {
            preparedStatement.setString(1, "test" + i);
            preparedStatement.setString(2, "123456" + i);
            preparedStatement.setString(3, i + "123@231.com");
            //添加批次
            preparedStatement.addBatch();
        }
        //执行批次
        preparedStatement.executeBatch();
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为" + (end - start));
        //关闭资源
        preparedStatement.close();
        conn.close();
    }
}
