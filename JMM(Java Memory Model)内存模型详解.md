## JMM(Java Memory Model)内存模型详解
`java线程内存模型`跟CPU缓存模型类似,是基于cpu缓存模型建立的,Java线程内存模型是标准化的,屏蔽掉了底层不同计算机的区别.

### CPU缓存模型
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200304154856347.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)


### JMM![在这里插入图片描述](https://img-blog.csdnimg.cn/20200304154701332.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)



---

> 证明工作内存的存在

```java
public class JMM {
    public static boolean initFlag = false;
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread1 开始.....");
                while(!initFlag){
                }
                System.out.println("initFlag == true");
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread2开始...");
                change();
            }
        }).start();
    }

    private static void change() {
        initFlag = true;
        System.out.println("已将initFlag改为true.....");
    }
}
```
我们本来想的是,第二个线程把`initFlag`改为`true`后,第一个线程跳出循环,并打印`initFlag == true`;我们看看运行结果
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200304161014236.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
我们惊喜的发现,似乎线程1中的initFlag并没有该成true,还在死循环中,由此可见,线程2更改的值并没有对线程1造成影响,由此可以证明,工作内存的存在,各个工作内存是相互独立的

---

> 但是,我们想要的结果是跳出循环,那么我们就可以对共享变量加`volatile`关键字

```java
public static volatile boolean initFlag = false;
```

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200304162921457.png)

----------

> JMM数据原子操作

- read (读取) : 从主内存读取数据
- load (载入) : 将主内存读取到的数据写入工作内存
- use (使用) : 从工作内存读取数据来计算
- assign (赋值) : 将计算好的值重新赋值到工作内存中
- store (存储) : 将工作内存的数据写入主内存
- write (写入) : 将store过去的变量值赋予给主内存中的变量
- lock (锁定) : 将主内存变量加锁,标识为线程独占状态
- unlock(解锁) : 将主内存变量解锁,解锁后的其他线程可以锁定该变量

---
## (无volatile过程)
> 先看前四个原子操作的流程(无volatile)

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200304171242803.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
目前到assign (赋值)操作之后,工作内存中的值更改

---

> store和write

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200304172051134.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

----
## 加上volatile后

> 但这样会出现缓存不一致现象,怎样解决

- 总线枷锁(性能太低)
	- cpu从主内存读取数据到高速缓存,会在总线对这个数据加锁,这样其他cpu无法去读写这个数据,直到这个cpu使用完数据释放锁之后,其他cpu才能读取该数据
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200304173941561.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
- MESI缓存一致性协议
	- 多个cpu从主内存读取同一个数据到各自高速缓存,其中某个cpu修改了缓存里的数据,该数据会马上同步到主内存,其他cpu通过`总线嗅探机制`可以感知到数据的变化从而将自己缓存里的数据失败
