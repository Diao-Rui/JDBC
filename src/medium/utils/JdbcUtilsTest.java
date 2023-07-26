package medium.utils;

import org.junit.Test;

import java.sql.Connection;

/**
 * @Author: Diao Rui
 * @className: JdbcUtilsTest
 * @Description: TODO
 * @DateTime: 2023/7/26 15:17
 * @version: 1.0
 **/
public class JdbcUtilsTest {
    @Test
    public void test(){
        Connection connection = JdbcUtils.getConnection();
        Connection connection1 = JdbcUtils.getConnection();
        System.out.println(connection1 == connection);
        JdbcUtils.releaseConnection(connection1);
        JdbcUtils.releaseConnection(connection);
    }
}
