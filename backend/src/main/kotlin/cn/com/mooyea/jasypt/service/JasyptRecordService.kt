package cn.com.mooyea.jasypt.service

import cn.com.mooyea.jasypt.dto.JasyptRecordResponse
import cn.com.mooyea.jasypt.entity.JasyptRecord
import cn.com.mooyea.jasypt.repository.JasyptRecordRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class JasyptRecordService(
    private val jasyptRecordRepository: JasyptRecordRepository
) {

    fun saveRecord(
        plainText: String,
        cipherText: String,
        saltValue: String,
        algorithm: String,
        operationType: String,
        isContainChinese: Boolean
    ): JasyptRecord {
        val record = JasyptRecord(
            plainText = plainText,
            cipherText = cipherText,
            saltValue = saltValue,
            algorithm = algorithm,
            operationType = operationType,
            isContainChinese = isContainChinese
        )
        return jasyptRecordRepository.save(record)
    }

    fun getAllRecords(): List<JasyptRecordResponse> {
        return jasyptRecordRepository.findByOrderByCreateTimeDesc()
            .map { it.toResponse() }
    }

    fun getRecordsByOperationType(operationType: String): List<JasyptRecordResponse> {
        return jasyptRecordRepository.findByOperationTypeOrderByCreateTimeDesc(operationType)
            .map { it.toResponse() }
    }

    fun getRecordsByTimeRange(startTime: LocalDateTime, endTime: LocalDateTime): List<JasyptRecordResponse> {
        return jasyptRecordRepository.findByCreateTimeBetweenOrderByCreateTimeDesc(startTime, endTime)
            .map { it.toResponse() }
    }

    fun getRecordsByAlgorithm(algorithm: String): List<JasyptRecordResponse> {
        return jasyptRecordRepository.findByAlgorithmOrderByCreateTimeDesc(algorithm)
            .map { it.toResponse() }
    }

    fun deleteRecord(id: Long): Boolean {
        return try {
            jasyptRecordRepository.deleteById(id)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun clearAllRecords(): Boolean {
        return try {
            jasyptRecordRepository.deleteAll()
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun JasyptRecord.toResponse(): JasyptRecordResponse {
        return JasyptRecordResponse(
            id = this.id,
            plainText = this.plainText,
            cipherText = this.cipherText,
            saltValue = this.saltValue,
            algorithm = this.algorithm,
            operationType = this.operationType,
            createTime = this.createTime,
            isContainChinese = this.isContainChinese
        )
    }
}