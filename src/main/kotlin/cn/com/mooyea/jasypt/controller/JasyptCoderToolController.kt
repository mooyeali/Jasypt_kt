package cn.com.mooyea.jasypt.controller

import cn.com.mooyea.jasypt.annotations.Slf4k
import cn.com.mooyea.jasypt.annotations.Slf4k.Companion.log
import cn.com.mooyea.jasypt.handler.JasyptHandler
import cn.com.mooyea.jasypt.JasyptRecordUI
import cn.com.mooyea.jasypt.utils.H2JDBCTemplate
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.stage.Stage


@Slf4k
class JasyptCoderToolController {
    /**
     * 明文输入框
     */
    @FXML
    private lateinit var clearText: TextArea

    /**
     * 密文输入框
     */
    @FXML
    private lateinit var ciphertext: TextArea

    /**
     * 加密算法选择框
     */
    @FXML
    private lateinit var algorithm: ChoiceBox<String>

    /**
     * 盐值输入框
     */
    @FXML
    private lateinit var salt: TextField

    private val insertSql = "INSERT INTO RECORDS (cleartext, salt, algorithm, encrypt) VALUES (?, ?, ?, ?)"


    /**
     * 加密按钮点击事件
     *
     */
    @FXML
    fun onEncodeButtonClick() {
        // 获取加密算法
        val algorithmStr = algorithm.value
        // 获取盐值
        val saltStr = salt.text
        // 获取明文
        val clearStr = clearText.text
        log.info("加密盐值:{},加密算法:{},明文:{}", saltStr, algorithmStr, clearStr)
        errorAlter(saltStr, algorithmStr)
        // 调用 JasyptHandler 对明文进行加密
        val cipherStr = JasyptHandler.encode(clearStr, saltStr, algorithmStr)
        // 将加密后的密文设置到密文输入框中
        this.ciphertext.text = cipherStr
        // 将加密记录插入到数据库中
        saveRecord(clearStr, saltStr, algorithmStr, cipherStr)
    }

    /**
     * 解密按钮点击事件
     */
    @FXML
    fun onDecodeButtonClick() {
        // 获取加密算法
        val algorithmStr = algorithm.value
        // 获取盐值
        val saltStr = salt.text
        // 获取密文
        val cipherStr = ciphertext.text
        log.info("加密盐值:{},加密算法:{},密文:{}", saltStr, algorithmStr, cipherStr)
        errorAlter(saltStr, algorithmStr)
        // 调用 JasyptHandler 对密文进行解密
        val clearStr = JasyptHandler.decode(cipherStr, saltStr, algorithmStr)
        // 将加密后的密文设置到密文输入框中
        this.clearText.text = clearStr
        saveRecord(clearStr, saltStr, algorithmStr, cipherStr)
    }

    /**
     * 重置按钮点击事件
     */
    @FXML
    fun onResetButtonClick() {
        clearText.text = ""
        ciphertext.text = ""
        algorithm.value = null
        salt.text = ""
    }
    /**
     * 记录按钮点击事件
     */
    @FXML
    fun onRecordButtonClick() {
        // 查看加密记录
        val recordUI = JasyptRecordUI()
        recordUI.showWindow(Stage())
    }


    /**
     * 关闭按钮点击事件
     */
    @FXML
    fun onCloseButtonClick() {
        // 获取当前窗口
        val stage = clearText.scene.window
        // 关闭窗口
        stage.hide()
    }

    /**
     * algorithm下拉框选择事件
     */
    @FXML
    fun onAlgorithmChoiceBox() {
        println(algorithm.value)
    }

    /**
     * 错误弹窗提示
     *
     * @param saltStr 加密盐值
     * @param algorithmStr 加密算法
     */
    private fun errorAlter(saltStr: String?, algorithmStr: String?) {
        val alert = Alert(Alert.AlertType.ERROR)
        alert.title = "错误"
        if (JasyptHandler.verifyKeyParameters(saltStr, algorithmStr)) {
            log.error("加密盐值或加密算法为空")
            // 弹窗提示
            alert.headerText = "关键参数为空"
            alert.contentText = "加密盐值或加密算法为空"
            alert.showAndWait()
            return
        }
        // 判断salt是否包含中文
        if (JasyptHandler.isContainChinese(saltStr)) {
            log.error("加密盐值包含中文")
            // 弹窗提示
            alert.headerText = "错误的数据"
            alert.contentText = "加密盐值包含中文"
            alert.showAndWait()
            return
        }
    }

    private fun saveRecord(clearStr: String, saltStr: String, algorithmStr: String, cipherStr: String) {
        val params = HashMap<Int, String>()
        params[1] = clearStr
        params[2] = saltStr
        params[3] = algorithmStr
        params[4] = cipherStr
        if(H2JDBCTemplate.insert(insertSql, params)>0) {
            log.info("插入成功")
        }else {
            log.warn("插入失败,数据记录为:{}",params)
        }
    }
}
