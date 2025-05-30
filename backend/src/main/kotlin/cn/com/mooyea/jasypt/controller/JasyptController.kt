package cn.com.mooyea.jasypt.controller

import cn.com.mooyea.jasypt.dto.*
import cn.com.mooyea.jasypt.service.JasyptRecordService
import cn.com.mooyea.jasypt.service.JasyptService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/jasypt")
@CrossOrigin(origins = ["*"])
class JasyptController(
    private val jasyptService: JasyptService,
    private val jasyptRecordService: JasyptRecordService
) {

    @PostMapping("/encrypt")
    fun encrypt(@Valid @RequestBody request: EncryptRequest): ResponseEntity<ApiResponse<EncryptResponse>> {
        return try {
            if (!jasyptService.verifyKeyParameters(request.password, request.algorithm)) {
                return ResponseEntity.badRequest().body(
                    ApiResponse(false, "密码和算法不能为空")
                )
            }

            val cipherText = jasyptService.encrypt(request.plainText, request.password, request.algorithm)
            val isContainChinese = jasyptService.isContainChinese(request.plainText)

            // 保存记录
            jasyptRecordService.saveRecord(
                plainText = request.plainText,
                cipherText = cipherText,
                saltValue = request.password,
                algorithm = request.algorithm,
                operationType = "ENCRYPT",
                isContainChinese = isContainChinese
            )

            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "加密成功",
                    data = EncryptResponse(cipherText, isContainChinese)
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(false, "加密失败: ${e.message}")
            )
        }
    }

    @PostMapping("/decrypt")
    fun decrypt(@Valid @RequestBody request: DecryptRequest): ResponseEntity<ApiResponse<DecryptResponse>> {
        return try {
            if (!jasyptService.verifyKeyParameters(request.password, request.algorithm)) {
                return ResponseEntity.badRequest().body(
                    ApiResponse(false, "密码和算法不能为空")
                )
            }

            val plainText = jasyptService.decrypt(request.cipherText, request.password, request.algorithm)
            val isContainChinese = jasyptService.isContainChinese(plainText)

            // 保存记录
            jasyptRecordService.saveRecord(
                plainText = plainText,
                cipherText = request.cipherText,
                saltValue = request.password,
                algorithm = request.algorithm,
                operationType = "DECRYPT",
                isContainChinese = isContainChinese
            )

            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "解密成功",
                    data = DecryptResponse(plainText, isContainChinese)
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(false, "解密失败: ${e.message}")
            )
        }
    }

    @PostMapping("/generate-salt")
    fun generateSalt(@RequestBody request: GenerateSaltRequest): ResponseEntity<ApiResponse<GenerateSaltResponse>> {
        return try {
            val salt = jasyptService.generateRandomSalt(request.length)
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "生成盐值成功",
                    data = GenerateSaltResponse(salt)
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(false, "生成盐值失败: ${e.message}")
            )
        }
    }

    @GetMapping("/algorithms")
    fun getAlgorithms(): ResponseEntity<ApiResponse<AlgorithmsResponse>> {
        return try {
            val algorithms = jasyptService.getSupportedAlgorithms()
            ResponseEntity.ok(
                ApiResponse(
                    success = true,
                    message = "获取算法列表成功",
                    data = AlgorithmsResponse(algorithms)
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(
                ApiResponse(false, "获取算法列表失败: ${e.message}")
            )
        }
    }
}