package cn.com.mooyea.jasypt.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "jasypt_record")
data class JasyptRecord(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    
    @Column(name = "plain_text", columnDefinition = "TEXT")
    val plainText: String,
    
    @Column(name = "cipher_text", columnDefinition = "TEXT")
    val cipherText: String,
    
    @Column(name = "salt_value")
    val saltValue: String,
    
    @Column(name = "algorithm")
    val algorithm: String,
    
    @Column(name = "operation_type")
    val operationType: String, // ENCRYPT or DECRYPT
    
    @Column(name = "create_time")
    val createTime: LocalDateTime = LocalDateTime.now(),
    
    @Column(name = "is_contain_chinese")
    val isContainChinese: Boolean = false
)