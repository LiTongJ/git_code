package com.tong.service.impl;


import com.tong.dao.IAccountDao;
import com.tong.domain.Account;
import com.tong.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private IAccountDao accountDao;

    @Override
    public List<Account> findAll() {
        return accountDao.findAll();
    }

    @Override
    public Account findAccoundById(int id) {
        return accountDao.findAccoundById(id);
    }
}
