package com.ming;


import com.ming.bean.Person;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    private Person person = null;

    @Before
    public void testInitial(){
        //acquire object from spring container
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        person = (Person) context.getBean("person");
    }

    @Test
    public void testMethod(){
        //Person person = new Person();
        person.sayHello();
        System.out.println("pName = " + person.getpName() + ", pAge = " + person.getpAge());
    }
}
