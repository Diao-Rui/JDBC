package medium.transactionNew;

import medium.utils.JdbcUtilsV2;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @Author: Diao Rui
 * @className: BankService
 * @Description: TODO
 * @DateTime: 2023/7/26 16:02
 * @version: 1.0
 **/
public class BankService {
    @Test
    public void test() {

        String addAccount = "tom";
        String subAccount = "lucy";
        int money = 10000;
        transform(addAccount, subAccount, money);
    }

    public void transform(String addAccount, String subAccount, int money) {
        BankDao bankDao = new BankDao();
        //从工具类中获取连接
        Connection connection = JdbcUtilsV2.getConnection();
        try {
            //关闭自动提交
            connection.setAutoCommit(false);
            bankDao.add(addAccount, money);
            bankDao.sub(subAccount, money);
            //提交事务
            connection.commit();
        } catch (SQLException e) {
            //事务回滚
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
        JdbcUtilsV2.freeConnection();
    }
}
