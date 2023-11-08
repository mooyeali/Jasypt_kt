package cn.com.mooyea.jasypt.init

import cn.com.mooyea.jasypt.annotations.Slf4k
import cn.com.mooyea.jasypt.annotations.Slf4k.Companion.log
import cn.com.mooyea.jasypt.utils.H2JDBCUtil.connection
import cn.com.mooyea.jasypt.utils.H2JDBCUtil.printSQLException
import java.sql.SQLException

/**
 * <h1>InitH2Table<h1>
 *
 * Copyright (C), 星期一,06,11月,2023
 * <br></br>
 * <hr></hr>
 * <h3>File Info:</h3>
 *
 * FileName: InitH2Table
 *
 * Author:   mooye
 *
 * Work_Email： lidy@skyvis.com.cn
 *
 * E-mail： mooyeali@yeah.net
 *
 * Date:     2023/11/6
 *
 * Description: 初始化 H2 数据库并创建表
 * <hr></hr>
 * <h3>History:</h3>
 * <hr></hr>
 * <table>
 * <thead>
 * <tr><td style='width:100px;' center>Author</td><td style='width:200px;' center>Time</td><td style='width:100px;' center>Version_Number</td><td style='width:100px;' center>Description</td></tr>
</thead> *
 * <tbody>
 * <tr><td style='width:100px;' center>mooye</td><td style='width:200px;' center>14:57 2023/11/6</td><td style='width:100px;' center>v_1.0.0</td><td style='width:100px;' center>创建</td></tr>
</tbody> *
</table> *
 * <hr></hr>
 * <br></br>
 *
 * @author mooye
</h1></h1> */
@Slf4k
class InitH2Table {
    fun createTable() {
        log.info("============开始创建表============")
        try {
            connection.use { connection ->
                connection!!.createStatement().use { statement ->
                    val rs = connection.prepareStatement(EXISTENCE).executeQuery()
                    // 获取结果集的元数据
                    if (rs.next()) {
                        println(rs.getBoolean(1))
                        if (!rs.getBoolean(1)) {
                            statement.execute(CREATE_TABLE_SQL)
                            log.info("表创建成功")
                        } else {
                            log.warn("表已存在")
                        }
                    } else {
                        statement.execute(CREATE_TABLE_SQL)
                    }
                }
            }
        } catch (e: SQLException) {
            // print SQL exception information
            printSQLException(e)
        }
        log.info("============创建表结束============")
    }
    companion object {
        private const val CREATE_TABLE_SQL =
            "create table records ( cleartext  varchar(300) , salt varchar(200), algorithm varchar(200),  encrypt varchar(1000));"
        private const val EXISTENCE =
            "SELECT EXISTS(SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'records' or TABLE_NAME = 'RECORDS');"
    }
}

