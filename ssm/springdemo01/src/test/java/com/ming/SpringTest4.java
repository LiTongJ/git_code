package com.ming;


import com.ming.bean.UserInfo;
import com.ming.bean2.Bean2;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest4 {

    private Bean2 bean2;
    private Bean2 bean2_1;
    ApplicationContext context;

    @Before
    public void testInitial(){
      context  = new ClassPathXmlApplicationContext(new String[]{"applicationContext-beans2.xml"});


    }

    @Test
    public void testMethod(){
//        System.out.println("===========bean3==============");
//        System.out.println(bean2.getBean3().getId());
//        System.out.println(bean2.getBean3().getPassword());
//        System.out.println(bean2.getBean3().getUsername());
//        System.out.println("===========bean4==============");
//        System.out.println(bean2.getBean4().getId());
//        System.out.println(bean2.getBean4().getUsername());
//        System.out.println("===========bean5==============");
//        System.out.println(bean2.getBean5().getAge());
    }

    @Test
    public void testMethod2(){
        bean2 = (Bean2) context.getBean("bean2");
        bean2_1 = (Bean2)context.getBean("bean2");
        if(bean2 == bean2_1){
            System.out.println("bean2 is singleton");
        }else{
            System.out.println("bean2 is multible");
        }

    }
}
