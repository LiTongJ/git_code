## TCP/IP四层(五层)模型图
TCP/IP是一组协议的代名词，它还包括许多协议，组成了TCP/IP协议簇.
TCP/IP通讯协议采用了5层的层级结构，每一层都呼叫它的下一层所提供的网络来完成自己的需求
- 物理层: **负责光/电信号的传递方式**. 比如现在以太网通用的网线(双绞 线)、早期以太网采用的的同轴电缆(现在主要用于有线电视)、光纤, 现在的wifi无线网使用电磁波等都属于物理层的概念。物理层的能力决定了最大传输速率、传输距离、抗干扰性等. 集线器(Hub)工作在物理层.
- 数据链路层: **负责设备之间的数据帧的传送和识别**. 例如网卡设备的驱动、帧同步(就是说从网线上检测到什么信号算作新帧的开始)、冲突检测(如果检测到冲突就自动重发)、数据差错校验等工作. 有以太网、令牌环网, 无线LAN等标准. 交换机(Switch)工作在数据链路层.
- 网络层: **负责地址管理和路由选择**. 例如在`IP协议`中, 通过IP地址来标识一台主机, 并通过路由表的方式规划出两台主机之间的数据传输的线路(路由). 路由器(`Router`)工作在网路层.
- 传输层: **负责两台主机之间的数据传输**. 如`传输控制协议` (`TCP`), 能够确保数据可靠的从源主机发送到目标主机.
- 应用层: **负责应用程序间沟通**，如简单电子邮件传输（`SMTP`）、文件传输协议（`FTP`）、网络远程访问协议（`Telnet`）等. 我们的网络编程主要就是针对应用层 

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200305155516721.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

## 数据包封装和分用

- 不同的协议层对数据包有不同的称谓,在**传输层叫做段**(segment),在**网络层叫做数据报** (datagram),在b(frame).
- 应用层数据通过协议栈发到网络上时,每**层协议都要加上一个数据首部**(header),称为封装(Encapsulation).
- 首部信息中包含了一些类似于首部有多长, 载荷(payload)有多长, 上层协议是什么等信息.
- 数据封装成帧后发到传输介质上,到达目的主机后每层协议再剥掉相应的首部, 根据首部中的 "上层协议字段" 将数据交给对应的上层协议处理 

### 数据封装的过程 

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200305163218607.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)
### 数据分用的过程 

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200305164434150.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

## GET和POST的区别

> `修改服务器方面`

- get请求不会在服务器上产生任何结果,所以通常说的安全就是指不会修改服务器信息
- post请求可能会修改服务器上的数据或信息,比如,发表的博客或者评论,都会对现在的界面进行修改,即都是post请求,也可以看作,post是动态的,get是静态的
- ---

> 书写格式方面



 get方法
```css
 GET http://weibo.com/signup/signup.php?inviteCode=2388493434 HTTP/1.1
Host: weibo.com
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
```
 post方法


```css
POST /inventory-check.cgi HTTP/1.1
Host: www.joes-hardware.com
Content-Type: text/plain
Content-length: 18

item=bandsaw 2647

```
两个的区别就是:
- **GET请求的数据会附加在URL之后，以?分割URL和传输数据，多个参数用&连接**。URL的编码格式采用的是ASCII编码，而不是uniclde，即是说所有的非ASCII字符都要编码之后再传输。
- **POST请求会把请求的数据放置在HTTP请求体中**。上面的`item=bandsaw`就是实际的传输数据。
也就是说,GET会把信息暴漏在地址栏中,POST不会

---

> 在传输数据大小的方面

- 在HTTP规范中，没有对URL的长度和传输的数据大小进行限制。但是在实际开发过程中，对于**GET，特定的浏览器和服务器对URL的长度有限制**。因此，在使用GET请求时，传输数据会受到URL长度的限制。
- 对于**POST，由于不是URL传值，理论上是不会受限制的**，但是实际上各个服务器会规定对POST提交数据大小进行限制，Apache、IIS都有各自的配置。

---

> 安全方面

**POST的安全性比GET的高**。`这里的安全是指真正的安全`，而不同于上面GET提到的安全方法中的安全，上面提到的安全仅仅是不修改服务器的数据。

- 比如，在进行登录操作，通过GET请求，用户名和密码都会暴露再URL上，因为登录页面有可能被浏览器缓存以及其他人查看浏览器的历史记录的原因，此时的用户名和密码就很容易被他人拿到了。除此之外，GET请求提交的数据还可能会造成Cross-site request frogery攻击

## HTTP协议(超文本传输协议)

> 什么是URL 

平时我们俗称的 "网址" 其实就是说的 URL 
___

> HTTP请求的格式是什么样的?

- 首行 : [发法] + [url] + [版本号]
- 请求头(Header) : 请求的属性,冒号分隔的键值对;每组属性用\n划分,遇到空行Header部分结束
- 请求体(Body) : 空行后面都是请求体.请求体可为空.如果请求体存在,那么请求头中会有一个Content-Length来标识请求体的长度

---

> HTTP响应的格式是什么样子的?

- 首行 : [版本号] + [状态码] + [状态码解释]
- Header: 请求的属性, 冒号分割的键值对;每组属性之间使用\n分隔;遇到空行表示Header部分结束
- Body: 空行后面的内容都是Body. Body允许为空字符串. 如果Body存在, 则在Header中会有一个ContentLength属性来标识Body的长度; 如果服务器返回了一个html页面, 那么html页面内容就是在body中 

---

> 说几个HTTP的方法

最常见的是post方法和get方法,还有HEAD,DELETE,PUT

