package cn.com.mooyea.jasypt.repository

import cn.com.mooyea.jasypt.entity.JasyptRecord
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface JasyptRecordRepository : JpaRepository<JasyptRecord, Long> {
    
    fun findByOrderByCreateTimeDesc(): List<JasyptRecord>
    
    fun findByOperationTypeOrderByCreateTimeDesc(operationType: String): List<JasyptRecord>
    
    @Query("SELECT r FROM JasyptRecord r WHERE r.createTime BETWEEN :startTime AND :endTime ORDER BY r.createTime DESC")
    fun findByCreateTimeBetweenOrderByCreateTimeDesc(startTime: LocalDateTime, endTime: LocalDateTime): List<JasyptRecord>
    
    fun findByAlgorithmOrderByCreateTimeDesc(algorithm: String): List<JasyptRecord>
}