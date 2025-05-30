package cn.com.mooyea.jasypt.dto

import jakarta.validation.constraints.NotBlank

data class EncryptRequest(
    @field:NotBlank(message = "明文不能为空")
    val plainText: String,
    
    @field:NotBlank(message = "密码不能为空")
    val password: String,
    
    @field:NotBlank(message = "算法不能为空")
    val algorithm: String
)

data class DecryptRequest(
    @field:NotBlank(message = "密文不能为空")
    val cipherText: String,
    
    @field:NotBlank(message = "密码不能为空")
    val password: String,
    
    @field:NotBlank(message = "算法不能为空")
    val algorithm: String
)

data class GenerateSaltRequest(
    val length: Int = 16
)