// 主应用逻辑
class JasyptApp {
    constructor() {
        this.currentRecords = [];
        this.algorithms = [];
        this.init();
    }

    async init() {
        this.bindEvents();
        await this.loadInitialData();
        this.startPeriodicCheck();
    }

    // 绑定事件
    bindEvents() {
        // 加密按钮
        document.getElementById('encryptBtn').addEventListener('click', () => {
            this.handleEncrypt();
        });

        // 解密按钮
        document.getElementById('decryptBtn').addEventListener('click', () => {
            this.handleDecrypt();
        });

        // 清空表单按钮
        document.getElementById('clearFormBtn').addEventListener('click', () => {
            uiManager.clearForm();
        });

        // 交换按钮
        document.getElementById('swapBtn').addEventListener('click', () => {
            uiManager.swapTexts();
        });

        // 生成盐值按钮
        document.getElementById('generateSalt').addEventListener('click', () => {
            this.handleGenerateSalt();
        });

        // 复制密文按钮
        document.getElementById('copyCipher').addEventListener('click', () => {
            const cipherText = document.getElementById('cipherText').value;
            if (cipherText) {
                uiManager.copyToClipboard(cipherText);
            } else {
                uiManager.showWarning('没有可复制的密文');
            }
        });

        // 密码显示/隐藏切换
        document.getElementById('togglePassword').addEventListener('click', () => {
            this.togglePasswordVisibility();
        });

        // 刷新按钮
        document.getElementById('refreshBtn').addEventListener('click', () => {
            this.refreshData();
        });

        // 清空记录按钮
        document.getElementById('clearBtn').addEventListener('click', () => {
            this.handleClearRecords();
        });

        // 过滤器
        document.getElementById('operationFilter').addEventListener('change', () => {
            this.filterRecords();
        });

        document.getElementById('algorithmFilter').addEventListener('change', () => {
            this.filterRecords();
        });

        // 键盘快捷键
        document.addEventListener('keydown', (e) => {
            if (e.ctrlKey || e.metaKey) {
                switch (e.key) {
                    case 'e':
                        e.preventDefault();
                        this.handleEncrypt();
                        break;
                    case 'd':
                        e.preventDefault();
                        this.handleDecrypt();
                        break;
                    case 'n':
                        e.preventDefault();
                        uiManager.clearForm();
                        break;
                }
            }
        });
    }

    // 加载初始数据
    async loadInitialData() {
        uiManager.showLoading();
        
        try {
            // 检查服务器状态
            const isServerConnected = await apiClient.checkServerStatus();
            uiManager.updateServerStatus(isServerConnected);
            
            if (!isServerConnected) {
                uiManager.showError('无法连接到服务器，请确保后端服务已启动');
                return;
            }

            // 加载算法列表
            await this.loadAlgorithms();
            
            // 加载记录
            await this.loadRecords();
            
            uiManager.updateStatus('就绪');
        } catch (error) {
            console.error('加载初始数据失败:', error);
            uiManager.showError('加载数据失败: ' + error.message);
        } finally {
            uiManager.hideLoading();
        }
    }

