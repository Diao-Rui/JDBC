package medium.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Diao Rui
 * @className: BaseDao
 * @Description: 封装DAO数据库重复代码
 * 封装两个方法：非DQL
 * DQL
 * @DateTime: 2023/7/26 16:29
 * @version: 1.0
 **/
public abstract class BaseDao {
    /*
     * 封装简化非DQL语句
     * @param sql
     * @param args
     * @return 影响返回的行数
     * */
    public int executeUpdate(String sql, Object... args) {
        //获取连接
        Connection connection = JdbcUtilsV2.getConnection();
        PreparedStatement preparedStatement = null;
        int rows = 0;
        try {
            preparedStatement = connection.prepareStatement(sql);
            //设置参数
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
            rows = preparedStatement.executeUpdate();
            return rows;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (preparedStatement != null)
                    preparedStatement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                //判断是否有开启事务，没开启就关闭
                if (connection.getAutoCommit())
                    JdbcUtilsV2.freeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /*
     * 表中的字段与javaBean中的属性一一对应
     * 不确定的类型就用泛型代替
     * 声明一个泛型，不确定类型user.Class = user
     * 使用反射技术给属性赋值
     * @param clazz 要接值的是实体类的集合的模板对象
     * @param SQL 查询语句，要求列名或者别名等于实体类的属性名
     * @param args 占位符的值 要和？位置对象传递
     * @return <T>声明的
     * */
    public <T> List<T> executeQuery(Class<T> clazz, String sql, Object... args) throws Exception {
        Connection connection = JdbcUtilsV2.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        if (args != null && args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }
        }
        ResultSet resultSet = preparedStatement.executeQuery();
        List<T> list = new ArrayList<>();
        while (resultSet.next()) {
            /*
             * 获取构造器
             * 设置构造器可以被访问（打破封装性）
             * 获取元数据
             * 通过元数据获得列名
             * */
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            ResultSetMetaData metaData = resultSet.getMetaData();
            T t = constructor.newInstance();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                Field field = clazz.getDeclaredField(metaData.getColumnLabel(i));
                field.setAccessible(true);
                field.set(t, resultSet.getObject(i));
            }
            list.add(t);
        }
        resultSet.close();
        preparedStatement.close();
        if (connection.getAutoCommit()) {
            JdbcUtilsV2.freeConnection();
        }
        return list;
    }
}
