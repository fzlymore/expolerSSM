package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DataBaseHelper;
import org.smart4j.chapter2.model.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 魂之挽歌 on 2017/11/21.
 */
public class CustomerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    /**
     * 获取客户列表
     * @param keyword
     * @return
     */
    public List<Customer> getCustomerList (String keyword){
        Connection conn = null;
        List<Customer> customerList = new ArrayList<>();
        try {
            String sql = "select * from customer";
            conn = DataBaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setContact(rs.getString("contact"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setEmail(rs.getString("email"));
                customer.setRemark(rs.getString("remark"));
                customerList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error("excute sql failure",e);
        }
//        finally {
//            DataBaseHelper.closeConnection();
//        }
        return  customerList;
    }

    /**
     * 获取客户列表之简单方法（dbUtils）
     */
    public List<Customer> getCustomerList(){
        Connection conn = DataBaseHelper.getConnection();
        String sql = "select * from customer";
        return DataBaseHelper.queryEntityList(Customer.class,sql,conn);
    }

    /**
     * 获取客户列表方法（改进dbutils后）
     */
    public List<Customer> getCustomerList1(){
        String sql = "SELECT * FROM customer";
        return DataBaseHelper.queryEntityList(Customer.class,sql);
    }

    /**
     * 获取客户
     */
    public Customer getCustomer(Long id){
        // TODO: 2017/11/21
        return  null;
    }

    /**
     * 创建用户
     */
    public boolean createCustomer (Map<String,Object> fieldMap){
        // TODO: 2017/11/21
//        return false;
        return DataBaseHelper.insertEntity(Customer.class,fieldMap);
    }

    /**
     * 更新用户
     */
    public boolean updateCustomer (Long id, Map<String,Object> fieldMap){
        // TODO: 2017/11/21
//        return  false;
        return DataBaseHelper.updateEntity(Customer.class,id,fieldMap);
    }

    /**
     * 删除用户
     */
    public boolean deleteCustomer(Long id){
        // TODO: 2017/11/21
//        return  false;
        return DataBaseHelper.deleteEntity(Customer.class,id);
    }
}
