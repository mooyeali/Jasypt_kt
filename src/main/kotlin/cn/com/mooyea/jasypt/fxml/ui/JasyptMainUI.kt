package cn.com.mooyea.jasypt.fxml.ui

import de.felixroske.jfxsupport.AbstractFxmlView
import de.felixroske.jfxsupport.FXMLView
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage

@FXMLView(value = "/fxml/main.fxml",encoding = "UTF-8", title = "Jasypt 加密/解密工具")
class JasyptMainUI : AbstractFxmlView() {
//    override fun start(stage: Stage) {
//        val fxmlLoader = FXMLLoader(JasyptMainUI::class.java.getResource("/fxml/main.fxml"))
//        val screenRectangle = Screen.getPrimary().bounds
//        val scene = Scene(fxmlLoader.load(), screenRectangle.width * 0.75, screenRectangle.height * 0.5)
////        stage.isResizable = false
//        stage.title = "Jasypt 加密/解密工具"
//        stage.scene = scene
//        stage.show()
//    }
//
//    fun showWindow(stage: Stage){
//        start(stage)
//    }
}



//@OptIn(DelicateCoroutinesApi::class)
//fun main() {
//    // 处理未捕获异常,防止程序崩溃
//    Thread.setDefaultUncaughtExceptionHandler { _, e ->
//        LogManager.getLogger(JasyptMainUI::class.java).error(e.message, e)
//    }
//
//    // 使用协程初始化数据库
//    GlobalScope.launch {
//        InitH2Table().createTable()
//    }
//    Application.launch(JasyptMainUI::class.java)
//}
