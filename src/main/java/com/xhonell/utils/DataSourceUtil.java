package com.xhonell.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class DataSourceUtil {

    private static  DataSource dataSource = null;
    private static Connection connection = null;


    /**
     * 获取数据源对象
     *
     * @return 如果成功加载并创建数据源则返回 DataSource 对象，否则返回 null
     */
    public  static DataSource getDataSource() {
        Properties properties = new Properties();
        try(InputStream inputStream = Files.newInputStream(Paths.get("src/main/resources/druid.properties"));) {
            // 加载 properties 文件
            //InputStream inputStream = DataSourceUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            // 更有效的异常处理
            System.err.println("加载 properties 文件时出错: " + e.getMessage());
            e.printStackTrace();
            return null; // 如果加载失败，返回 null
        }

        try {
            // 使用 DruidDataSourceFactory 创建 DataSource
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            return dataSource;
        } catch (Exception e) {
            // 创建 DataSource 时的异常处理
            System.err.println("创建 DataSource 时出错: " + e.getMessage());
            e.printStackTrace();
            return null; // 如果创建失败，返回 null
        }
    }


    /**
     * 获取数据库连接
     *
     * @return 如果获取成功则返回数据库连接对象，否则返回null
     */

    public static Connection getConnection() {
        DataSource dataSource = getDataSource();
        try {
            if (dataSource != null) {
                connection =  dataSource.getConnection();
                return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭数据库连接
     *
     * 此方法用于关闭已打开的数据库连接。
     * 如果连接对象不为null，则调用其close方法关闭连接。
     * 如果在关闭过程中发生SQLException异常，则捕获该异常并打印堆栈跟踪。
     */
    public static void getClose() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改数据库记录
     *
     * @param sql SQL语句，用于指定要执行的删除操作
     * @param params SQL语句中的参数，以可变参数形式传入
     * @return 成功修改的记录数，如果发生异常则返回-1
     */
    public static int update(String sql, Object... params){
        try {
            DataSource dataSource = getDataSource();
            QueryRunner queryRunner = new QueryRunner(dataSource);
            return queryRunner.update(sql, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 将查询结果映射到一个JavaBean对象中
     *
     * @param <T> 要映射的JavaBean类型
     * @param sql 要执行的SQL查询语句
     * @param cl  JavaBean的Class对象，用于反射创建实例
     * @param params SQL查询语句中的参数，可变参数形式
     * @return 如果查询成功并找到匹配的数据，则返回映射后的JavaBean对象；否则返回null
     * @throws SQLException 如果数据库查询过程中发生异常，则会抛出此异常
     */
    public static <T>T queryToBeanHandler(String sql, Class<T> cl, Object... params) {
        try {
            if (sql == null || sql.trim().isEmpty()){
                return null;
            }
            DataSource dataSource = getDataSource();
            QueryRunner queryRunner = new QueryRunner(dataSource);
            BeanHandler<T> beanHandler = new BeanHandler<>(cl);
            return queryRunner.query(sql, beanHandler, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将查询结果映射到一个JavaBean列表对象中
     *
     * @param <T>   要映射的JavaBean的类型
     * @param sql   要执行的SQL查询语句
     * @param cl    JavaBean的Class对象，用于反射创建实例
     * @param params SQL查询语句中的参数，可变参数形式
     * @return 如果查询成功并找到匹配的数据，则返回映射后的JavaBean列表对象；否则返回null
     * @throws SQLException 如果数据库查询过程中发生异常，则会抛出此异常
     */
    public static <T>List<T> queryToBeanListHandler(String sql, Class<T> cl, Object... params) {
        try {
            if (sql == null || sql.trim().isEmpty()){
                return null;
            }
            DataSource dataSource = getDataSource();
            QueryRunner queryRunner = new QueryRunner(dataSource);
            BeanListHandler<T> beanListHandler = new BeanListHandler<>(cl);
            return queryRunner.query(sql, beanListHandler, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将查询结果映射到一个对象数组中
     *
     * @param sql 要执行的SQL查询语句
     * @param params SQL查询语句中的参数，可变参数形式
     * @return 如果查询成功并找到匹配的数据，则返回包含查询结果的对象数组；否则返回null
     * @throws SQLException 如果数据库查询过程中发生异常，则会抛出此异常
     */
    public static Object[] queryToArrayHandler(String sql, Object... params) {
        try {
            if (sql == null || sql.trim().isEmpty()){
                return null;
            }
            DataSource dataSource = getDataSource();
            QueryRunner queryRunner = new QueryRunner(dataSource);
            ArrayHandler arrayHandler = new ArrayHandler();
            return queryRunner.query(sql, arrayHandler, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 将查询结果映射到一个Object数组列表中
     *
     * @param sql  要执行的SQL查询语句
     * @param params SQL查询语句中的参数，可变参数形式
     * @return 如果查询成功并找到匹配的数据，则返回包含Object数组的列表；否则返回null
     * @throws SQLException 如果数据库查询过程中发生异常，则会抛出此异常
     */
    public static List<Object[]> queryToArrayListHandler(String sql, Object... params) {
        try {
            if (sql == null || sql.trim().isEmpty()){
                return null;
            }
            DataSource dataSource = getDataSource();
            QueryRunner queryRunner = new QueryRunner(dataSource);
            ArrayListHandler arrayListHandler = new ArrayListHandler();
            return queryRunner.query(sql, arrayListHandler, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 将查询结果映射到一个Map对象中
     *
     * @param sql SQL查询语句
     * @param params SQL查询语句中的参数，可变参数形式
     * @return 如果查询成功并找到匹配的数据，则返回映射后的Map对象；否则返回null
     * @throws SQLException 如果数据库查询过程中发生异常，则会抛出此异常
     */
    public static Map<String, Object> queryToMapHandler(String sql, Object... params) {
        try {
            DataSource dataSource = getDataSource();
            QueryRunner queryRunner = new QueryRunner(dataSource);
            MapHandler mapHandler = new MapHandler();
            return queryRunner.query(sql, mapHandler, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将查询结果映射到一个Map列表对象中
     *
     * @param sql   SQL查询语句
     * @param params SQL查询语句中的参数，可变参数形式
     * @return 如果查询成功并找到匹配的数据，则返回映射后的Map列表对象；否则返回null
     * @throws SQLException 如果数据库查询过程中发生异常，则会抛出此异常
     */
    public static List<Map<String, Object>> queryToMapListHandler(String sql, Object... params) {
        try {
            DataSource dataSource = getDataSource();
            QueryRunner queryRunner = new QueryRunner(dataSource);
            MapListHandler mapListHandler = new MapListHandler();
            return queryRunner.query(sql, mapListHandler, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据给定的SQL查询语句，将结果映射到一个以指定列为键的Map中。
     *
     * @param sql    要执行的SQL查询语句
     * @param column 指定为Map键的列名
     * @param params SQL查询语句中的参数，以可变参数形式传入
     * @return 如果查询成功，返回一个Map，其键为指定列的值，值为查询结果映射成的Map；如果查询失败或发生异常，则返回null。
     * @throws SQLException 如果在执行SQL查询时发生异常，将抛出此异常。
     */
    public static Map<Object, Map<String, Object>> queryToKeyHandler(String sql,String column, Object... params) {
        try {
            DataSource dataSource = getDataSource();
            QueryRunner queryRunner = new QueryRunner(dataSource);
            KeyedHandler<Object> objectKeyedHandler = new KeyedHandler<>(column);
            return queryRunner.query(sql, objectKeyedHandler, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将查询结果中的指定列映射到一个对象列表中
     *
     * @param sql    要执行的SQL查询语句
     * @param column 要映射到对象列表中的列名
     * @param params SQL查询语句中的参数，可变参数形式
     * @return 如果查询成功并找到匹配的数据，则返回包含指定列数据的对象列表；否则返回null
     * @throws SQLException 如果数据库查询过程中发生异常，则会抛出此异常
     */
    public static List<Object> queryToColumnListHandler(String sql,String column, Object... params) {
        try {
            DataSource dataSource = getDataSource();
            QueryRunner queryRunner = new QueryRunner(dataSource);
            ColumnListHandler<Object> objectColumnListHandler = new ColumnListHandler<>(column);
            return queryRunner.query(sql, objectColumnListHandler, params);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将聚合查询结果中的单个值映射为一个对象。
     *
     * 该方法执行指定的SQL查询语句，并将查询结果中的单个值映射为一个对象返回。
     * 如果查询成功并找到匹配的数据，则返回该值；否则返回null。
     *
     * @param sql    要执行的SQL查询语句
     * @param params SQL查询语句中的参数，以可变参数形式传入
     * @return 如果查询成功并找到匹配的数据，则返回单个值；否则返回null
     * @throws SQLException 如果数据库查询过程中发生异常，则会抛出此异常
     */
    public static Object queryToScalarHandler(String sql, Object... params) {
        try {
            DataSource dataSource = getDataSource();
            QueryRunner queryRunner = new QueryRunner(dataSource);
            ScalarHandler<Object> objectScalarHandler = new ScalarHandler<>();
            return queryRunner.query(sql, objectScalarHandler, params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

