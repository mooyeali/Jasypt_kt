#!/bin/bash

# Jasypt Electron 应用演示脚本

echo "=== Jasypt Electron 应用演示 ==="
echo

# 检查后端是否运行
echo "1. 检查后端服务状态..."
if curl -s http://localhost:12000/api/jasypt/algorithms > /dev/null; then
    echo "✅ 后端服务运行正常"
else
    echo "❌ 后端服务未运行，请先启动后端"
    exit 1
fi
echo

# 获取支持的算法
echo "2. 获取支持的加密算法..."
curl -s http://localhost:12000/api/jasypt/algorithms | jq '.'
echo

# 演示加密功能
echo "3. 演示加密功能..."
echo "加密文本: 'Hello Jasypt Electron!'"
ENCRYPT_RESULT=$(curl -s -X POST http://localhost:12000/api/jasypt/encrypt \
  -H "Content-Type: application/json" \
  -d '{"plainText":"Hello Jasypt Electron!","password":"demo123","algorithm":"PBEWITHHMACSHA512ANDAES_256"}')

echo "加密结果:"
echo $ENCRYPT_RESULT | jq '.'

# 提取密文
CIPHER_TEXT=$(echo $ENCRYPT_RESULT | jq -r '.data.cipherText')
echo

# 演示解密功能
echo "4. 演示解密功能..."
echo "解密密文: $CIPHER_TEXT"
DECRYPT_RESULT=$(curl -s -X POST http://localhost:12000/api/jasypt/decrypt \
  -H "Content-Type: application/json" \
  -d "{\"cipherText\":\"$CIPHER_TEXT\",\"password\":\"demo123\",\"algorithm\":\"PBEWITHHMACSHA512ANDAES_256\"}")

echo "解密结果:"
echo $DECRYPT_RESULT | jq '.'
echo

# 演示中文加密
echo "5. 演示中文文本加密..."
echo "加密中文文本: '你好，Jasypt！'"
CHINESE_ENCRYPT=$(curl -s -X POST http://localhost:12000/api/jasypt/encrypt \
  -H "Content-Type: application/json" \
  -d '{"plainText":"你好，Jasypt！","password":"中文密码","algorithm":"PBEWITHHMACSHA512ANDAES_256"}')

echo "中文加密结果:"
echo $CHINESE_ENCRYPT | jq '.'

CHINESE_CIPHER=$(echo $CHINESE_ENCRYPT | jq -r '.data.cipherText')
echo

# 解密中文
echo "6. 解密中文文本..."
CHINESE_DECRYPT=$(curl -s -X POST http://localhost:12000/api/jasypt/decrypt \
  -H "Content-Type: application/json" \
  -d "{\"cipherText\":\"$CHINESE_CIPHER\",\"password\":\"中文密码\",\"algorithm\":\"PBEWITHHMACSHA512ANDAES_256\"}")

echo "中文解密结果:"
echo $CHINESE_DECRYPT | jq '.'
echo

# 生成随机盐值
echo "7. 生成随机盐值..."
SALT_RESULT=$(curl -s -X POST http://localhost:12000/api/jasypt/generate-salt \
  -H "Content-Type: application/json" \
  -d '{"length":16}')

echo "盐值生成结果:"
echo $SALT_RESULT | jq '.'
echo

# 查看操作记录
echo "8. 查看操作记录..."
RECORDS=$(curl -s http://localhost:12000/api/records)
echo "操作记录:"
echo $RECORDS | jq '.data | length' | xargs echo "总记录数:"
echo $RECORDS | jq '.data[0:3]'  # 显示前3条记录
echo

# 按操作类型查询
echo "9. 按操作类型查询记录..."
ENCRYPT_RECORDS=$(curl -s http://localhost:12000/api/records/operation/ENCRYPT)
echo "加密操作记录数:"
echo $ENCRYPT_RECORDS | jq '.data | length'
echo

echo "=== 演示完成 ==="
echo
echo "🎉 Jasypt Electron 应用功能演示成功！"
echo
echo "主要功能："
echo "✅ 文本加密/解密"
echo "✅ 中文字符支持"
echo "✅ 多种加密算法"
echo "✅ 操作记录管理"
echo "✅ 随机盐值生成"
echo "✅ RESTful API"
echo
echo "要启动完整应用，请运行: ./start.sh"