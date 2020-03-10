 @[TOC](Myredis)

# 项目源码

> 项目源码:[https://gitee.com/li_tong_jia/project.git](https://gitee.com/li_tong_jia/project.git)

# 项目目标
- 巩固多线程、集合框架、TCP、字节流、redis等技术
- 实现redis协议、命令等
- 可以使用redis-cli进行测试

# 项目使用技术栈与平台
- 所用技术：redis,字节流协议,内存数据库,多线程等
- 平台与环境：Windows/Mac，IDEA，Maven

# 项目背景
### 认识缓存
- 实现数据的重复使用，速度慢的设备需要通过缓存将经常要用到的数据缓存起来，缓存下来的数据可以提供高速的传输速度给速度快的设备。
- 例如：将硬盘中的数据读取出来放在内存的缓存区中，这样以后再次访问同一个资源，速度会快很多。

### 认识redis
- redis是一个key-value存储系统。和Memcached类似，它支持存储的value类型相对更多，包括string(字符串)、list(链表)、set(集合)、zset(sorted set --有序集合)和hash（哈希类型）。这些数据类型都支持push/pop、add/remove及取交集并集和差集及更丰富的操作，而且这些操作都是原子性的。在此基础上，redis支持各种不同方式的排序。与memcached一样，为了保证效率，数据都是缓存在内存中。区别的是redis会周期性的把更新的数据写入磁盘或者把修改操作写入追加的记录文件，并且在此基础上实现了master-slave(主从)同步。

- Redis 是一个高性能的key-value数据库。 redis的出现，很大程度补偿了memcached这类key/value存储的不足，在部 分场合可以对关系数据库起到很好的补充作用。它提供了Java，C/C++，C#，PHP，JavaScript，Perl，Object-C，Python，Ruby，Erlang等客户端，使用很方便。

- Redis支持主从同步。数据可以从主服务器向任意数量的从服务器上同步，从服务器可以是关联其他从服务器的主服务器。

# 项目功能
- lpush存放数据到Map<String,ArrayList< String>>,返回List长度
- lrange从Map<String,ArrayList< String>>中读取需要的数据,按行打印到客户端
- hset存放数据到Map<String, Map<String,String>>,更新返回0,插入返回1
- hget从Map<String, Map<String,String>>中读取需要的数据,打印到客户端
- 多线程中间读写数据
# 项目演示

### 启动服务器
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200309164835622.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
### 启动一个客户端redis-cli
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200309165039332.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

### 插入一些数据
lpush插入数据到 List,打印链表的长度
hset插入数据到HashMap,更新为0,打印为1


![在这里插入图片描述](https://img-blog.csdnimg.cn/20200309165935670.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

### 再启动一个客户端读取数据
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200309170514775.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
# 系统流程
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200309170748900.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
# 系统设计
### socket设计
- 实现服务器与客户端连接
- 取得客户端的I/O流
- 创建一个固定大小的线程池

### 协议支持的数据类型
![在这里插入图片描述](https://img-blog.csdnimg.cn/20200309172114993.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
### 创建一个内存数据库
- 使用多线程安全的单例模式
- 初始化一个`Map<String, List< String>>`和一个`Map<String, Map<String,String>>`

### 命令
- 实现List的lpush和lrange命令
- 实现Map的hset和hget方法

# 开发步骤
### 创建Maven

```java
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.tong</groupId>
    <artifactId>myredis</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
        <encoding>UTF-8</encoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.26</version>
        </dependency>

        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.2.3</version>
        </dependency>
    </dependencies>
</project>
```

### 实现客户端与服务器连接

```java
public static void main(String[] args) {
    new Server().run(6379);
}

private void run(int port){    
    ServerSocket serverSocket = new ServerSocket(port)) 
    Socket socket = serverSocket.accept();
    }
```
### 获取socket的I/O流

```java
InputStream is = socket.getInputStream();
OutputStream out = socket.getOutputStream();
```
### 建立一个固定大小的线程池

```java
ExecutorService pool = Executors.newFixedThreadPool(10);
try(ServerSocket serverSocket = new ServerSocket(port)) {
    while (true){
        Socket socket = serverSocket.accept();
        pool.execute( () -> {
            try {
                logger.info("{} 已连接。", socket.getInetAddress().getHostName());
                InputStream is = socket.getInputStream();
                OutputStream out = socket.getOutputStream();
                Command command = null;
                while (true) {
                    try {
                        command = Protocol.readCommand(is);
                        command.run(out);
                    } catch (Exception e) {
                        w.writeError(out, "不识别的命令");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
} catch (IOException e) {
    e.printStackTrace();
}
```
### redis协议的处理
- 协议
```java
public class Protocol {
    public static Command readCommand(InputStream is) throws Exception {
        Object o = read(is);
        List<Object> list = (List<Object>) o;
        if (list.size() < 1){
            throw new Exception("元素个数必须大于1。");
        }
        Object o1 = list.remove(0);
        if (!(o1 instanceof byte[])){
            throw new Exception("这不是一个命令。");
        }
        byte[] o2 = (byte[]) o1;
        String commandName = new String(o2);
        String className = String.format("com.tong.commands.%sCommand",commandName.toUpperCase());
        Class<?> cla = Class.forName(className);
        if (!(Command.class.isAssignableFrom(cla))){
            throw new Exception("错误的命令。");
        }
        Command command = (Command) cla.newInstance();
        command.setList(list);
        return command;
    }

    private static Object read(InputStream is) throws IOException {
        return new Process().process(is);
    }
}
```
- 具题的实现:

```java
public class Process {
    private Reads reads = new Reads();

    public Object process(InputStream is) throws IOException {
        int b = is.read();
        if (b == -1){
            throw new RuntimeException("不应该读到结尾的。");
        }
        switch (b){
            case '+':
                return processSimpleString(is);
            case '-':
                throw new RuntimeException(processError(is));
            case ':':
                return processInteger(is);
            case '$':
                return processBulkString(is);
            case '*':
                return processArray(is);
            default:
                throw new RuntimeException("不识别的类型");

        }
    }

    private String processSimpleString(InputStream is) throws IOException {
        return reads.readLine(is);
    }

    private String processError(InputStream is) throws IOException {
        return reads.readLine(is);
    }

    private Long processInteger(InputStream is) throws IOException {
        return reads.readInteger(is);
    }

    private byte[] processBulkString(InputStream is) throws IOException {
        int len = (int)reads.readInteger(is);
        if (len == -1){
            return null;
        }
        byte[] arr = new byte[len];
        is.read(arr,0, len);
        is.read();
        is.read();
        return arr;
    }

    private List<Object> processArray(InputStream is) throws IOException {
        int len = (int)reads.readInteger(is);
        if (len == -1){
            return null;
        }
        List<Object> list = new ArrayList<>(len);
        for (int i = 0; i < len; i++) {
            try{
                list.add(process(is));
            }catch (Exception e){
                list.add(e);
            }
        }
        return list;
    }
}

```
- 读取字符串

```java
public String readLine(InputStream is) throws IOException {
    List<Byte> bytes = new ArrayList<>();
    boolean needRead = true;
    int b = -1;
    while(true){
        if (needRead){
            b = is.read();
            if (b == -1){
                throw new RuntimeException("不应该读到结尾的。");
            }
        }else {
            needRead = true;
        }
        if (b == '\r'){

            int c = is.read();
            if (c == -1){
                throw new RuntimeException("不应该读到结尾的。");
            }
            if (c == '\r'){
                bytes.add((byte) b);
                needRead = false;
            }else {
                bytes.add((byte) b);
                bytes.add((byte) c);
            }
            if (c == '\n'){
                break;
            }
        }else {
            bytes.add((byte) b);
        }
    }
    byte[] ret = new byte[bytes.size()];
    for (int i = 0; i < bytes.size(); i++) {
        ret[i] = bytes.get(i);
    }
    String str = new String(ret, Charset.forName("UTF-8"));

    return str;
}
```
- 读取数字

```java
public long readInteger(InputStream is) throws IOException {
    StringBuilder sb = new StringBuilder();
    boolean needRead = true;
    boolean isNegative = false;
    int b = -1;
    while(true){
        if (needRead){
            b = is.read();
            if (b == -1){
                throw new RuntimeException("不应该读到结尾的。");
            }
        }else {
            needRead = true;
        }
        if (b == '\r'){
            int c = is.read();
            if (c == -1){
                throw new RuntimeException("不应该读到结尾的。");
            }
            if (c == '\r'){
                needRead = false;
            }
            if (c == '\n'){
                break;
            }
        }else if(b == '-'){
            isNegative = true;
        }else {
            sb.append((char)b);
        }
    }
    long num = Long.parseLong(sb.toString());
    if (isNegative){
        num = -num;
    }
    return num;
}
```

### 内存数据库
- 单例模式

```java
//多线程的安全版本单例模式
private static volatile Database instance = null;
public static Database getInstance() {
    if(instance==null) {
        synchronized (Database.class) {
            if(instance==null)
                instance = new Database();
        }
    }
    return instance;
}
```
- 初始化表

```java
private static Map<String, List<String>> lists = new HashMap<>();
private static Map<String, Map<String,String>> hashes = new HashMap<>();
Database() {
    hashes = new HashMap<>();
    lists = new HashMap<>();
}
```
### 命令实现
- command接口,定义两个方法

```java
void run(OutputStream out) throws Exception;

void setList(List<Object> list);
```
- lpushCommand,两个参数:Map中的位置,要插入的数据;
返回当前链表的长度

```java
private final static Logger logger = LoggerFactory.getLogger(LPUSHCommand.class);
private List<Object> list;
private Write w = new Write();

@Override
public void run(OutputStream out) throws IOException {
    if (list.size() != 2) {
        w.writeError(out, "命令至少需要两个参数");
        return;
    }

    String key = new String((byte[]) list.get(0));
    String value = new String((byte[]) list.get(1));
    logger.debug("运行的是 lpush 命令: {} {}", key, value);
    List<String> list = Database.getInstance().getList(key);
    list.add(0,value);
    logger.debug("插入后数据共有 {} 个", list.size());
    w.writeInteger(out,list.size());
}

@Override
public void setList(List<Object> list) {
    this.list = list;
}
```
lrangeCommand,三个参数:Map中链表的位置,查询的起始位置,查询的最后一个位置

```java
private final static Logger logger = LoggerFactory.getLogger(LRANGECommand.class);
private List<Object> list;
private Write w = new Write();

@Override
public void run(OutputStream out) throws Exception {
    if (list.size() != 3) {
        w.writeError(out, "命令至少需要三个参数.");
        return;
    }
    String key = new String((byte[]) list.get(0));
    int start = Integer.parseInt(new String((byte[]) list.get(1)));
    int end = Integer.parseInt(new String((byte[]) list.get(2)));
    logger.debug("运行的是 lrange 命令: {} {} {}", key, start, end);
    List<String> list = Database.getInstance().getList(key);
    if (end < 0) {
        end = list.size() + end;
    }
    w.writeArray(out, list.subList(start, end + 1));
}

@Override
public void setList(List<Object> list) {
    this.list = list;
}
```
 hsetCommand : 三个参数: Map中Map的位置,Map的Key,Map的Value
 插入返回1,更新返回0

```java
private final static Logger logger = LoggerFactory.getLogger(HSETCommand.class);
private List<Object> list;
private Write w = new Write();

@Override
public void run(OutputStream out) throws IOException {
    if (list.size() != 3) {
        w.writeError(out, "命令至少需要三个参数");
        return;
    }
    String key = new String((byte[]) list.get(0));
    String filed = new String((byte[]) list.get(1));
    String value = new String((byte[]) list.get(2));
    logger.debug("运行的是 hset 命令: {} {} {}", key, filed, value);
    Map<String,String> hash = Database.getInstance().getHash(key);
    boolean isUpdate = hash.containsKey(filed);
    hash.put(filed,value);
    if (isUpdate){
        logger.debug("更新 {} 的值为 {}", filed, value);
        w.writeInteger(out, 0);
    }else {
        logger.debug("插入了 {} {}", filed, value);
        w.writeInteger(out, 1);
    }
}

@Override
public void setList(List<Object> list) {
    this.list = list;
}
```
hgetCommand:两个参数:Map中Map的位置,Map的key

```java
private final static Logger logger = LoggerFactory.getLogger(HGETCommand.class);
private List<Object> list;
private Write w = new Write();

@Override
public void run(OutputStream out) throws IOException {
    if (list.size() != 2) {
        w.writeError(out, "命令至少需要两个参数");
        return;
    }
    String key = new String((byte[]) list.get(0));
    String filed = new String((byte[]) list.get(1));
    logger.debug("运行的是 hget 命令: {} {}", key, filed);
    Map<String,String> hash = Database.getInstance().getHash(key);
    String value = hash.get(filed);
    logger.debug("得到的数据是 {}", value);
    if (value != null){
        w.writeBulkString(out, value);
    }else {
        w.writeNull(out);
    }
}

@Override
public void setList(List<Object> list) {
    this.list = list;
}
```

# 项目总结
- 项目有点 : 通过k-v的形式存储数据,访问速度很快,实现了redis的协议,可以用redis-cli来操作,实现了多线程,多个用户可以对内存数据库进行读写
- 项目缺点 : 实现的命令较少
- 项目扩展 :
	1. 支持更多的数据类型
	2. 数据想办法持久化
	3. 提升处理性能 

# 项目参考资料
redis官网
redis-api文档