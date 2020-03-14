package com.tong.service.factory;

import com.tong.service.impl.AccountServiceImpl;

public class InstanceFactory {

    public static AccountServiceImpl getAccountServiceImpl(){
        return new AccountServiceImpl("z");
    }
}