    // 加载算法列表
    async loadAlgorithms() {
        try {
            const response = await apiClient.getAlgorithms();
            if (response.success) {
                this.algorithms = response.data.algorithms;
                this.populateAlgorithmSelect();
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('加载算法失败:', error);
            uiManager.showError('加载算法失败: ' + error.message);
        }
    }

    // 填充算法选择框
    populateAlgorithmSelect() {
        const algorithmSelect = document.getElementById('algorithm');
        const algorithmFilter = document.getElementById('algorithmFilter');
        
        // 清空现有选项
        algorithmSelect.innerHTML = '<option value="">请选择算法...</option>';
        algorithmFilter.innerHTML = '<option value="">全部算法</option>';
        
        // 添加算法选项
        this.algorithms.forEach(algorithm => {
            const option1 = document.createElement('option');
            option1.value = algorithm;
            option1.textContent = algorithm;
            algorithmSelect.appendChild(option1);
            
            const option2 = document.createElement('option');
            option2.value = algorithm;
            option2.textContent = algorithm;
            algorithmFilter.appendChild(option2);
        });
    }

    // 加载记录
    async loadRecords() {
        try {
            const response = await apiClient.getAllRecords();
            if (response.success) {
                this.currentRecords = response.data || [];
                this.displayRecords(this.currentRecords);
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('加载记录失败:', error);
            uiManager.showError('加载记录失败: ' + error.message);
        }
    }

    // 显示记录
    displayRecords(records) {
        const recordsList = document.getElementById('recordsList');
        
        if (records.length === 0) {
            recordsList.innerHTML = `
                <div class="no-records">
                    <i class="fas fa-inbox"></i>
                    <p>暂无操作记录</p>
                </div>
            `;
            return;
        }
        
        recordsList.innerHTML = '';
        records.forEach(record => {
            const recordElement = uiManager.createRecordElement(record);
            recordsList.appendChild(recordElement);
        });
    }

    // 处理加密
    async handleEncrypt() {
        if (!uiManager.validateEncryptForm()) {
            return;
        }

        const plainText = document.getElementById('plainText').value.trim();
        const password = document.getElementById('password').value.trim();
        const algorithm = document.getElementById('algorithm').value;

        uiManager.showLoading();
        uiManager.updateStatus('加密中...');

        try {
            const response = await apiClient.encrypt(plainText, password, algorithm);
            
            if (response.success) {
                document.getElementById('cipherText').value = response.data.cipherText;
                uiManager.updateChineseStatus(response.data.isContainChinese);
                uiManager.updateStatus('加密成功');
                uiManager.showSuccess('加密成功');
                
                // 刷新记录
                await this.loadRecords();
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('加密失败:', error);
            uiManager.updateStatus('加密失败');
            uiManager.showError('加密失败: ' + error.message);
        } finally {
            uiManager.hideLoading();
        }
    }

    // 处理解密
    async handleDecrypt() {
        if (!uiManager.validateDecryptForm()) {
            return;
        }

        const cipherText = document.getElementById('cipherText').value.trim();
        const password = document.getElementById('password').value.trim();
        const algorithm = document.getElementById('algorithm').value;

        uiManager.showLoading();
        uiManager.updateStatus('解密中...');

        try {
            const response = await apiClient.decrypt(cipherText, password, algorithm);
            
            if (response.success) {
                document.getElementById('plainText').value = response.data.plainText;
                uiManager.updateChineseStatus(response.data.isContainChinese);
                uiManager.updateStatus('解密成功');
                uiManager.showSuccess('解密成功');
                
                // 刷新记录
                await this.loadRecords();
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('解密失败:', error);
            uiManager.updateStatus('解密失败');
            uiManager.showError('解密失败: ' + error.message);
        } finally {
            uiManager.hideLoading();
        }
    }

    // 处理生成盐值
    async handleGenerateSalt() {
        try {
            const response = await apiClient.generateSalt(16);
            
            if (response.success) {
                document.getElementById('password').value = response.data.salt;
                uiManager.showSuccess('已生成随机盐值');
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('生成盐值失败:', error);
            uiManager.showError('生成盐值失败: ' + error.message);
        }
    }

    // 切换密码可见性
    togglePasswordVisibility() {
        const passwordInput = document.getElementById('password');
        const toggleButton = document.getElementById('togglePassword');
        const icon = toggleButton.querySelector('i');
        
        if (passwordInput.type === 'password') {
            passwordInput.type = 'text';
            icon.className = 'fas fa-eye-slash';
        } else {
            passwordInput.type = 'password';
            icon.className = 'fas fa-eye';
        }
    }

    // 刷新数据
    async refreshData() {
        uiManager.showInfo('正在刷新数据...');
        await this.loadRecords();
    }

    // 处理清空记录
    async handleClearRecords() {
        if (!confirm('确定要清空所有记录吗？此操作不可恢复。')) {
            return;
        }

        uiManager.showLoading();
        
        try {
            const response = await apiClient.clearAllRecords();
            
            if (response.success) {
                this.currentRecords = [];
                this.displayRecords([]);
                uiManager.showSuccess('已清空所有记录');
            } else {
                throw new Error(response.message);
            }
        } catch (error) {
            console.error('清空记录失败:', error);
            uiManager.showError('清空记录失败: ' + error.message);
        } finally {
            uiManager.hideLoading();
        }
    }

    // 过滤记录
    filterRecords() {
        const operationFilter = document.getElementById('operationFilter').value;
        const algorithmFilter = document.getElementById('algorithmFilter').value;
        
        let filteredRecords = this.currentRecords;
        
        if (operationFilter) {
            filteredRecords = filteredRecords.filter(record => 
                record.operationType === operationFilter
            );
        }
        
        if (algorithmFilter) {
            filteredRecords = filteredRecords.filter(record => 
                record.algorithm === algorithmFilter
            );
        }
        
        this.displayRecords(filteredRecords);
    }

    // 定期检查服务器状态
    startPeriodicCheck() {
        setInterval(async () => {
            const isConnected = await apiClient.checkServerStatus();
            uiManager.updateServerStatus(isConnected);
        }, 30000); // 每30秒检查一次
    }
}

// 应用启动
document.addEventListener('DOMContentLoaded', () => {
    new JasyptApp();
});