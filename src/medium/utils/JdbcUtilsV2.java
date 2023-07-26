package medium.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Author: Diao Rui
 * @className: JdbcUtils
 * @Description: v1.0版本工具类：内部包含一个连接池对象，并且对外提供获取连接和回收连接的方法
 * 小建议（工具类使用静态方法）
 * 实现：
 * 单例模式
 * 方法
 * 对外提供连接方法
 * 回收传入外部的连接
 * 利用线程本地变量，存储连接信息。确保一个线程的多个方法可以获取同一个connection
 * 优势：事务操作的时候 service和DAO属于同一线程，不用在传递连接参数
 * 大家都可以调用getConnection自动获取的是相同的连接池
 * @DateTime: 2023/7/26 15:03
 * @version: 1.0
 **/
public class JdbcUtilsV2 {
    private static DataSource dataSource = null;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    static {
        Properties properties = new Properties();
        InputStream inputStream = JdbcUtilsV2.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        //判断当前线程是否有连接
        Connection connection = threadLocal.get();
        //第一次获取连接
        if (connection == null) {
            try {
                //从连接池中取出连接
                connection = dataSource.getConnection();
                //将连接加入到本地变量
                threadLocal.set(connection);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void freeConnection() {
        //从线程本地变量中取
        Connection connection = threadLocal.get();
        if (connection != null) {
            //清空线程本地变量数据
            threadLocal.remove();
            try {
                //事务状态回顾
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
