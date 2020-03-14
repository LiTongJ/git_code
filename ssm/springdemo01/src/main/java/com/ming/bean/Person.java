package com.ming.bean;


/**
 * Person p = new Person
 * p.setpName("arvin");
 *
 *
 * p.getpName();
 */

public class Person {

    //DI
    private String pName;

    private int pAge;


    public Person(){

    }

    public Person(String name, int age){
        this.pName = name;
        this.pAge = age;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public int getpAge() {
        return pAge;
    }

    public void setpAge(int pAge) {
        this.pAge = pAge;
    }

    /**
     * sayHello is defined by arvin
     * @return
     */
    public void sayHello(){
        System.out.println("hello nice to meeting you");
    }

    @Override
    public String toString() {
        return "Person{" +
                "pName='" + pName + '\'' +
                '}';
    }
}
