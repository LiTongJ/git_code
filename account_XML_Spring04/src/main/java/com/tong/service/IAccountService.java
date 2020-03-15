package com.tong.service;

import com.tong.domain.Account;

import java.util.List;

public interface IAccountService {
    List<Account> findAll();
    Account findAccoundById(int id);
}
