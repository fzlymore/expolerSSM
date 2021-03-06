package org.smart4j.chapter2.model;

/**
 * Created by 魂之挽歌 on 2017/11/21.
 * 客户
 * @author white
 */
public class Customer {
    /**
    id
     */
    private long id ;

    /**
     * 客户名称
     */
    private String name ;

    /**
     * 联系人
     */

    private String contact;

    /**
     * 电话号码
     */
    private String telephone;

    /**
     * 邮箱地址
     */

    private String email;

    /**
     * 备注
     */
    private String remark;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
