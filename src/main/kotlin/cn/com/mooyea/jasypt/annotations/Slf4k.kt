package cn.com.mooyea.jasypt.annotations


import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger


/**
 * <h1>Slf4k<h1>
 * <p>Copyright (C), 星期五,03,11月,2023</p>
 * <br/>
 * <hr>
 * <h3>File Info:</h3>
 * <p>FileName: Slf4k</p>
 * <p>Author:   mooye</p>
 * <p>Work_Email： lidy@skyvis.com.cn</p>
 * <p>E-mail： mooyeali@yeah.net</p>
 * <p>Date:     2023/11/3</p>
 * <p>Description: 基于 lombok 的 Slf4j 注解适配 Kotlin 日志注解</p>
 * <hr>
 * <h3>History:</h3>
 * <hr>
 * <table>
 *  <thead>
 *  <tr><td style='width:100px;' center>Author</td><td style='width:200px;' center>Time</td><td style='width:100px;' center>Version_Number</td><td style='width:100px;' center>Description</td></tr>
 *  </thead>
 *  <tbody>
 *    <tr><td style='width:100px;' center>mooye</td><td style='width:200px;' center>15:09 2023/11/3</td><td style='width:100px;' center>v_1.0.0</td><td style='width:100px;' center>创建</td></tr>
 *  </tbody>
 * </table>
 * <hr>
 * <br/>
 *@author mooye
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Slf4k {
    companion object {
        val <reified T> T.log: Logger
            inline get() = LogManager.getLogger(T::class.java)
    }
}
