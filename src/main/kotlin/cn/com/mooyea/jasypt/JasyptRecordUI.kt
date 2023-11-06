package cn.com.mooyea.jasypt

import cn.com.mooyea.jasypt.controller.JasyptRecordController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage

/**
 * <h1>JasyptRecordUI<h1>
 *
 * Copyright (C), 星期一,06,11月,2023
 * <br></br>
 * <hr></hr>
 * <h3>File Info:</h3>
 *
 * FileName: JasyptRecordUI
 *
 * Author:   mooye
 *
 * Work_Email： lidy@skyvis.com.cn
 *
 * E-mail： mooyeali@yeah.net
 *
 * Date:     2023/11/6
 *
 * Description: 加密历史记录 UI 界面
 * <hr></hr>
 * <h3>History:</h3>
 * <hr></hr>
 * <table>
 * <thead>
 * <tr><td style='width:100px;' center>Author</td><td style='width:200px;' center>Time</td><td style='width:100px;' center>Version_Number</td><td style='width:100px;' center>Description</td></tr>
</thead> *
 * <tbody>
 * <tr><td style='width:100px;' center>mooye</td><td style='width:200px;' center>10:00 2023/11/6</td><td style='width:100px;' center>v_1.0.0</td><td style='width:100px;' center>创建</td></tr>
</tbody> *
</table> *
 * <hr></hr>
 * <br></br>
 *
 * @author mooye
</h1></h1> */
class JasyptRecordUI : Application(){
    override fun start(primaryStage: Stage) {
        val fxmlLoader = FXMLLoader(JasyptRecordUI::class.java.getResource("jasypt_record.fxml"))
        val screenRectangle = Screen.getPrimary().bounds
        val scene = Scene(fxmlLoader.load(), screenRectangle.width * 0.75, screenRectangle.height * 0.5)

//        primaryStage.isResizable = false
        primaryStage.title = "Jasypt 历史记录"
        // setOnShowing() 方法在窗口显示之前执行
        primaryStage.setOnShowing {
            // 窗口显示之前，加载历史记录
            val controller = fxmlLoader.getController<JasyptRecordController>()
            controller.loadRecord()
            println("页面被打开")
        }
        primaryStage.scene = scene
        primaryStage.show()
    }
    fun showWindow(stage: Stage){
        start(stage)
    }
}

fun main(args: Array<String>) {
    Application.launch(JasyptRecordUI::class.java)
}
