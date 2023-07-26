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
 * @DateTime: 2023/7/26 15:03
 * @version: 1.0
 **/
public class JdbcUtils {
    private static DataSource dataSource = null;

    static {
        //初始化连接池的对象
        Properties properties = new Properties();
        //获取输入流（通过类加载机制）
        InputStream inputStream = JdbcUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        try {
            //加载配置文件
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            //创建连接池（从数据源工厂中创建连接池）
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
     * 对外提供连接的方法
     * @return
     * */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    /*
     * 回收连接的方法
     * */
    public static void releaseConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
