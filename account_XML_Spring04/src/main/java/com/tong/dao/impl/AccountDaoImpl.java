package com.tong.dao.impl;

import com.tong.dao.IAccountDao;
import com.tong.domain.Account;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Repository("accountDao")
public class AccountDaoImpl implements IAccountDao {

    @Autowired
    private QueryRunner runner;

    @Override
    public List<Account> findAll() {
        try {
            return runner.query("select * from account",new BeanListHandler<Account>(Account.class));
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Account findAccoundById(int id) {
        try {
            return runner.query("select * from account where id = ?",
                    new BeanHandler<Account>(Account.class),id);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }
}
