package cn.com.mooyea.jasypt.dto

import java.time.LocalDateTime

data class ApiResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T? = null
)

data class EncryptResponse(
    val cipherText: String,
    val isContainChinese: Boolean
)

data class DecryptResponse(
    val plainText: String,
    val isContainChinese: Boolean
)

data class GenerateSaltResponse(
    val salt: String
)

data class AlgorithmsResponse(
    val algorithms: List<String>
)

data class JasyptRecordResponse(
    val id: Long?,
    val plainText: String,
    val cipherText: String,
    val saltValue: String,
    val algorithm: String,
    val operationType: String,
    val createTime: LocalDateTime,
    val isContainChinese: Boolean
)