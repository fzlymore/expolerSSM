package org.smart4j.chapter2.helper;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.PropUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
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
    //使用dbUtils封装简化代码
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

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

    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();
    /**
     * 获取数据库连接
     */
    public static Connection getConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if (null == conn){
            try {
                conn=DriverManager.getConnection(URL,USERNAME,PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
                LOGGER.error("get SqlConnection failure",e);
                throw new RuntimeException(e);
            } finally {
              CONNECTION_HOLDER.set(conn);
            }
        }
//        try {
//            conn= DriverManager.getConnection(URL,USERNAME,PASSWORD);
//        }
//        catch (SQLException e){
//            LOGGER.error("get SqlConnection failure",e);
//        }
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

    /**
     * 查询实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){
        Connection conn = DataBaseHelper.getConnection();
        List<T> entityList = null;
        try {
            entityList= QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("query entity list failure",e);
        } finally {
            closeConnection(conn);
        }
      return entityList;
    }


}
