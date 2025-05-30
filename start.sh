#!/bin/bash

# Jasypt Electron 应用启动脚本

echo "=== Jasypt Electron 应用启动 ==="

# 检查后端是否已启动
if ! curl -s http://localhost:12000/api/jasypt/algorithms > /dev/null; then
    echo "启动后端服务..."
    cd backend
    mvn spring-boot:run > backend.log 2>&1 &
    BACKEND_PID=$!
    echo "后端服务 PID: $BACKEND_PID"
    
    # 等待后端启动
    echo "等待后端服务启动..."
    for i in {1..30}; do
        if curl -s http://localhost:12000/api/jasypt/algorithms > /dev/null; then
            echo "后端服务启动成功!"
            break
        fi
        sleep 1
        echo -n "."
    done
    
    if ! curl -s http://localhost:12000/api/jasypt/algorithms > /dev/null; then
        echo "后端服务启动失败!"
        exit 1
    fi
    cd ..
else
    echo "后端服务已在运行"
fi

# 启动前端
echo "启动前端应用..."
cd frontend

# 设置显示环境变量（如果在无头环境中）
export DISPLAY=:99
export ELECTRON_DISABLE_SECURITY_WARNINGS=true

# 启动 Electron 应用
npm start

echo "应用已退出"