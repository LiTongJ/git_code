package com.tong.service.impl;

import com.tong.service.IAccountService;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    public AccountServiceImpl() {
    }

    @Override
    public void saveAccount() {
        System.out.println("创建成功");
    }
}
