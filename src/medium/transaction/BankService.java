package medium.transaction;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @Author: Diao Rui
 * @className: BankService
 * @Description: 银行卡业务方法，调用Dao方法
 * @DateTime: 2023/7/26 10:46
 * @version: 1.0
 **/
public class BankService {
    @Test
    public void test() {
        String addAccount = "lucy";
        String sunAccount = "tom";
        int money = 10000;
        transform(addAccount, sunAccount, money);
    }

    public void transform(String addAccount, String subAccount, int money) {
        //事务处理
        /*
         * 默认情况下MySQL是一条语句时一个事务，当中途出现异常时前面的SQL语句已经提交了，可能或出现特殊的错误
         * 例如转账钱加了但是减不了
         * */
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql:///stuGrade", "root", "12305dg");
            try {
                BankDao dao = new BankDao();
                //关闭自动提交
                connection.setAutoCommit(false);
                //开始事务
                //加钱
                dao.add(addAccount, money, connection);
                //减钱
                dao.sub(subAccount, money, connection);
                //提交事务
                connection.commit();
            } catch (Exception e) {

                //回滚
                connection.rollback();
                //抛出异常
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
}
