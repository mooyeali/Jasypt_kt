package cn.com.mooyea.jasypt.fxml.entity

import com.baomidou.mybatisplus.annotation.IdType
import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*


/**
 * <h1>JasyptRecordEntity<h1>
 * <p>Copyright (C), 星期一,06,11月,2023</p>
 * <br/>
 * <hr>
 * <h3>File Info:</h3>
 * <p>FileName: JasyptRecordEntity</p>
 * <p>Author:   mooye</p>
 * <p>Work_Email： lidy@skyvis.com.cn</p>
 * <p>E-mail： mooyeali@yeah.net</p>
 * <p>Date:     2023/11/6</p>
 * <p>Description: 历史记录实体类</p>
 * <hr>
 * <h3>History:</h3>
 * <hr>
 * <table>
 *  <thead>
 *  <tr><td style='width:100px;' center>Author</td><td style='width:200px;' center>Time</td><td style='width:100px;' center>Version_Number</td><td style='width:100px;' center>Description</td></tr>
 *  </thead>
 *  <tbody>
 *    <tr><td style='width:100px;' center>mooye</td><td style='width:200px;' center>14:04 2023/11/6</td><td style='width:100px;' center>v_1.0.0</td><td style='width:100px;' center>创建</td></tr>
 *  </tbody>
 * </table>
 * <hr>
 * <br/>
 *@author mooye
 */

//JasyptRecordEntityRepository
@TableName("records")
data class JasyptRecordEntity(
    @TableId(type = IdType.ASSIGN_ID)
    var id: String?,
    @Column(name = "cleartext")
    var cleartext: String?,
    @Column(name = "salt")
    var salt: String?,
    @Column(name = "algorithm")
    var algorithm: String?,
    @Column(name = "encrypt")
    var encrypt: String?
) {
    constructor() : this(UUID.randomUUID().toString(), "", "", "", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JasyptRecordEntity) return false

        if (id != other.id) return false
        if (cleartext != other.cleartext) return false
        if (salt != other.salt) return false
        if (algorithm != other.algorithm) return false
        if (encrypt != other.encrypt) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + cleartext.hashCode()
        result = 31 * result + salt.hashCode()
        result = 31 * result + algorithm.hashCode()
        result = 31 * result + encrypt.hashCode()
        return result
    }

    override fun toString(): String {
        return "JasyptRecordEntity(id=$id, cleartext='$cleartext', salt='$salt', algorithm='$algorithm', encrypt='$encrypt')"
    }


}
