## 目录结构
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200311195456923.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)s
## 第一步 : 配置Pom文件
加载mybatis与mysql等依赖
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.tong</groupId>
    <artifactId>mybatis02</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging>

    <dependencies>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.6</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.1</version>
        </dependency>
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.12</version>
        </dependency>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.10</version>
            <scope>test</scope>
        </dependency>

    </dependencies>

</project>
```
## 第二步 : 加入log4j.properties文件

```xml
log4j.rootLogger=DEBUG, Console

#Console
log4j.appender.Console=org.apache.log4j.ConsoleAppender
log4j.appender.Console.layout=org.apache.log4j.PatternLayout
log4j.appender.Console.layout.ConversionPattern=%d [%t] %-5p [%c] - %m%n

log4j.logger.java.sql.ResultSet=INFO
log4j.logger.org.apache=INFO
log4j.logger.java.sql.Connection=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.
```

## 第三步 : 创建数据库

```sql
create database dir;

use dir;

create table UserInfo (
userId int not null auto_increment,
userName varchar(32) not null comment '用户名',
userSex varchar(20) not null comment '性别',
primary key (userId)
);

insert into UserInfo (userId,userName,userSex) values 
(1,"aaa","男"),
(2,"bbb","男"),
(3,"ccc","女"),
(4,"a","男"),
(5,"b","男"),
(6,"c","男");
```
## 第四步 : 创建一个类,接收数据库的值(类名不用等于表名)
类中的属性与数据库表中的属性一致,书写getter()和setter()方法,还有toString()方法

```java
public class UserInfo1 {

   private int userId;

   private String userName;

   private String userSex;

   public UserInfo1(){

   }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    @Override
    public String toString() {
        return "UserInfo1{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userSex='" + userSex + '\'' +
                '}';
    }
}
```
## 第五步 : 创建一个接口,里面放访问数据库的方法

```java
public interface IUserDao {
    UserInfo1 queryUserById(int id) throws Exception;
    
    List<UserInfo1> findAll();
}
```
## 第六步 : 编写mapper文件,与接口名保持一致

放置sql语句,实现方法的作用

```xml
<?xml version="1.0"  encoding="UTF-8"?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">


<mapper namespace="com.tong.Dao.IUserDao">
    
    <select id="findAll" resultType="com.tong.task.UserInfo1">
        select * from UserInFo;
    </select>
    
    <select id="queryUserById" parameterType="int" resultType="com.tong.task.UserInfo1">
        select * from UserInFo where userid = #{myId};
    </select>
</mapper>
```
## 第七步 : 与数据库连接,创建SqlMapConfig.xml
**`url后面的是database`**
resource是相对于`SqlMapConfig.xml`的**`相对路径`**
```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="mysql">
        <environment id="mysql">
            <transactionManager type="JDBC">
            </transactionManager>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url"
                          value="jdbc:mysql://localhost:3306/dir"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>
    
    <mappers>
         <mapper resource="mapper/IUserDao.xml"/>
    </mappers>
</configuration>
```
## `第八步 : Test 5步走`
Resources的包
```java
import org.apache.ibatis.io.Resources;
```
- **1.读取配置文件**
- **2.创建SqlSessionFactory工厂**
- **3.使用工厂生产sqlSession对象**
- **4.使用sqlSession创建DAO接口的代理对像**
 - **5.使用代理对象执行方法**
```java
public class Test{
    public static void main(String[] args) throws Exception {
        //1.读取配置文件
        InputStream in = Resources.getResourceAsStream("SqlMapConfig.xml");
        //2.创建SqlSessionFactory工厂
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);
        //3.使用工厂生产sqlSession对象
        SqlSession sqlSession = factory.openSession();
        //4.使用sqlSession创建DAO接口的代理对像
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        //5.使用代理对象执行方法
        List<UserInfo1> users = userDao.findAll();
        for (UserInfo1 user: users) {
            System.out.println(user);
        }
       
        System.out.println("===============================");
        
        //5.使用代理对象执行方法
        UserInfo1 userInfo1 = userDao.queryUserById(4);
        System.out.println(userInfo1);
        //6.释放资源
        sqlSession.close();
        in.close();
    }
}
```
## 第九步 : 运行结果
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200311195735312.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)