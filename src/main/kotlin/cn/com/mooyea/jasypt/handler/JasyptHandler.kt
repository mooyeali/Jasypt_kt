package cn.com.mooyea.jasypt.handler

import org.jasypt.encryption.StringEncryptor
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig
import java.util.regex.Pattern

/**
 * JasyptHandler
 *
 * Copyright (C), 星期五,03,11月,2023
 *  * Info:
 *  *      Author       :    mooye
 *  *      Work_Email   :    lidy@skyvis.com.cn
 *  *      E-mail       :    mooyeali@yeah.net
 *  *      Date         :    2023/11/3
 *  *      Version      :    v_1.0.0
 *  *      Description  :    Jasypt 加解密工具类
 *  * History:
 *  *      Author      :      mooye
 *  *      Time        :      12:11 2023/11/3
 *  *      Version     :      v_1.0.0
 *  *      Description :      Jasypt 加解密工具类
 *  *
 *@author mooye
 *
 */
class JasyptHandler {

    // 静态方法
    companion object {
        /**
         * 初始化生成加密器
         *
         * @param password 加密种子
         * @param algorithm 加密算法
         * @return
         */
        @JvmStatic
        private fun initEncryptor(password: String, algorithm: String): StringEncryptor {
            val encryptor = PooledPBEStringEncryptor()
            val config = SimpleStringPBEConfig()
            config.setPassword(password)
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
         *
         * @param context 明文
         * @param password 加密种子
         * @param algorithm 加密算法
         * @return 密文
         */
        @JvmStatic
        fun encode(context: String?, password: String, algorithm: String): String {
            val encryptor = initEncryptor(password, algorithm)
            return encryptor.encrypt(context)
        }

        /**
         * 解密
         *
         * @param context 密文
         * @param password 加密种子
         * @param algorithm 加密算法
         * @return 明文
         */
        @JvmStatic
        fun decode(context: String?, password: String, algorithm: String): String {
            val encryptor = initEncryptor(password, algorithm)
            return encryptor.decrypt(context)
        }
        @JvmStatic
        fun verifyKeyParameters(password: String?, algorithm: String?): Boolean {
            return (password == null || algorithm == null || password.isEmpty() || algorithm.isEmpty())
        }
        /**
         * 判断明文是否包含中文
         *
         * @param clearText 明文
         * @return true:包含中文 false:不包含中文
         */
        fun isContainChinese(clearText: String?): Boolean {
            if (clearText == null) {
                return false
            }
            // 判断明文是否包含中文
            val pattern = Pattern.compile("[\u4E00-\u9FA5|\\！|\\，|\\。|\\（|\\）|\\《|\\》|\\“|\\”|\\？|\\：|\\；|\\【|\\】]")
            val matcher = pattern.matcher(clearText)
            return matcher.find()
        }
    }
}

fun main(args: Array<String>) {
    println(JasyptHandler.isContainChinese("是不是啊"))
}
