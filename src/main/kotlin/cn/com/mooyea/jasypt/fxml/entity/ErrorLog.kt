package cn.com.mooyea.jasypt.fxml.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data
import lombok.EqualsAndHashCode
import lombok.ToString
import org.springframework.format.annotation.DateTimeFormat
import java.io.Serializable
import java.util.Date
import java.util.UUID
import javax.persistence.Column

@TableName("ERROR_LOG")
@Data
@EqualsAndHashCode
@ToString
data class ErrorLog(
    @TableId(type = IdType.ASSIGN_ID)
    var id: String = UUID.randomUUID().toString().replace("-", ""),
    @Column(name = "ERROR_DATA")
    var errorData: String,
    @Column(name = "HANDLER_STATUS")
    var handlerStatus: Int = 0,
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name = "CREATE_TIME")
    var createTime: Date = Date()
): Serializable {
    // 序列化 id
    private companion object {
        private const val serialVersionUID = 1L
    }
}
