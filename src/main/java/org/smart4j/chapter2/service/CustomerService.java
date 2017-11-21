package org.smart4j.chapter2.service;

import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.util.PropUtil;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 魂之挽歌 on 2017/11/21.
 */
public class CustomerService {

    /**
     * 获取客户列表
     * @param keyword
     * @return
     */
    public List<Customer> getCustomerList (String keyword){
        // TODO: 2017/11/21
        return  null;
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
        return false;
    }

    /**
     * 更新用户
     */
    public boolean updateCustomer (Long id, Map<String,Object> fieldMap){
        // TODO: 2017/11/21
        return  false;
    }

    /**
     * 删除用户
     */
    public boolean deleteCustomer(Long id){
        // TODO: 2017/11/21
        return  false;
    }
}
