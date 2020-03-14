package com.ming;


import com.ming.bean.UserInfo;
import com.ming.bean2.Bean1;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest3 {

    private UserInfo userInfo;

    @Before
    public void testInitial(){
        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext-beans.xml"});
        userInfo = (UserInfo) context.getBean("userinfo");

    }

    @Test
    public void testMethod(){
        System.out.println(userInfo.getPerson().getpName() + ", " + userInfo.getPerson().getpAge());
    }
}
