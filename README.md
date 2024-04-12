# jasypt
![Java](https://img.shields.io/badge/Java-OpenJdk11+-<yellow>)
![Kotlin](https://img.shields.io/badge/Kotlin-1.8.22-<yellow>)
![Maven](https://img.shields.io/badge/Maven-3.x-<yellow>)

![Language](https://img.shields.io/badge/Language-Java-<green>)
![Language](https://img.shields.io/badge/Language-Kotlin-<green>)
![database](https://img.shields.io/badge/database-H2-<green>)

[![项目地址](https://img.shields.io/badge/项目地址-GitHub-<yellow>)](https://github.com/Leemuyi/jasypt.git)
![License](https://img.shields.io/badge/license-MIT-yellow)

[English](docs/README_EN.md)

## 项目背景

<p style="text-indent:2em">
在开发过程中，经常会遇到一些敏感信息的加解密，比如数据库的密码，第三方接口的密钥等等。这些敏感信息不能明文存储，需要加密存储。
在项目中，我们通常会使用配置文件的方式存储这些敏感信息，但是配置文件也是明文存储的，所以我们需要对配置文件进行加密，这样就可以保证敏感信息不会被泄露。
</p>
<p style="text-indent:2em">
Jasypt是一个Java库，用于简化加密和解密敏感信息，如密码和其他文本。它提供了一种易于使用的API，可以在应用程序中轻松地实现加密和解密。
</p>
<p style="text-indent:2em">
本项目是基于Jasypt的加解密工具，可以通过GUI界面进行加解密操作。简化了加解密的操作，提高了开发效率。
</p>
<p style="text-indent:2em">
在此之前已经使用 Java Swing 开发过一个 gui 工具,但是由于 Java Swing 的局限性,导致界面不够美观,所以我决定使用 Kotlin + JavaFx 开发一个新的加解密工具。
并且解决了一些问题,比如:使用Java Swing 开发的 gui 在 MacOS 经常会出现程序崩溃(至少在我的 MBP 上是这样的)、加解密的时候会出现卡顿、没有加解密记录等等。
</p>

## 项目介绍
### 项目结构
```
├─src
│  ├─main
│  │  ├─kotlin
│  │  │  └─cn
│  │  │  │   └─com
│  │  │  │       └─mooyea
│  │  │  │           └─jasypt
│  │  │  │               ├─annotations - 自定义注解
│  │  │  │               │      └─Slf4k.kt - 基于 kotlin 的 log4j2 日志注解
│  │  │  │               ├─controller - 控制器
│  │  │  │               │      ├─JasyptCoderToolController.kt - 主界面控制器
│  │  │  │               │      └─JasyptRecordController.kt - 记录界面控制器
│  │  │  │               ├─entity - 实体类
│  │  │  │               │      └─JasyptRecordEntity.kt - 记录实体类
│  │  │  │               ├─handler - 处理器
│  │  │  │               │      └─JasyptHandler.kt - jasypt 加解密处理器
│  │  │  │               ├─init - 初始化
│  │  │  │               │      └─InitH2Table.kt - 初始化 H2 数据库表
│  │  │  │               ├─utils - 工具类
│  │  │  │               │      ├─H2JdbcTemplate.kt - H2 数据库操作工具类
│  │  │  │               │      └─H2JDBCUtil.kt - H2 数据库连接工具类
│  │  │  │               ├─JasyptCoderToolApplication.kt - 主窗口
│  │  │  │               └─JasyptRecordUI.kt - 记录窗口
│  │  │  └─module-info.java - 模块信息
│  │  └─resources 
│  │      ├─i18n
│  │      │  └─cn
│  │      │      └─com
│  │      │          └─mooyea
│  │      │              └─jasypt
│  │      │                  ├─jasypt.fxml - 主界面布局
│  │      │                  └─jasypt_record.fxml - 记录界面布局
│  │      └─log4j2.xml - log4j2 配置文件
```

### 项目使用的开发语言、开发框架、构建工具、数据库
- 开发语言: Java、Kotlin
- 开发框架: JavaFx
- 构建工具: Maven
- 数据库: H2(使用内存模式,不需要安装数据库,每次启动时会自动检查数据库表是否存在)

### 项目使用的加密算法
1. PBEWITHHMACSHA512ANDAES_256
<p style="text-indent:2em">
一种基于口令的加密(PBE)算法，由Jasypt提供。这种算法使用HMAC-SHA512作为消息认证码(MAC)，并使用AES(Advanced Encryption Standard)算法进行加密。
要使用PBEWithHmacSHA512AndAES_256进行加密和解密，你需要提供一个口令和一个盐值(salt value)。口令用于保护加密的数据，而盐值则用于增加加密的安全性。
</p>
2. PBEWithMD5AndDES
<p style="text-indent:2em">
PBEWithMD5AndDES是一种密码基础加密（Password-Based Encryption，简称PBE）算法，它将MD5消息摘要算法与DES对称加密算法结合起来使用。
在这种算法中，首先使用MD5算法将输入的消息摘要，然后使用该摘要作为密钥来使用DES算法进行加密。这样，即使攻击者知道加密算法和输出的密文，也很难破解出原始消息，因为这需要猜测正确的输入消息才能获得正确的密钥。
</p>

<p style="text-indent:2em">
目前,本项目仅支持上述两种PEB加密算法,其他的加密算法由于我不常用,所以暂时没有添加。
等有机会会将 Jasypt 提供的其他加密算法添加到本项目中。(或许吧🤪)
</p>


### 项目使用的开发工具
- IDEA 2020.2.3
- JDK 11
- Kotlin 1.8.22
- Maven 3.6.3

### 项目的运行环境
- JDK 11
- javafx-sdk-17.0.9

## 项目运行截图
![主界面](http://cdn.mooyea.com.cn/blogs/jasypt_gui_main.png)
<div style="text-align: center;">图1. 主界面</div>

![记录界面](http://cdn.mooyea.com.cn/blogs/jasypt_his.png)
<div style="text-align: center;">图2. 记录界面</div>

[更新历史](docs/CHANGELOG.md)