![在这里插入图片描述](https://img-blog.csdnimg.cn/2020030417484619.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

- 假如现在有CPU2和CPU1，主内存有变量`initFlag = false` 。现在cpu2要做 `initFlag = true`的操作。
- 如果在变量 ` initFlag = false` 上加上volatile，则就会触发MESI
- 当CPU1从主内存中读取到`initFlag = false`时，CPU1会把此变量标记成独享状态
- 并监听总线，是否有其它CPU去读取此变量
- 当CPU2从主内存中读取`initFlag = false`变量时，CPU1会通过嗅探机制监听到。
- 此时CPU2的`initFlag`变量会变成共享状态。继续进行赋值，赋值完变成`initFlag = true`。
- 此时要回写到主内存之前。先锁住缓存行。并标记`initFlag`变量为修改状态。并向总线发消息。
- CPU1监听总线时，会监听到，并把`initFlag`标记成无效状态。
- CPU2把变量`initFlag = true`回写到主内存后，会由修改状态变成独享状态。
- 此时，如果CPU1如果想修改X变量时，要重启从主内存中读取。然后开始新的轮回。

---

## volatile关键字的两个作用
- 关键字volatile可以说是JVM提供的最轻量级的同步机制，但是它并不容易完全被正确理解和使用。JVM内存模型对volatile专门定义了一些特殊的访问规则。
- 当一个变量定义为volatile之后，它将具备两种特性。 

> `可见性`

**第一是保证此变量对所有线程的可见性**，这里的"可见性"是指 : 当一条线程修改了这个变量的值，新值对于其他线程来说是可以立即得知的。而普通变量做不到这一点，普通变量的值在线程间传递均需要通过主内存来完成。

> 例如:线程A修改一个普通变量的值，然后向主内存进行回写，另外一条线程B在线程A回写完成之后再从主内存进行读取操作，新值才会对线程B可见。

**关于volatile变量的可见性，经常会被开发人员误解。volatile变量在各个线程中是一致的，但是volatile变量的运算在并发下一样是不安全的。原因在于`Java里面的运算并非原子操作`。** 

---
> `有序性`

**使用volatile变量的第二个语义是禁止指令重排序**。普通的变量仅仅会保证在该方法的执行过程中所有依赖赋值结果的地方都能获取到正确的结果，而不能保证变量赋值操作的顺序和程序代码中执行的顺序一致。
volatile关键字禁止指令重排序有两层意思：
- 当程序执行到volatile变量的读操作或者写操作时，在其前面的操作的更改肯定全部已经进行，且结果已经对后面的操作可见；在其后面的操作肯定还没有进行；
- 在进行指令优化时，不能将在对volatile变量访问的语句放在其后面执行，也不能把volatile变量后面的语句
放到其前面执行。 

> 举个简单的例子

```java
//x、y为非volatile变量
//flag为volatile变量
x = 2; //语句1
y = 0; //语句2
flag = true; //语句3 
x = 4; //语句4
y = -1;//语句5 
```

- 由于flag变量为volatile变量，那么在进行指令重排序的过程的时候，不会将语句3放到语句1、语句2前面，也不会将语句3放到语句4、语句5后面。 **但是要注意语句1和语句2的顺序、语句4和语句5的顺序是不作任何保证的**。
 -并且volatile关键字能保证，执行到语句3时，语句1和语句2必定是执行完毕了的，且语句1和语句2的执行结果对语句3、语句4、语句5是可见的。

---


> ### `但是不能保证程序的原子性`

```java
public class VolatileAtomic {
    public static volatile int num = 0;
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[100];
        for (int i = 0; i <  threads.length;i++){
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 100; j++) {
                        num++;
                    }
                }
            });
            threads[i].start();
        }
        for (Thread t:threads
             ) {
            t.join();
        }
        System.out.println(num);
    }
}
```
理想情况下打印出来是10000,但是:
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200304191159824.png)
- 问题就在于num++之中，实际上num++等同于num = num+1。volatile关键字保证了num的值在取值时是正确的，但是在执行num+1的时候，其他线程可能已经把num值增大了，这样在+1后会把较小的数值同步回主内存之中。
- 由于volatile关键字只保证可见性，在不符合以下两条规则的运算场景中，我们仍然需要通过加锁(synchronized或者lock)来保证原子性。
	1. 运算结果并不依赖变量的当前值，或者能够确保只有单一的线程修改变量的值
	2. 变量不需要与其他的状态变量共同参与不变约束 

**volatile变量在各个线程中是一致的，但是volatile变量的运算在并发下一样是不安全的。原因在于Java里面的运算并非原子操作** 

---
## 单例模式
> 单例模式的双重校验锁

是一种使用同步块加锁的方法。程序员称其为双重检查锁，因为会有两次检查 instance == null，一次是在同步块外，一次是在同步块内。为什么在同步块内还要再检验一次？**因为可能会有多个线程一起进入同步块外的 if，如果在同步块内不进行二次检验的话就会生成多个实例了。** 



