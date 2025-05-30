// UI 工具函数和组件
class UIManager {
    constructor() {
        this.messageContainer = document.getElementById('messageContainer');
        this.loadingOverlay = document.getElementById('loadingOverlay');
    }

    // 显示消息
    showMessage(message, type = 'info', duration = 3000) {
        const messageElement = document.createElement('div');
        messageElement.className = `message ${type}`;
        messageElement.textContent = message;

        this.messageContainer.appendChild(messageElement);

        // 触发动画
        setTimeout(() => {
            messageElement.classList.add('show');
        }, 100);

        // 自动移除
        setTimeout(() => {
            messageElement.classList.remove('show');
            setTimeout(() => {
                if (messageElement.parentNode) {
                    messageElement.parentNode.removeChild(messageElement);
                }
            }, 300);
        }, duration);
    }

    // 显示成功消息
    showSuccess(message, duration = 3000) {
        this.showMessage(message, 'success', duration);
    }

    // 显示错误消息
    showError(message, duration = 5000) {
        this.showMessage(message, 'error', duration);
    }

    // 显示警告消息
    showWarning(message, duration = 4000) {
        this.showMessage(message, 'warning', duration);
    }

    // 显示信息消息
    showInfo(message, duration = 3000) {
        this.showMessage(message, 'info', duration);
    }

    // 显示加载遮罩
    showLoading() {
        this.loadingOverlay.classList.add('show');
    }

    // 隐藏加载遮罩
    hideLoading() {
        this.loadingOverlay.classList.remove('show');
    }

    // 更新状态文本
    updateStatus(text, type = 'info') {
        const statusElement = document.getElementById('statusText');
        statusElement.textContent = text;
        statusElement.className = `status-value ${type}`;
    }

    // 更新中文状态
    updateChineseStatus(hasChinese) {
        const chineseElement = document.getElementById('chineseStatus');
        chineseElement.textContent = hasChinese ? '是' : '否';
        chineseElement.style.color = hasChinese ? '#f56565' : '#48bb78';
    }

    // 更新服务器状态
    updateServerStatus(isConnected) {
        const serverElement = document.getElementById('serverStatus');
        serverElement.textContent = isConnected ? '已连接' : '连接失败';
        serverElement.style.color = isConnected ? '#48bb78' : '#f56565';
    }

    // 复制到剪贴板
    async copyToClipboard(text) {
        try {
            await navigator.clipboard.writeText(text);
            this.showSuccess('已复制到剪贴板');
            return true;
        } catch (error) {
            console.error('复制失败:', error);
            this.showError('复制失败');
            return false;
        }
    }

    // 格式化时间
    formatTime(dateString) {
        const date = new Date(dateString);
        return date.toLocaleString('zh-CN', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
            hour: '2-digit',
            minute: '2-digit',
            second: '2-digit'
        });
    }

    // 截断文本
    truncateText(text, maxLength = 50) {
        if (text.length <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength) + '...';
    }

    // 创建记录项元素
    createRecordElement(record) {
        const recordElement = document.createElement('div');
        recordElement.className = 'record-item';
        recordElement.dataset.recordId = record.id;

        const typeClass = record.operationType.toLowerCase();
        const typeText = record.operationType === 'ENCRYPT' ? '加密' : '解密';
        
        recordElement.innerHTML = `
            <div class="record-header">
                <span class="record-type ${typeClass}">${typeText}</span>
                <span class="record-time">${this.formatTime(record.createTime)}</span>
            </div>
            <div class="record-content">
                <div class="record-text">
                    <strong>明文:</strong> ${this.truncateText(record.plainText)}
                </div>
                <div class="record-text">
                    <strong>密文:</strong> ${this.truncateText(record.cipherText)}
                </div>
                <div class="record-algorithm">
                    算法: ${record.algorithm} | 中文: ${record.isContainChinese ? '是' : '否'}
                </div>
            </div>
        `;

        // 添加点击事件
        recordElement.addEventListener('click', () => {
            this.selectRecord(record);
        });

        return recordElement;
    }

    // 选择记录
    selectRecord(record) {
        // 填充表单
        document.getElementById('plainText').value = record.plainText;
        document.getElementById('cipherText').value = record.cipherText;
        document.getElementById('algorithm').value = record.algorithm;
        
        // 更新中文状态
        this.updateChineseStatus(record.isContainChinese);
        
        this.showInfo('已选择记录');
    }

    // 清空表单
    clearForm() {
        document.getElementById('plainText').value = '';
        document.getElementById('cipherText').value = '';
        document.getElementById('password').value = '';
        this.updateStatus('就绪');
        this.updateChineseStatus(false);
    }

    // 交换明文和密文
    swapTexts() {
        const plainTextElement = document.getElementById('plainText');
        const cipherTextElement = document.getElementById('cipherText');
        
        const temp = plainTextElement.value;
        plainTextElement.value = cipherTextElement.value;
        cipherTextElement.value = temp;
        
        this.showInfo('已交换明文和密文');
    }

    // 验证表单
    validateForm() {
        const password = document.getElementById('password').value.trim();
        const algorithm = document.getElementById('algorithm').value;
        
        if (!password) {
            this.showError('请输入密码');
            return false;
        }
        
        if (!algorithm) {
            this.showError('请选择加密算法');
            return false;
        }
        
        return true;
    }

    // 验证加密表单
    validateEncryptForm() {
        if (!this.validateForm()) {
            return false;
        }
        
        const plainText = document.getElementById('plainText').value.trim();
        if (!plainText) {
            this.showError('请输入要加密的明文');
            return false;
        }
        
        return true;
    }

    // 验证解密表单
    validateDecryptForm() {
        if (!this.validateForm()) {
            return false;
        }
        
        const cipherText = document.getElementById('cipherText').value.trim();
        if (!cipherText) {
            this.showError('请输入要解密的密文');
            return false;
        }
        
        return true;
    }
}

// 创建全局 UI 管理器实例
const uiManager = new UIManager();