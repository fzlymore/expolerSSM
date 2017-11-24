package org.smart4j.chapter2.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.smart4j.chapter2.helper.DataBaseHelper;
import org.smart4j.chapter2.model.Customer;
import org.smart4j.chapter2.service.CustomerService;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 魂之挽歌 on 2017/11/21.
 *单元测试
 */
public class CustomerServiceTest {
  private final CustomerService customerService;

    public CustomerServiceTest(){
        customerService = new CustomerService();
    }

    @Before
    public void init () throws Exception{
        // TODO: 2017/11/21 初始化数据库
//        String file = "sql/customer_init.sql";
//        InputStream is =Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
//        String sql ;
//        while ((sql=reader.readLine())!=null){
//            DataBaseHelper.executeUpdate(sql);
//        } 抽出方法到dbHelper去
        DataBaseHelper.executeSqlFile("sql/customer_init.sql");
    }

    /**
     * 根据id查找客户
     * @throws Exception
     */
    @Test
    public void getCustomerTest() throws Exception{
      long id = 1;
        Customer customer = customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }

    /**
     * 创建一个用户的信息
     */
    @Test
    public void createCustomerTest() throws Exception{
        Map<String,Object> fieldMap = new HashMap<>();
        fieldMap.put("name","customer100");
        fieldMap.put("contact","John");
        fieldMap.put("telephone","13512345678");
        boolean result = customerService.createCustomer(fieldMap);
        Assert.assertTrue(result);
    }




}
