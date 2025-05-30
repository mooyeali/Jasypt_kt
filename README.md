# Jasypt Electron 加密工具

基于 Electron + Spring Boot 的现代化加密解密工具，从原 JavaFX 版本重构而来。

## 项目概述

这是对原 Jasypt_kt 项目的完整重构，采用了现代化的技术栈：

- **前端**: Electron + HTML5 + CSS3 + JavaScript
- **后端**: Spring Boot + Kotlin + H2 Database
- **加密**: Jasypt 加密库

## 功能特性

### 🔐 加密功能
- 支持多种加密算法（PBEWITHHMACSHA512ANDAES_256、PBEWithMD5AndDES）
- 文本加密和解密
- 自动检测中文字符
- 随机盐值生成

### 📊 数据管理
- 操作记录存储
- 历史记录查看
- 按操作类型和算法过滤
- 一键清空记录

### 🎨 用户界面
- 现代化的 Electron 界面
- 响应式设计
- 实时状态显示
- 快捷键支持

### 🔧 技术特性
- RESTful API 架构
- 前后端分离
- 跨平台支持
- 自动数据库初始化

## 项目结构

```
jasypt-electron/
├── backend/                    # Spring Boot 后端
│   ├── src/main/kotlin/
│   │   └── cn/com/mooyea/jasypt/
│   │       ├── JasyptBackendApplication.kt
│   │       ├── config/
│   │       │   └── CorsConfig.kt
│   │       ├── controller/
│   │       │   ├── JasyptController.kt
│   │       │   └── JasyptRecordController.kt
│   │       ├── dto/
│   │       │   ├── EncryptRequest.kt
│   │       │   ├── DecryptRequest.kt
│   │       │   ├── GenerateSaltRequest.kt
│   │       │   └── ApiResponse.kt
│   │       ├── entity/
│   │       │   └── JasyptRecord.kt
│   │       ├── repository/
│   │       │   └── JasyptRecordRepository.kt
│   │       └── service/
│   │           ├── JasyptService.kt
│   │           └── JasyptRecordService.kt
│   ├── src/main/resources/
│   │   └── application.yml
│   └── pom.xml
├── frontend/                   # Electron 前端
│   ├── main.js                # Electron 主进程
│   ├── index.html             # 主界面
│   ├── styles/
│   │   └── main.css           # 样式文件
│   ├── js/
│   │   ├── api.js             # API 客户端
│   │   ├── ui.js              # UI 管理器
│   │   └── app.js             # 主应用逻辑
│   └── package.json
├── start.sh                   # 启动脚本
└── README.md                  # 项目文档
```

## 快速开始

### 环境要求

- Java 17+
- Maven 3.6+
- Node.js 18+
- npm 或 yarn

### 安装依赖

1. **后端依赖**
```bash
cd backend
mvn clean install
```

2. **前端依赖**
```bash
cd frontend
npm install
```

### 启动应用

#### 方式一：使用启动脚本（推荐）
```bash
./start.sh
```

#### 方式二：分别启动

1. **启动后端**
```bash
cd backend
mvn spring-boot:run
```

2. **启动前端**
```bash
cd frontend
npm start
```

### 访问应用

- 后端 API: http://localhost:12000/api
- 前端应用: Electron 桌面应用

## API 接口

### 加密相关

#### 获取支持的算法
```http
GET /api/jasypt/algorithms
```

#### 加密文本
```http
POST /api/jasypt/encrypt
Content-Type: application/json

{
    "plainText": "要加密的文本",
    "password": "密码",
    "algorithm": "PBEWITHHMACSHA512ANDAES_256"
}
```

#### 解密文本
```http
POST /api/jasypt/decrypt
Content-Type: application/json

{
    "cipherText": "加密后的文本",
    "password": "密码",
    "algorithm": "PBEWITHHMACSHA512ANDAES_256"
}
```

#### 生成盐值
```http
POST /api/jasypt/generate-salt
Content-Type: application/json

{
    "length": 16
}
```

