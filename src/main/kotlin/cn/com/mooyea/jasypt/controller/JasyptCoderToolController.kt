package cn.com.mooyea.jasypt.controller

import javafx.fxml.FXML
import javafx.scene.control.Label
import lombok.extern.slf4j.Slf4j

@Slf4j
class JasyptCoderToolController {
    @FXML
    private lateinit var welcomeText: Label

    @FXML
    private fun onHelloButtonClick() {
        welcomeText.text = "Welcome to JavaFX Application!"
    }
}
