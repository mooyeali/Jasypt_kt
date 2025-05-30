package cn.com.mooyea.jasypt.service

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import org.springframework.stereotype.Service
import java.util.regex.Pattern

@Service
class JasyptService {

    /**
     * 初始化生成加密器
     */
    private fun initEncryptor(password: String, algorithm: String): StringEncryptor {
        val encryptor = PooledPBEStringEncryptor()
        val config = SimpleStringPBEConfig()
        // 对于非ASCII密码，使用Base64编码
        val encodedPassword = if (password.any { it.code > 127 }) {
            java.util.Base64.getEncoder().encodeToString(password.toByteArray(Charsets.UTF_8))
        } else {
            password
        }
        config.setPassword(encodedPassword)
        config.algorithm = algorithm
        config.keyObtentionIterations = 1000
        config.poolSize = 1
        config.providerName = "SunJCE"
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator")
        config.setIvGeneratorClassName("org.jasypt.iv.RandomIvGenerator")
        config.stringOutputType = "base64"
        encryptor.setConfig(config)
        return encryptor
    }

    /**
     * 加密
     */
    fun encrypt(plainText: String, password: String, algorithm: String): String {
        val encryptor = initEncryptor(password, algorithm)
        return encryptor.encrypt(plainText)
    }

    /**
     * 解密
     */
    fun decrypt(cipherText: String, password: String, algorithm: String): String {
        val encryptor = initEncryptor(password, algorithm)
        return encryptor.decrypt(cipherText)
    }

    /**
     * 验证密钥参数
     */
    fun verifyKeyParameters(password: String?, algorithm: String?): Boolean {
        return !(password.isNullOrEmpty() || algorithm.isNullOrEmpty())
    }

    /**
     * 判断明文是否包含中文
     */
    fun isContainChinese(text: String?): Boolean {
        if (text == null) {
            return false
        }
        val pattern = Pattern.compile("[\\u4E00-\\u9FA5\\！\\，\\。\\（\\）\\《\\》\\？\\：\\；\\【\\】]")
        val matcher = pattern.matcher(text)
        return matcher.find()
    }

    /**
     * 生成随机盐值
     */
    fun generateRandomSalt(length: Int = 16): String {
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }

    /**
     * 获取支持的算法列表
     */
    fun getSupportedAlgorithms(): List<String> {
        return listOf(
            "PBEWITHHMACSHA512ANDAES_256",
            "PBEWithMD5AndDES"
        )
    }
}