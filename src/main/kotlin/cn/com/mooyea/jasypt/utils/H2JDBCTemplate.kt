package cn.com.mooyea.jasypt.utils

import cn.com.mooyea.jasypt.annotations.Slf4k
import cn.com.mooyea.jasypt.annotations.Slf4k.Companion.log
import cn.com.mooyea.jasypt.entity.JasyptRecordEntity
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import java.sql.SQLException

/**
 * H2JDBCTemplate
 *
 * Copyright (C), 星期一,06,11月,2023
 *  * Info:
 *  *      Author       :    mooye
 *  *      Work_Email   :    lidy@skyvis.com.cn
 *  *      E-mail       :    mooyeali@yeah.net
 *  *      Date         :    2023/11/6
 *  *      Version      :    v_1.0.0
 *  *      Description  :    H2数据库模板
 *  * History:
 *  *      Author      :      mooye
 *  *      Time        :      15:50 2023/11/6
 *  *      Version     :      v_1.0.0
 *  *      Description :      H2数据库模板
 *  *
 *@author mooye
 *
 */
@Slf4k
class H2JDBCTemplate {
    fun select(query: String, params: Map<Int, String>): ObservableList<JasyptRecordEntity> {
        val list: ObservableList<JasyptRecordEntity> = FXCollections.observableArrayList()
        try {
            H2JDBCUtil.connection.use { connection ->
                connection!!.prepareStatement(query).use { preparedStatement ->
                    for ((key, value) in params) {
                        preparedStatement.setString(key, value)
                    }
                    log.info("即将执行的 SQL:{}",preparedStatement)
                    val rs = preparedStatement.executeQuery()
                    while (rs.next()) {
                        val jasyptRecordEntity = JasyptRecordEntity(
                            rs.getString("cleartext"),
                            rs.getString("salt"),
                            rs.getString("algorithm"),
                            rs.getString("encrypt")
                        )
                        list.add(jasyptRecordEntity)
                    }
                }
            }
        } catch (e: SQLException) {
            H2JDBCUtil.printSQLException(e)
        }
        return list
    }

    companion object {
//        @JvmStatic
//        fun select(query: String, params: Map<Int, String>): ObservableList<JasyptRecordEntity> {
//            val list: ObservableList<JasyptRecordEntity> = FXCollections.observableArrayList()
//            try {
//                H2JDBCUtil.connection.use { connection ->
//                    connection!!.prepareStatement(query).use { preparedStatement ->
//                        for ((key, value) in params) {
//                            preparedStatement.setString(key, value)
//                        }
//                        log.info("即将执行的 SQL:{}",preparedStatement)
//                        val rs = preparedStatement.executeQuery()
//                        while (rs.next()) {
//                            val jasyptRecordEntity = JasyptRecordEntity(
//                                rs.getString("cleartext"),
//                                rs.getString("salt"),
//                                rs.getString("algorithm"),
//                                rs.getString("encrypt")
//                            )
//                            list.add(jasyptRecordEntity)
//                        }
//                    }
//                }
//            } catch (e: SQLException) {
//                H2JDBCUtil.printSQLException(e)
//            }
//            return list
//        }
        /**
         * 插入
         */
        @JvmStatic
        fun insert(query: String, params: Map<Int, String>): Int {
            var result = 0
            try {
                H2JDBCUtil.connection.use { connection ->
                    connection!!.prepareStatement(query).use { preparedStatement ->
                        for ((key, value) in params) {
                            preparedStatement.setString(key, value)
                        }
                        log.info("即将执行的 SQL:{}", preparedStatement)
                        result = preparedStatement.executeUpdate()
                    }
                }
            } catch (e: SQLException) {
                H2JDBCUtil.printSQLException(e)
            }
            return result
        }
    }


}
