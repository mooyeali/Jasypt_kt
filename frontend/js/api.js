// API 配置和请求处理
const API_BASE_URL = 'http://localhost:12000/api';

class ApiClient {
    constructor() {
        this.baseURL = API_BASE_URL;
        this.timeout = 10000; // 10秒超时
    }

    async request(method, url, data = null) {
        const config = {
            method,
            headers: {
                'Content-Type': 'application/json',
            },
            timeout: this.timeout
        };

        if (data) {
            config.body = JSON.stringify(data);
        }

        try {
            const response = await fetch(`${this.baseURL}${url}`, config);
            
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            
            const result = await response.json();
            return result;
        } catch (error) {
            console.error('API request failed:', error);
            throw error;
        }
    }

    // 加密
    async encrypt(plainText, password, algorithm) {
        return this.request('POST', '/jasypt/encrypt', {
            plainText,
            password,
            algorithm
        });
    }

    // 解密
    async decrypt(cipherText, password, algorithm) {
        return this.request('POST', '/jasypt/decrypt', {
            cipherText,
            password,
            algorithm
        });
    }

    // 生成盐值
    async generateSalt(length = 16) {
        return this.request('POST', '/jasypt/generate-salt', {
            length
        });
    }

    // 获取支持的算法
    async getAlgorithms() {
        return this.request('GET', '/jasypt/algorithms');
    }

    // 获取所有记录
    async getAllRecords() {
        return this.request('GET', '/records');
    }

    // 根据操作类型获取记录
    async getRecordsByOperation(operationType) {
        return this.request('GET', `/records/operation/${operationType}`);
    }

    // 根据算法获取记录
    async getRecordsByAlgorithm(algorithm) {
        return this.request('GET', `/records/algorithm/${algorithm}`);
    }

    // 删除记录
    async deleteRecord(id) {
        return this.request('DELETE', `/records/${id}`);
    }

    // 清空所有记录
    async clearAllRecords() {
        return this.request('DELETE', '/records/clear');
    }

    // 检查服务器状态
    async checkServerStatus() {
        try {
            await this.request('GET', '/jasypt/algorithms');
            return true;
        } catch (error) {
            return false;
        }
    }
}

// 创建全局 API 客户端实例
const apiClient = new ApiClient();