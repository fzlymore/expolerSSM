package org.smart4j.chapter2.helper;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.util.PropUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 魂之挽歌 on 2017/11/21.
 * 数据库操作助手类
 * @author white
 */
public final class DataBaseHelper {
     private static final Logger LOGGER = LoggerFactory.getLogger(DataBaseHelper.class);

    private static final String DRVIER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    //使用dbUtils封装简化代码
    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private static final ThreadLocal<Connection> CONNECTION_HOLDER = new ThreadLocal<>();

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
        Connection conn = CONNECTION_HOLDER.get();
        if(null==conn) {
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                LOGGER.error("get SqlConnection failure", e);
                throw new RuntimeException(e);
            }finally {
                CONNECTION_HOLDER.set(conn);
            }
        }
            return conn;
    }

    /**
     * 关闭数据库连接
     */
    public static void closeConnection(){
        Connection conn = CONNECTION_HOLDER.get();
        if(conn!=null){
            try {
                conn.close();
            }catch (SQLException e){
                LOGGER.error("closeConnection failure",e);
                throw new RuntimeException(e);
            }
            finally {
                CONNECTION_HOLDER.remove();
            }
        }
    }

    /**
     * 查询实体列表
     */
    public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){
        List<T> entityList = null;
        try {
            Connection conn = getConnection();
            entityList= QUERY_RUNNER.query(conn,sql,new BeanListHandler<T>(entityClass),params);
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("query entity list failure",e);
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
      return entityList;
    }

    /**
     * 查询实体单个对象
     */
    public static <T> T queryEntity(Class<T>entityClass,String sql,Object...params){
        T entity;
        try{
            Connection conn = getConnection();
            entity = QUERY_RUNNER.query(conn,sql,new BeanHandler<T>(entityClass),params);
        }catch (SQLException e){
             LOGGER.error("query entity is fail",e);
            throw new RuntimeException(e);
        }finally {
            closeConnection();
        }
        return entity;
    }


    /**
     * 执行通用查询列表
     * @param sql
     * @param params
     * @return
     */
    public  static List<Map<String,Object>> executeQuery(String sql,Object...params){
        List<Map<String,Object>> result = null;
        Connection conn = getConnection();
        try {
            result = QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
        } catch (SQLException e) {
            LOGGER.error("Execute list fail",e);
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 执行更新语句（包括增删改）
     * @param sql
     * @param params
     * @return
     */
    public static int executeUpdate (String sql, Object...params){
        int rows = 0;
        Connection conn = getConnection();
        try {
            rows = QUERY_RUNNER.update(conn,sql,params);
        } catch (SQLException e) {
           LOGGER.error("executeUpdate is fail ",e);
            throw  new RuntimeException(e);
        }
        return rows;
    }

    private static  String getTableName (Class<?> entityClass){
         return entityClass.getSimpleName();
    }

    /**
     * 插入实体
     * @param entityClass
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T>boolean insertEntity(Class<T> entityClass , Map<String,Object>fieldMap){
        if(MapUtils.isEmpty(fieldMap)){
            LOGGER.error("cant insert entity : fieldMap is empty");
            return false;
        }
        String sql = "insert into" + getTableName(entityClass);
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName: fieldMap.keySet()) {
            columns.append(fieldName).append(",");
            values.append("?,");
        }
        columns.replace(columns.lastIndexOf(","),columns.length(),")");
        values.replace(values.lastIndexOf(","),values.length(),")");
        sql += columns + "values" +values;

        Object [] params = fieldMap.values().toArray();
        return  executeUpdate(sql,params)==1;
    }

    /**
     * 更新实体
     * @param entityClass
     * @param id
     * @param fieldMap
     * @param <T>
     * @return
     */
    public static <T>boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
        if(MapUtils.isEmpty(fieldMap)){
            LOGGER.error("cant update entity : fieldMap is empty");
            return false;
        }
        String sql = "UPDATE" + getTableName(entityClass) + "set";
        StringBuilder columns = new StringBuilder("(");
        for (String fieldName: fieldMap.keySet()) {
            columns.append(fieldName).append("=?,");
        }
        sql += columns.substring(0,columns.lastIndexOf(","))+"where id =?";
        List<Object> paramList = new ArrayList<>();
        paramList.addAll(fieldMap.values());
        paramList.add(id);
        Object [] params = fieldMap.values().toArray();
        return  executeUpdate(sql,params)==1;
    }

    public static <T>boolean deleteEntity(Class<T> entityClass,long id){
        String sql = "DELETE FROM"+ getTableName(entityClass)+"where id = ?";
        return executeUpdate(sql,id)==1;
    }

}
