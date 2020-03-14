package com.tong.service.impl;

import com.tong.Dao.IAccountDao;
import com.tong.service.IAccountService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 账户的业务层实现类
 * <bean id="accountService" class="com.tong.service.impl.AccountServiceImpl"></bean>
 *
 * 创建对象
 *      @Component 对象默认为类名小写
 *
 *       Controller : 表现层
 *       Service : 业务层
 *       Repository : 持久层
 *       与Component一样
 * 注入数据
 *      Autowired
 * 作用范围
 * 生命周期
 */

@Service("accountService")
public class AccountServiceImpl implements IAccountService {

    private IAccountDao accountDao ;
//            (IAccountDao) new ClassPathXmlApplicationContext
//                    ("bean.xml").getBean("accountDao");

    public AccountServiceImpl() {

        System.out.println("对像创建111");
    }

    @Override
    public void saveAccount() {
        accountDao.saveAccount();
    }
}
