package com.tong.service.impl;

import com.tong.service.IAccountService;

import java.util.*;

/**
 * 账户的业务层实现类
 */
public class AccountServiceImpl3 implements IAccountService {

    private String[] myStrings;
    private List<String> myLists;
    private Set<String> mySetes;
    private Map<String,String> myMaps;
    private Properties myProperties;

    public void setMyProperties(Properties myProperties) {
        this.myProperties = myProperties;
    }

    public void setMyStrings(String[] myStrings) {
        this.myStrings = myStrings;
    }

    public void setMyLists(List<String> myLists) {
        this.myLists = myLists;
    }

    public void setMySetes(Set<String> mySetes) {
        this.mySetes = mySetes;
    }

    public void setMyMaps(Map<String, String> myMaps) {
        this.myMaps = myMaps;
    }

    @Override
    public void saveAccount() {
        System.out.println(Arrays.toString(myStrings));
        System.out.println(myLists);
        System.out.println(mySetes);
        System.out.println(myMaps);
        System.out.println(myProperties);
    }
}