---

> 说说get和post的区别

#### [GET和POST的区别(面试重点考点)](https://blog.csdn.net/weixin_43508555/article/details/104679533)
---

> 说说常见的HTTP状态码

![在这里插入图片描述](https://img-blog.csdnimg.cn/20200305180547991.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzUwODU1NQ==,size_16,color_FFFFFF,t_70)

最常见的状态码, 比如 200(OK), 404(Not Found), 403(Forbidden), 302(Redirect, 重定向), 504(Bad Gateway) 

---

> HTTP常见Header
- Content-Type: 数据类型(text/html等)
- Content-Length: Body的长度
- Host: 客户端告知服务器, 所请求的资源是在哪个主机的哪个端口上;
- User-Agent: 声明用户的操作系统和浏览器版本信息;
- referer: 当前页面是从哪个页面跳转过来的;
- location: 搭配3xx状态码使用, 告诉客户端接下来要去哪里访问;
- Cookie: 用于在客户端存储少量信息. 通常用于实现会话(session)的功能; 

 ## 端口号
> `端口号是什么`

端口号(Port)标识了一个主机上进行通信的不同的应用程序; 

---

> `端口号范围划分`

- 0 - 1023: 知名端口号, HTTP, FTP, SSH等这些广为使用的应用层协议, 他们的端口号都是固定的.
- 1024 - 65535: 操作系统动态分配的端口号. 客户端程序的端口号, 就是由操作系统从这个范围分配的 

---

> `认识知名端口号` 

- ssh服务器, 使用22端口
- ftp服务器, 使用21端口
- telnet服务器, 使用23端口
- http服务器, 使用80端口
- https服务器, 使用443 
- tomcat服务器, 使用8080

我们在写端口号时,应该避开zhexie

## TCP 为什么是三次握手，而不是两次或四次？
TCP作为一种**可靠传输控制协议**,其核心思想:**既要保证数据可靠传输，又要提高传输的效率**,而用三次恰恰可以满足以上两方面的需求!

**TCP可靠传输的精髓**: TCP连接的一方A,由操作系统动态随机选取一个**32位长的序列号(InitialSequence Number)** ,假设A的初始序列号为1000，以该序列号为原点，对自2将要发送的每个字节的数据进行编号，1001, 1002, 100..... 并把自己的初始序列号ISN告诉B,让B有一个思想准备,什么样编号的数据是合法的,什么编号是非法的，比如编号900就是非法的,同时B还可以对A每- -个编号的字节数据进行确认。如果A收到B确认编号为2001,则意味着字节编号为1001-2000，共1000个字节已经安全到达。

同理B也是类似的操作，假设B的初始序列号ISN为2000,以该序列号为原点,对自己将要发送的每个字节的数据进行编号，2001, 2002, 200...并把自己的初始序列号ISN告诉A,以便A可以确.认B发送的每一个字节。 如果B收到A确认编号为4001,则意味着字节编号为2001- -4000,共2000个字节已经安全到达。

**一话概括，TCP连接握手,握的是啥?**

`通信双方数据原点的序列号!`

以此核心思想我们来分析二、三、四次握手的过程。

A <-------> B

`四次握手的过程: `

1.  A发送同步信号**SYN + A's Initial sequence number**

2.  B确认收到A的同步信号，并记录**A's ISN**到本地，发送**B's ACK sequence number**
3. B发送同步信号**SYN + B's Initial sequence number**

4. A确认收到B的同步信号,并记录**B's ISN**到本地，命名**A's ACK sequence number**

**很显然2和3这两个步骤可以合并，只需要三次握手，可以提高连接的速度与效率。**

`二次握手的过程:`

1. A发送同步信号**SYN + A's Initial sequence number**

2. B发送同步信号**SYN + B's Initial sequence number + B's ACK sequence number**

这里有一个问题，A与B就A的初始序例号达成了一致,这里是1000。但是B无法知道A是否已经接收到自己的同步信号，如果这个同步信丢失了，A和B就B的初始序列号将无法达成一致。

**于是TCP的设计者将SYN这个同步标志位SYN设计成占用一个字节的编号(FIN标志位也是)，既然是一个字节的数据，按照TCP对有数据的TCP segment必须确认的原则**，所以在这里A必须给B一个确认，以确认A已经接收到B的同步信号。

> 有人会说，如果A发给B的确认丢了，该如何?

A会超时重传这个ACK吗?不会! `TCP不会为没有数据的ACK超时重传。`

> 那该如何是好?

**B如果没有收到A的ACK,会超时重传自己的SYN同步信号，一直到收到A的ACK为止。**

---
> 第一个包，即A发给B的SYN中途被丢，没有到达B

A会周期性超时重传，直到收到B的确认

> 第二个包，即B发给A的SYN + ACK中途被丢，没有到达A

B会周期性超时重传，直到收到A的确认

> 第三个包，即A发给B的ACK中途被丢,没有到达B

A发完ACK，方面认为TCP为Established状态,而B显然认为TCP为Active状态:

- 假定此时双方都没有数据发送，B会周期性超时重传，直到收到A的确认,收到之后B的TCP连接也伪Established状态,双向可以发包。

- 假定此时A有数据发送，B收到A的Data + ACK,自然会切换为established 状态，接受A的Data。

- 假定B有数据发送，数据发送不了,会直周期性超时重传SYN + ACK,直到收到A的确认可以发送数据。

#### `说来说去对于SYN和FIN必须做出回应,使双方的序列号都做出回应,ACK不用做出回应,不然会进入死循环.`