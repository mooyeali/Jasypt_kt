# jasypt
![Java](https://img.shields.io/badge/Java-OpenJdk11+-<yellow>)
![Kotlin](https://img.shields.io/badge/Kotlin-1.8.22-<yellow>)
![Maven](https://img.shields.io/badge/Maven-3.x-<yellow>)

![Language](https://img.shields.io/badge/Language-Java-<green>)
![Language](https://img.shields.io/badge/Language-Kotlin-<green>)
![database](https://img.shields.io/badge/database-H2-<green>)

[![项目地址](https://img.shields.io/badge/项目地址-GitHub-<yellow>)](https://github.com/Leemuyi/jasypt.git)
![License](https://img.shields.io/badge/license-MIT-yellow)

## Project Background

<p style="text-indent:2em">
During the development process, you often encounter the encryption and decryption of some sensitive information, such as the password of the database, the key of the third-party interface, and so on. These sensitive information cannot be stored in plaintext and need to be stored encrypted.
In a project, we usually use configuration files to store this sensitive information, but configuration files are also stored in clear text, so we need to encrypt the configuration files to ensure that sensitive information is not leaked.
</p>
<p style="text-indent:2em">
Jasypt is a Java library that simplifies encrypting and decrypting sensitive information such as passwords and other texts. It provides an easy-to-use API that makes it easy to implement encryption and decryption in the application.
</p>
<p style="text-indent:2em">
This project is based on Jasypt's encryption and decryption tool, which can be encrypted and decrypted through the GUI interface. The operation of encryption and decryption is simplified, and the development efficiency is improved.
</p>
<p style="text-indent:2em">
I had already developed a gui tool using Java Swing, but due to the limitations of Java Swing, the interface was not beautiful enough, so I decided to use Kotlin + JavaFx to develop a new encryption and decryption tool.
And some problems have been solved, such as: the GUI developed with Java Swing often crashes on MacOS (at least on my MBP), there is a lag when encrypting and decrypting, there is no encryption and decryption record, etc.
</p>

## Project Introduction
### Project structure
```
├─src
│  ├─main
│  │  ├─kotlin
│  │  │  └─cn
│  │  │  │   └─com
│  │  │  │       └─mooyea
│  │  │  │           └─jasypt
│  │  │  │               ├─annotations - Custom annotations
│  │  │  │               │      └─Slf4k.kt - Kotlin-based log4j2 log annotation
│  │  │  │               ├─controller - controller
│  │  │  │               │      ├─JasyptCoderToolController.kt - Main interface controller
│  │  │  │               │      └─JasyptRecordController.kt - Recording interface controller
│  │  │  │               ├─entity - Entity classes
│  │  │  │               │      └─JasyptRecordEntity.kt - Record entity classes
│  │  │  │               ├─handler - 处理器
│  │  │  │               │      └─JasyptHandler.kt - Jasypt encryption and decryption processor
│  │  │  │               ├─init - Initialize
│  │  │  │               │      └─InitH2Table.kt - Initialize the H2 database table
│  │  │  │               ├─utils - Utilities
│  │  │  │               │      ├─H2JdbcTemplate.kt - H2 database manipulation tool class
│  │  │  │               │      └─H2JDBCUtil.kt - H2 Database Connection Utility Class
│  │  │  │               ├─JasyptCoderToolApplication.kt - Main window
│  │  │  │               └─JasyptRecordUI.kt - Record window
│  │  │  └─module-info.java - Module information
│  │  └─resources 
│  │      ├─i18n
│  │      │  └─cn
│  │      │      └─com
│  │      │          └─mooyea
│  │      │              └─jasypt
│  │      │                  ├─jasypt.fxml - Main interface layout
│  │      │                  └─jasypt_record.fxml - Record the layout of the interface
│  │      └─log4j2.xml - log4j2 configuration file
```

### The development language, development framework, build tools, and database used by the project
- Development language: Java、Kotlin
- Development frameworks: JavaFx
- Build tools: Maven
- Database: H2 (uses in-memory mode, no need to install the database, automatically checks the existence of the database table every time it is started)

### The cryptographic algorithm used by the project
1. PBEWITHHMACSHA512ANDAES_256
<p style="text-indent:2em">
A password-based encryption (PBE) algorithm, provided by Jasypt. This algorithm uses HMAC-SHA512 as the Message Authentication Code (MAC) and uses the Advanced Encryption Standard (AES) algorithm for encryption.
To encrypt and decrypt with PBEWithHmacSHA512AndAES_256, you need to provide a password and a salt value. Passwords are used to protect encrypted data, while salts are used to increase the security of encryption.
</p>
2. PBEWithMD5AndDES
<p style="text-indent:2em">
PBEWithMD5AndDES is a Password-Based Encryption (PBE) algorithm that combines the MD5 message digest algorithm with the DES symmetric encryption algorithm.
In this algorithm, the input message is first digested using the MD5 algorithm, and then the digest is used as a key to encrypt with the DES algorithm. This way, even if an attacker knows the encryption algorithm and the output ciphertext, it will be difficult to crack the original message, as this requires guessing the correct input message to get the correct key.
</p>

<p style="text-indent:2em">
At present, this project only supports the above two PEB encryption algorithms, and the other encryption algorithms are not added for the time being because I do not use them frequently.
Other encryption algorithms provided by Jasypt will be added to the project when the opportunity arises. (Perhaps 🤪)
</p>


### The development tools used by the project
- IDEA 2020.2.3
- JDK 11
- Kotlin 1.8.22
- Maven 3.6.3

### The operating environment of the project
- JDK 11
- javafx-sdk-17.0.9

## Screenshot of the project running
![主界面](http://cdn.mooyea.com.cn/blogs/jasypt_gui_main.png)
<div style="text-align: center;">Figure 1. Main interface</div>

![记录界面](http://cdn.mooyea.com.cn/blogs/jasypt_his.png)
<div style="text-align: center;">Figure 2. Recording interface</div>

[Update history](./CHANGELOG_EN.md)
