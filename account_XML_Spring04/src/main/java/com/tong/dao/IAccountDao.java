package com.tong.dao;

import com.tong.domain.Account;

import java.util.List;

public interface IAccountDao {
    List<Account> findAll();
    Account findAccoundById(int id);
}
