# JVM虚拟机运行时数据区

> `想要真正了解各个区执行程序时究竟发生了什么,我们还是从代码开始`

```java
public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        main.compute();
    }

    private int compute() {
        int a = 1;
        int b = 2;
        int c = (a + b)*10;
        return c;
    }
}
```
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303155413783.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

> **准备阶段**:

- 通过javac命令把Main.java编译成字节码文件Main.class
- 通过类装载子系统把字节码文件装在到方法区

> **运行阶段**:

- 先执行main()方法,在栈上开辟一块栈帧.(这个栈就是数据结构中的栈,先进后出)
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303160936176.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
- 然后执行局部变量` Main main = new Main();`
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020030316240190.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

- 再执行`main.compute()`;
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303162903758.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

- 然后执行compute()方法,在栈区开辟一块栈帧
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303163142115.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303163718212.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
- 然后执行`int a= 1;`
	- 将int类型常量1压入栈
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020030316461843.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
	- 将int类型值存入局部变量表1;
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303165015995.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
- 同理,第二个`int b = 2`同理
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303165207365.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)






- 第三个`int c = (a+b)*10;`取出a,b相加,再乘以10,放入局部表量表
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303165539800.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

- 执行完之后return,通过方法出口,返回到main()方法中的调用那一行,compute()-栈帧出栈;
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303170133838.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
- main()执行完之后,main()-栈帧出栈
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303170410634.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
-  然后堆区的main对象没有被引用了,会被gc掉
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303170640816.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)


- 线程结束后,程序结束.

# 运行时数据区
- **线程`私有`区域:程序计数器、Java虚拟机栈、本地方法栈**
- **线程`共享`区域:Java堆、方法区、运行时常量池** 


![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303173018186.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
> `程序计数器`

- 程序计数器是一块比较小的内存空间，可以看做是当前线程所执行的字节码的行号指示器。
- 如果当前线程正在执行的是一个Java方法，这个计数器记录的是正在执行的虚拟机字节码指令的地址；
- 如果正在执行的是一个Native方法，这个计数器值为空。
- **程序计数器内存区域是唯一一个在JVM规范中没有规定任何OOM情况的区域！** 
----

> 什么是线程私有? 


此**为了切换线程后能恢复到正确的执行位置，**`每条线程都需要独立的程序计数器，各条线程之间计数器互不影响，独立存储`。我们就把类似这类区域称之为"**线程私有**"的内存。 

---
>
> `java虚拟机栈`

**虚拟机栈描述的是Java方法执行的内存模型** : 每个方法执行的同时都会创建一个栈帧用于**存储局部变量表、操作数栈、动态链接、方法出口**等信息。每一个方法从调用直至执行完成的过程，就对应一个栈帧在虚拟机栈中入栈和出栈的过程。声明周期与线程相同 .

> # [JVM数据区 ----方法执行的详细步骤](https://blog.csdn.net/weixin_43508555/article/details/104634186)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200303173848285.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
此区域一共会产生以下两种异常:
-  如果线程请求的栈深度大于虚拟机所允许的深度(-Xss设置栈容量)，将会抛出StackOverFlowError异常。
-  虚拟机在**动态扩展时无法申请到足够的内存**，会抛出OOM(OutOfMemoryError)异常 

----

> `什么是动态链接?`

根据对象的头指针在方法区中找到指令码的内存地址,放到动态链接的区域中,运行时动态生成

---


>
> `本地方法栈` 

- 本地方法栈与虚拟机栈的作用完全一样，他俩的区别无非是:
	- 本地方法栈为虚拟机使用的Native方法服务，
	- 而虚拟机栈为JVM执行的Java方法服务。
- 在HotSpot虚拟机中，本地方法栈与虚拟机栈是同一块内存区域。 
- 底层由C语言实现
---

> `Java堆` 

 - Java堆(Java Heap)是JVM所管理的最大内存区域。Java堆是所有线程共享的一块区域，在JVM启动时创建。此内存区域存放的都是对象实例。JVM规范中说到:"`所有的对象实例以及数组都要在堆上分配`"。
- Java堆是垃圾回收器管理的主要区域，因此很多时候可以称之为"GC堆"。根据JVM规范规定的内容，Java堆可以处于物理上不连续的内存空间中。
- Java堆在主流的虚拟机中都是可扩展的(-Xmx设置最大值,-Xms设置最小值)。
- 如果在堆中**没有足够的内存完成实例分配并且堆也无法再拓展时，将会抛出OOM** 
---

> `方法区`

- 它用于存储已被虚拟机它用于存储已被虚拟机**加载的类信息、常量、静态变量**、即使编译器`编译后的代码等数据`。在JDK8以前的HotSpot虚拟机中，方法区也被称为"永久代"(JDK8已经被元空间取代)。 

- 永久代并不意味着数据进入方法区就永久存在，此区域的内存回收主要是针对常量池的回收以及对类型的卸载。
- 当方法区无法满足内存分配需求时，将抛出OOM异常 
----


> `运行时常量池(方法区的一部分) `

- 运行时常量池是方法区的一部分，存放字面量与符号引用。
- **字面量** : 字符串(JDK1.7后移动到堆中) 、final常量、基本数据类型的值。
- **符号引用** : 类和结构的完全限定名、字段的名称和描述符、`方法的名称`和描述符。 

---

> `Integer a = 1`和`Integer b = 200`的存储有什么区别
>

 Integer所定义的值的大小如果在`(-128 ~ 127)`之间时**存放在常量池中**,比如
- `Integer  c = 1;`,`a == c`是`true`;
- 但`Integer  d = 200`时`b != d`;
---
> `String a = "123";`与`String b = new String("123);`的存储有什么区别
>

 ## [多种实例详细分析字符串的各种存放情况,面试重点考察点](https://blog.csdn.net/weixin_43508555/article/details/102574874)
---


> `Java中基本数据类型和引用数据类型的存放位置`

## [Java中基本数据类型和引用数据类型的存放位置](https://blog.csdn.net/weixin_43508555/article/details/104553670)
----