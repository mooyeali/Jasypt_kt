package cn.com.mooyea.jasypt.controller

import cn.com.mooyea.jasypt.dto.ApiResponse
import cn.com.mooyea.jasypt.dto.JasyptRecordResponse
import cn.com.mooyea.jasypt.service.JasyptRecordService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/records")
@CrossOrigin(origins = ["*"])
class JasyptRecordController(
    private val jasyptRecordService: JasyptRecordService
) {

    @GetMapping
    fun getAllRecords(): ResponseEntity<ApiResponse<List<JasyptRecordResponse>>> {
        return try {
            val records = jasyptRecordService.getAllRecords()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "获取记录成功",
                    data = records
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(false, "获取记录失败: ${e.message}")
            )
        }
    }

    @GetMapping("/operation/{operationType}")
    fun getRecordsByOperationType(@PathVariable operationType: String): ResponseEntity<ApiResponse<List<JasyptRecordResponse>>> {
        return try {
            val records = jasyptRecordService.getRecordsByOperationType(operationType)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "获取记录成功",
                    data = records
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(false, "获取记录失败: ${e.message}")
            )
        }
    }

    @GetMapping("/algorithm/{algorithm}")
    fun getRecordsByAlgorithm(@PathVariable algorithm: String): ResponseEntity<ApiResponse<List<JasyptRecordResponse>>> {
        return try {
            val records = jasyptRecordService.getRecordsByAlgorithm(algorithm)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "获取记录成功",
                    data = records
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(false, "获取记录失败: ${e.message}")
            )
        }
    }

    @GetMapping("/time-range")
    fun getRecordsByTimeRange(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startTime: LocalDateTime,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endTime: LocalDateTime
    ): ResponseEntity<ApiResponse<List<JasyptRecordResponse>>> {
        return try {
            val records = jasyptRecordService.getRecordsByTimeRange(startTime, endTime)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "获取记录成功",
                    data = records
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(false, "获取记录失败: ${e.message}")
            )
        }
    }

    @DeleteMapping("/{id}")
    fun deleteRecord(@PathVariable id: Long): ResponseEntity<ApiResponse<Boolean>> {
        return try {
            val success = jasyptRecordService.deleteRecord(id)
            if (success) {
                ResponseEntity.ok(
                    ApiResponse(
                        success = true,
                        message = "删除记录成功",
                        data = true
                    )
                )
            } else {
                ResponseEntity.badRequest().body(
                    ApiResponse(false, "删除记录失败")
                )
            }
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(false, "删除记录失败: ${e.message}")
            )
        }
    }

    @DeleteMapping("/clear")
    fun clearAllRecords(): ResponseEntity<ApiResponse<Boolean>> {
        return try {
            val success = jasyptRecordService.clearAllRecords()
            if (success) {
                ResponseEntity.ok(
                    ApiResponse(
                        success = true,
                        message = "清空记录成功",
                        data = true
                    )
                )
            } else {
                ResponseEntity.badRequest().body(
                    ApiResponse(false, "清空记录失败")
                )
            }
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(false, "清空记录失败: ${e.message}")
            )
        }
    }
}