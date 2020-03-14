package com.tong.service.impl;

import com.tong.Dao.IAccountDao;
import com.tong.factory.BeanFactory;
import com.tong.service.IAccountService;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao = (IAccountDao) BeanFactory.getBean("accountDaoImpl");

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }
}
