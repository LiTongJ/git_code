package com.ming;


import com.ming.bean.Person;
import com.ming.bean2.Bean1;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest2 {

    private Bean1 bean1 = null;

    @Before
    public void testInitial(){

        ApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"applicationContext-beans.xml"});
        bean1 = (Bean1) context.getBean("bean1");
    }

    @Test
    public void testMethod(){
        System.out.println("bean1 strValue = " + bean1.getStrValue());
        System.out.println("bean1 listValue = " + bean1.getListValue());
        System.out.println("bean1 setValue = " + bean1.getSetValue());
        System.out.println("bean1 intValue = " + bean1.getIntValue());
        System.out.println("bean1 mapValue = " + bean1.getMapValue());
        System.out.println("bean1 arrayValue = " + bean1.getArrayValue());

        //date
        System.out.println("bean1 dateValue = " + bean1.getDateValue());
    }
}
