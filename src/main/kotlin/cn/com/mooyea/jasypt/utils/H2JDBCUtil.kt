package cn.com.mooyea.jasypt.utils

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * <h1>H2JDBCUtil<h1>
 *
 * Copyright (C), 星期一,06,11月,2023
 * <br></br>
 * <hr></hr>
 * <h3>File Info:</h3>
 *
 * FileName: H2JDBCUtil
 *
 * Author:   mooye
 *
 * Work_Email： lidy@skyvis.com.cn
 *
 * E-mail： mooyeali@yeah.net
 *
 * Date:     2023/11/6
 *
 * Description: H2数据库连接类
 * <hr></hr>
 * <h3>History:</h3>
 * <hr></hr>
 * <table>
 * <thead>
 * <tr><td style='width:100px;' center>Author</td><td style='width:200px;' center>Time</td><td style='width:100px;' center>Version_Number</td><td style='width:100px;' center>Description</td></tr>
</thead> *
 * <tbody>
 * <tr><td style='width:100px;' center>mooye</td><td style='width:200px;' center>14:40 2023/11/6</td><td style='width:100px;' center>v_1.0.0</td><td style='width:100px;' center>创建</td></tr>
</tbody> *
</table> *
 * <hr></hr>
 * <br></br>
 *
 * @author mooye
</h1></h1> */
object H2JDBCUtil {
    private const val JDBC_URL = "jdbc:h2:~/history;AUTO_SERVER=TRUE"
    private const val JDBC_USERNAME = "jasypt"
    private const val JDBC_PASSWORD = "jasypt!@#_H2"
    @JvmStatic
    val connection: Connection?
        get() {
            var connection: Connection? = null
            try {
                connection = DriverManager.getConnection(JDBC_URL, JDBC_USERNAME, JDBC_PASSWORD)
            } catch (e: SQLException) {
                e.printStackTrace()
            }
            return connection
        }

    @JvmStatic
    fun printSQLException(ex: SQLException) {
        for (e in ex) {
            if (e is SQLException) {
                e.printStackTrace(System.err)
                System.err.println("SQLState: " + e.sqlState)
                System.err.println("Error Code: " + e.errorCode)
                System.err.println("Message: " + e.message)
                var t = ex.cause
                while (t != null) {
                    println("Cause: $t")
                    t = t.cause
                }
            }
        }
    }

}
