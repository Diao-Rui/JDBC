package medium.utils;

import medium.entity.TBank;
import org.junit.Test;

import java.util.List;

/**
 * @Author: Diao Rui
 * @className: UtilsTest
 * @Description: TODO
 * @DateTime: 2023/7/26 16:48
 * @version: 1.0
 **/
public class UtilsTest extends BaseDao {
    @Test
    public void testInsert() {
        String sql = "insert into t_bank (account,money) values(?,?)";
        int rows = executeUpdate(sql, "mimi", 1000);
        if (rows > 0) {
            System.out.println("插入成功");
        } else {
            System.out.println("插入失败");
        }
    }
    @Test
    public void testQuery(){
        String sql = "select * from t_bank";
        try {
            List<TBank> tBanks = executeQuery(TBank.class, sql);
            System.out.println(tBanks);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
