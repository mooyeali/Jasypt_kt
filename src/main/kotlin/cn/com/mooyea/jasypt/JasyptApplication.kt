package cn.com.mooyea.jasypt

import cn.com.mooyea.jasypt.fxml.ui.JasyptMainUI
import de.felixroske.jfxsupport.AbstractFxmlView
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport
import javafx.event.Event
import javafx.stage.Modality
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * JasyptApplication
 *
 * Copyright (C), 星期三,08,11月,2023
 *  * Info:
 *  *      Author       :    mooye
 *  *      Work_Email   :    lidy@skyvis.com.cn
 *  *      E-mail       :    mooyeali@yeah.net
 *  *      Date         :    2023/11/8
 *  *      Version      :    v_1.0.0
 *  *      Description  :    启动类
 *  * History:
 *  *      Author      :      mooye
 *  *      Time        :      11:13 2023/11/8
 *  *      Version     :      v_1.0.0
 *  *      Description :      启动类
 *  *
 *@author mooye
 *
 */
@SpringBootApplication
class JasyptApplication: AbstractJavaFxApplicationSupport() {
    companion object{
        fun showUI(window: Class<out AbstractFxmlView>, mode: Modality) {
            showView(window, mode)
        }
    }
}

fun main(args: Array<String>) {
    AbstractJavaFxApplicationSupport.launch(JasyptApplication::class.java, JasyptMainUI::class.java, args)
}