### 记录管理

#### 获取所有记录
```http
GET /api/records
```

#### 按操作类型获取记录
```http
GET /api/records/operation/{operationType}
```

#### 按算法获取记录
```http
GET /api/records/algorithm/{algorithm}
```

#### 清空所有记录
```http
DELETE /api/records/clear
```

## 使用说明

### 基本操作

1. **选择算法**: 从下拉列表中选择加密算法
2. **输入密码**: 可以手动输入或点击"生成"按钮生成随机密码
3. **加密**: 在明文框输入内容，点击"加密"按钮
4. **解密**: 在密文框输入内容，点击"解密"按钮
5. **交换**: 点击"交换"按钮可以交换明文和密文内容

### 快捷键

- `Ctrl+E`: 加密
- `Ctrl+D`: 解密
- `Ctrl+N`: 清空表单

### 记录管理

- 所有操作会自动保存到数据库
- 可以通过右侧面板查看历史记录
- 支持按操作类型和算法过滤
- 点击记录可以快速填充表单

## 技术架构

### 后端架构

- **Spring Boot**: 主框架
- **Spring Data JPA**: 数据访问层
- **H2 Database**: 内存数据库
- **Jasypt**: 加密库
- **Kotlin**: 编程语言

### 前端架构

- **Electron**: 桌面应用框架
- **原生 JavaScript**: 无框架依赖
- **CSS3**: 现代样式
- **Fetch API**: HTTP 请求

### 数据库设计

```sql
CREATE TABLE jasypt_record (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    plain_text TEXT,
    cipher_text TEXT,
    algorithm VARCHAR(255),
    salt_value VARCHAR(255),
    operation_type VARCHAR(255),
    is_contain_chinese BOOLEAN,
    create_time TIMESTAMP
);
```

## 开发指南

### 添加新的加密算法

1. 在 `JasyptService.kt` 中的 `supportedAlgorithms` 列表添加新算法
2. 确保 Jasypt 库支持该算法
3. 重启应用即可

### 自定义界面

1. 修改 `frontend/styles/main.css` 调整样式
2. 修改 `frontend/index.html` 调整布局
3. 修改 `frontend/js/` 下的文件调整交互逻辑

### 扩展 API

1. 在 `controller` 包下添加新的控制器
2. 在 `service` 包下添加业务逻辑
3. 在 `dto` 包下添加数据传输对象

## 部署说明

### 打包应用

#### 后端打包
```bash
cd backend
mvn clean package
```

#### 前端打包
```bash
cd frontend
npm run build
```

### 生产环境配置

1. 修改 `application.yml` 中的数据库配置
2. 配置生产环境的 CORS 设置
3. 设置合适的日志级别

## 故障排除

### 常见问题

1. **后端启动失败**
   - 检查 Java 版本是否为 17+
   - 检查端口 12000 是否被占用
   - 查看 `backend.log` 日志文件

2. **前端启动失败**
   - 检查 Node.js 版本是否为 18+
   - 运行 `npm install` 重新安装依赖
   - 检查是否安装了必要的系统库

3. **API 连接失败**
   - 确认后端服务已启动
   - 检查防火墙设置
   - 验证 API 地址配置

### 日志查看

- 后端日志: `backend/backend.log`
- 前端日志: Electron 开发者工具控制台

## 贡献指南

1. Fork 项目
2. 创建特性分支
3. 提交更改
4. 推送到分支
5. 创建 Pull Request

## 许可证

本项目采用 MIT 许可证，详见 LICENSE 文件。

## 更新日志

### v1.0.0 (2024-05-30)
- 完成从 JavaFX 到 Electron 的重构
- 实现 Spring Boot 后端 API
- 添加现代化的用户界面
- 支持操作记录管理
- 添加快捷键支持

## 联系方式

如有问题或建议，请通过以下方式联系：

- 项目地址: https://github.com/mooyeali/Jasypt_kt
- 问题反馈: GitHub Issues

---

感谢使用 Jasypt Electron 加密工具！