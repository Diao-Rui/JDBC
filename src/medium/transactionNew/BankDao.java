package medium.transactionNew;

import medium.utils.JdbcUtilsV2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author: Diao Rui
 * @className: BankDao
 * @Description: 使用JDBCUTIL完成转账业务
 * @DateTime: 2023/7/26 16:02
 * @version: 1.0
 **/
public class BankDao {

    public void add(String addAccount, int money) {
        //从工具类中获取连接
        Connection connection = JdbcUtilsV2.getConnection();
        String sql = "update t_bank set money = money + ? where account = ?";
        int rows = 0;
        //创建statement
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            //设置参数
            preparedStatement.setInt(1, money);
            preparedStatement.setString(2, addAccount);
            //执行SQL语句
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (rows > 0) {
            System.out.println("收账成功");
        } else {
            System.out.println("收账失败");
        }
    }

    public void sub(String subAccount, int money) {
        //从工具类中获取连接
        Connection connection = JdbcUtilsV2.getConnection();
        String sql = "update t_bank set money = money -? where account =?";
        int rows = 0;
        //创建statement
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(sql);
            //设置参数
            preparedStatement.setInt(1, money);
            preparedStatement.setString(2, subAccount);
            //执行SQL语句
            rows = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (rows > 0) {
            System.out.println("支出成功");
        } else {
            System.out.println("支出失败");
        }
    }
}
