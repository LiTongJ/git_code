package com.tong.Dao.impl;

import com.tong.Dao.IAccountDao;

public class AccountDaoImpl implements IAccountDao {
    @Override
    public void saveAccount() {
        System.out.println("保存账户成功");
    }
}
