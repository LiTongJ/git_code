package com.tong.service.impl;

import com.tong.service.IAccountService;

import java.util.Date;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl2 implements IAccountService {

    private String name;
    private int age;
    private Date bir;

    @Override
    public void saveAccount() {
        System.out.println("创建成功\\n" + toString());
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setBir(Date bir) {
        this.bir = bir;
    }

    @Override
    public String toString() {
        return "AccountServiceImpl{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", bir=" + bir +
                '}';
    }
}
