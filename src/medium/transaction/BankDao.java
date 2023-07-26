package medium.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * @Author: Diao Rui
 * @className: BankDao
 * @Description: TODO
 * @DateTime: 2023/7/26 10:48
 * @version: 1.0
 **/
public class BankDao {
    public void add(String account, int money, Connection connection) throws Exception {
        String sql = "update t_bank set money = money + ? where account = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, money);
        preparedStatement.setString(2, account);
        preparedStatement.executeUpdate();
        System.out.println("加钱成功");
    }

    public void sub(String account, int money, Connection connection) throws Exception {
        String sql = "update t_bank set money = money -? where account =?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, money);
        preparedStatement.setString(2, account);
        preparedStatement.executeUpdate();
        System.out.println("减钱成功");
    }
}
