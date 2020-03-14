package com.tong.ui;

import com.tong.factory.BeanFactory;
import com.tong.service.IAccountService;

/**
 * 模拟一个表现层,用于调用业务层
 * */
public class Client {
    public static void main(String[] args) {
        IAccountService as = (IAccountService) BeanFactory.getBean("accountServiceImpl");
        as.saveAccount();
    }
}
