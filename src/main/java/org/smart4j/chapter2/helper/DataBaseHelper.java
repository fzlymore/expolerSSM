package org.smart4j.chapter2.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.PropUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by 魂之挽歌 on 2017/11/21.
 * 数据库操作助手类
 */
public final class DataBaseHelper {
     private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseHelper.class);

    private static final String DRVIER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        Properties conf = PropUtil.loadProps("config.properties");
        DRVIER=conf.getProperty("jdbc.driver");
        URL=conf.getProperty("jdbc.url");
        USERNAME=conf.getProperty("jdbc.username");
        PASSWORD=conf.getProperty("jdbc.password");
        try {
            Class.forName(DRVIER);
        }catch (Exception e){
          LOGGER.error("cant load jdbc driver",e);
        }
    }

    /**
     * 获取数据库连接
     */
    public static Connection getConnection(){
        Connection conn = null;
        try {
            conn= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        }
        catch (SQLException e){
            LOGGER.error("get SqlConnection failure",e);
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            }catch (SQLException e){
                LOGGER.error("closeConnection failure",e);
            }
        }
    }
}
