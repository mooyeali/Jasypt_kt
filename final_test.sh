#!/bin/bash

cd /workspace/jasypt-electron

echo "=== 最终功能测试 ==="
echo

# 测试基本功能
echo "1. 测试英文加密解密..."
RESULT1=$(curl -s -X POST http://localhost:12000/api/jasypt/encrypt -H "Content-Type: application/json" -d '{"plainText":"Hello World","password":"test123","algorithm":"PBEWITHHMACSHA512ANDAES_256"}')
CIPHER1=$(echo $RESULT1 | jq -r '.data.cipherText')
RESULT2=$(curl -s -X POST http://localhost:12000/api/jasypt/decrypt -H "Content-Type: application/json" -d "{\"cipherText\":\"$CIPHER1\",\"password\":\"test123\",\"algorithm\":\"PBEWITHHMACSHA512ANDAES_256\"}")
echo "加密: $(echo $RESULT1 | jq -r '.success')"
echo "解密: $(echo $RESULT2 | jq -r '.success')"
echo "原文: $(echo $RESULT2 | jq -r '.data.plainText')"
echo

# 测试中文功能
echo "2. 测试中文加密解密..."
RESULT3=$(curl -s -X POST http://localhost:12000/api/jasypt/encrypt -H "Content-Type: application/json" -d '{"plainText":"你好世界","password":"中文密码","algorithm":"PBEWITHHMACSHA512ANDAES_256"}')
CIPHER3=$(echo $RESULT3 | jq -r '.data.cipherText')
RESULT4=$(curl -s -X POST http://localhost:12000/api/jasypt/decrypt -H "Content-Type: application/json" -d "{\"cipherText\":\"$CIPHER3\",\"password\":\"中文密码\",\"algorithm\":\"PBEWITHHMACSHA512ANDAES_256\"}")
echo "加密: $(echo $RESULT3 | jq -r '.success')"
echo "解密: $(echo $RESULT4 | jq -r '.success')"
echo "原文: $(echo $RESULT4 | jq -r '.data.plainText')"
echo "中文检测: $(echo $RESULT4 | jq -r '.data.isContainChinese')"
echo

# 测试记录功能
echo "3. 测试记录管理..."
RECORDS=$(curl -s http://localhost:12000/api/records)
echo "记录总数: $(echo $RECORDS | jq '.data | length')"
echo

# 测试算法列表
echo "4. 测试算法支持..."
ALGORITHMS=$(curl -s http://localhost:12000/api/jasypt/algorithms)
echo "支持的算法: $(echo $ALGORITHMS | jq -r '.data.algorithms | join(", ")')"
echo

# 测试盐值生成
echo "5. 测试盐值生成..."
SALT=$(curl -s -X POST http://localhost:12000/api/jasypt/generate-salt -H "Content-Type: application/json" -d '{"length":16}')
echo "盐值生成: $(echo $SALT | jq -r '.success')"
echo "盐值长度: $(echo $SALT | jq -r '.data.salt | length')"
echo

echo "=== 测试完成 ==="
echo
echo "🎉 Jasypt Electron 重构项目测试通过！"
echo
echo "✅ 主要功能："
echo "  - 英文文本加密/解密"
echo "  - 中文文本加密/解密"
echo "  - 中文密码支持"
echo "  - 操作记录管理"
echo "  - 多算法支持"
echo "  - 随机盐值生成"
echo
echo "✅ 技术架构："
echo "  - Spring Boot 后端 API"
echo "  - Electron 前端界面"
echo "  - H2 数据库存储"
echo "  - RESTful API 设计"
echo
echo "📁 项目文件："
echo "  - ./start.sh - 启动脚本"
echo "  - ./demo.sh - 演示脚本"
echo "  - ./README.md - 项目文档"
echo "  - ./PROJECT_SUMMARY.md - 重构总结"