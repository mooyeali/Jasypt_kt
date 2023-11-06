package cn.com.mooyea.jasypt

import cn.com.mooyea.jasypt.annotations.Slf4k
import cn.com.mooyea.jasypt.init.InitH2Table
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage
import org.apache.logging.log4j.LogManager


class JasyptCoderToolApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(JasyptCoderToolApplication::class.java.getResource("jasypt.fxml"))
        val screenRectangle = Screen.getPrimary().bounds
        val scene = Scene(fxmlLoader.load(), screenRectangle.width * 0.75, screenRectangle.height * 0.5)
//        stage.isResizable = false
        stage.title = "Jasypt 加密/解密工具"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Thread.setDefaultUncaughtExceptionHandler { _: Thread, _: Throwable ->
        @Override
        fun uncaughtException(t: Thread, e: Throwable) {
            val log = LogManager.getLogger(JasyptCoderToolApplication::class.java)
            // 抛出栈信息
            log.error("Thread:{},Throwable:{}", t, e)
        }
    }
    InitH2Table().createTable()
    Application.launch(JasyptCoderToolApplication::class.java)
}
