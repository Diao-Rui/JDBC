package medium.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author: Diao Rui
 * @className: DruidPart
 * @Description: druid连接池使用类
 * @DateTime: 2023/7/26 14:25
 * @version: 1.0
 **/
public class DruidPart {
    /*
     * 直接使用代码设置连接池连接方式
     * 创建一个druid连接对象
     * 设置连接池参数[必须|非必须]
     * 获取连接【通用方法，所有连接池都一样】
     * 回收连接【通用方法，所有连接池都一样】
     * */
    @Test
    public void testHard() throws SQLException {
        //连接池对象
        DruidDataSource dataSource = new DruidDataSource();
        //设置连接池参数
        //必要参数
        /*
         * 驱动名称
         * URL
         * username
         * password
         * */
        //注册驱动
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //设置数据库连接地址（URL）
        dataSource.setUrl("jdbc:mysql://localhost:3306/stuGrade");
        //设置用户名
        dataSource.setUsername("root");
        //设置密码
        dataSource.setPassword("12305dg");
        /*
         * 非必要参数
         * setInitialSize:初始化连接池大小
         * setMaxActive:最大连接数量
         * */
        dataSource.setInitialSize(5);
        dataSource.setMaxActive(10);
        //获取连接
        DruidPooledConnection connection = dataSource.getConnection();
        //操作数据
        PreparedStatement preparedStatement = connection.prepareStatement("select * from t_bank");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.print(resultSet.getString(1) + " ");
            System.out.print(resultSet.getString(2) + " ");
            System.out.print(resultSet.getString(3));
            System.out.println();
        }
        //回收连接
        connection.close();
    }

    /*
     * 通过读取外部配置文件的方法实例化druid连接池对象
     * 软连接
     * */
    @Test
    public void testSoft() throws Exception {
        //读取外部配置文件到properties对象
        Properties properties = new Properties();
        //src下的文件可以使用类加载器
        InputStream resourceAsStream = DruidPart.class.getClassLoader().getResourceAsStream("druid.properties");
        properties.load(resourceAsStream);
        //使用连接池的工具类的工厂模式，创建连接池
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        //获取连接
        Connection connection = dataSource.getConnection();
        //操作数据
        PreparedStatement preparedStatement = connection.prepareStatement("select * from t_bank");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.print(resultSet.getString(1) + " ");
            System.out.print(resultSet.getString(2) + " ");
            System.out.print(resultSet.getString(3));
            System.out.println();
        }
        //回收连接
        connection.close();
    }
}
