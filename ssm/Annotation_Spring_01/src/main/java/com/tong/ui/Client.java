package com.tong.ui;
        import com.tong.Dao.impl.AccountDaoImpl;
        import com.tong.service.impl.AccountServiceImpl;
        import org.springframework.context.ApplicationContext;
        import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 模拟一个表现层,用于调用业务层
 * */
public class Client {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean.xml");
        AccountServiceImpl as = (AccountServiceImpl) context.getBean("accountService");
        System.out.println(as);
        AccountDaoImpl ad = (AccountDaoImpl) context.getBean("accountDao");
        System.out.println(ad);
        as.saveAccount();
    }
}
