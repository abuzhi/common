package com.xiao.common.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 懒汉模式，双重检查锁
 */
public class HikariCPUtils {

    private static volatile Connection connection = null;
    private static volatile HikariConfig config = null;
    private static volatile DataSource dataSource = null;

    private HikariCPUtils(){

    }

    public void setUp(Properties properties){
        this.config = new HikariConfig(properties);
    }

    public static Connection getConnection() throws SQLException {
        if(connection==null){
            synchronized (HikariCPUtils.class){
                if(connection==null){
                    connection = getDataSource().getConnection();
                }
            }
        }
        return connection;
    }

    public static DataSource getDataSource() throws SQLException {
        if(dataSource==null){
            synchronized (HikariCPUtils.class){
                if(dataSource==null){
                    dataSource = new HikariDataSource(config);
                }
            }
        }
        return dataSource;
    }

}