**错误代码:**
```java
class Singleton{
    private static Singleton instance = null;
    private Singleton() {
    }
    public static Singleton getInstance() {
        if(instance==null) {
            synchronized (Singleton.class) {
                if(instance==null)
                    instance = new Singleton();
            }
        }
        return instance;
    }
} 
 
```
- 这段代码看起来很完美，很可惜，它是有问题。
- 主要在于instance = new Singleton()这句，这并非是一个原子操作，事实上在 JVM 中这句话大概做了下面 3 件事情。
	-  给 instance 分配内存 
	- 调用 Singleton 的构造函数来初始化成员变量 
	- 将instance对象指向分配的内存空间（执行完这步 instance 就为非 null 了）
- 但是在 JVM 的即时编译器中存在指令重排序的优化。也就是说上面的第二步和第三步的顺序是不能保证的，最终的执行顺序可能是 1-2-3 也可能是 1-3-2。如果是后者，则在 3 执行完毕、2 未执行之前，被线程二抢占了，这时 instance 已经是非 null 了（但却没有初始化），所以线程二会直接返回 instance，然后使用，然后顺理成章地报错。
-  我们只需要将 instance 变量声明成 volatile 就可以了。

---

**正确代码:**
```java
class Singleton{
    private static volatile Singleton instance = null;
    private Singleton() {
    }
    public static Singleton getInstance() {
        if(instance==null) {
            synchronized (Singleton.class) {
                if(instance==null)
                    instance = new Singleton();
            }
        }
        return instance;
    }
} 

```
volatile和synchronized共同保证了程序的有序性,原子性和可见性

## 深浅拷贝(对象)

`Cloneable:CloneNotSupportedException`
- 只有子类实现了Cloneable接口后才可以使用Object类提供的clone方法。

```java
protected native Object clone() throws CloneNotSupportedException;
```

 - 要想让对象具有拷贝的功能,必须实现Cloneable接口(标识接口，表示此类允许被clone),并且在类中自定义clone调用Object类提供的继承权限clone方法
- `浅拷贝`:**对象值拷贝对于浅拷贝而言，拷贝出来的对象仍然保留原对象的所有引用**。
	
- 问题:**牵一发而动全身**只要任意一个拷贝对象(或原对象)中的引用发生改变，所有对象均会受到影响。
	
- `深拷贝`:**深拷贝，拷贝出来的对象产生了所有引用的`新的对象`**。
- ---


> 如何实现深拷贝

1. 包含的其他类继续实现Cloneable接口，并且调用clone方法(递归实现Clone) 
2. 使用序列化(*****)使用序列化进行深拷贝时，无须再实现Cloneable接口，只需要实现Serializable即可。

```java
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

class Teacher implements Serializable {
    private String name;
    private String job;

    public Teacher(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}

class Student implements Serializable{

    private String name;
    private int age;
    private Teacher teacher;

    public Student(String name, int age, Teacher teacher) {
        this.name = name;
        this.age = age;
        this.teacher = teacher;
    }

    public Student cloneObject() throws Exception{
        ByteOutputStream bos = new ByteOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(this);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.getBytes());
        ObjectInputStream ois = new ObjectInputStream(bis);
        return (Student) ois.readObject();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

}
public class Test {
    public static void main(String[] args) throws Exception{
        Teacher teacher = new Teacher("小明","Java Teacher");
        Student student = new Student("小李",18,teacher);
        Student studentClone = student.cloneObject();
        System.out.println(student);
        System.out.println(studentClone);
        System.out.println("=================");
        System.out.println(studentClone.getName());
        System.out.println(studentClone.getAge());
        System.out.println(studentClone.getTeacher().getName());
        System.out.println(studentClone.getTeacher().getJob());
        System.out.println("------------------------------");
        System.out.println(teacher == studentClone.getTeacher());
    }
}
```
代码输出

```java
tong.day2.Student@135fbaa4
tong.day2.Student@6acbcfc0
=================
小李
18
小明
Java Teacher
------------------------------
false
```