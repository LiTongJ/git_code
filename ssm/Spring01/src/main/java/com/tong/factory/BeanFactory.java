package com.tong.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 一个创建Bean对象的工厂
 * Bean,在计算机英语中,有可重用组建的含义
 *
 * JavaBean:
 *      不是实体类
 */
public class BeanFactory {
    private static Properties properties ;

    //单例的对象容器
    private static Map<String,Object> beans;
    static{
        //实例化对象
        properties = new Properties();
        InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
        try {
            properties.load(in);
            beans = new HashMap<>();
            Enumeration keys = properties.keys();
            while(keys.hasMoreElements()){
                String key = keys.nextElement().toString();
                String beanpath = properties.getProperty(key);
                Object o = Class.forName(beanpath).newInstance();
                beans.put(key,o);
                System.out.println(key + " ++++++++++++ " + o.toString());
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("properties文件初始化失败");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public static Object getBean(String beanName) {
        System.out.println(beans.get(beanName));
        return beans.get(beanName);
    }
}
