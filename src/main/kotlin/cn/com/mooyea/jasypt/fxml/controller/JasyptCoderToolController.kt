package cn.com.mooyea.jasypt.fxml.controller

import cn.com.mooyea.jasypt.JasyptApplication
import cn.com.mooyea.jasypt.annotations.Slf4k
import cn.com.mooyea.jasypt.annotations.Slf4k.Companion.log
import cn.com.mooyea.jasypt.common.RandomChar
import cn.com.mooyea.jasypt.fxml.service.IJasyptRecordService
import cn.com.mooyea.jasypt.fxml.ui.JasyptRecordUI
import cn.com.mooyea.jasypt.handler.JasyptHandler
import de.felixroske.jfxsupport.FXMLController
import javafx.event.Event
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TextArea
import javafx.scene.control.TextField
import javafx.stage.Modality
import org.jasypt.exceptions.EncryptionOperationNotPossibleException
import javax.annotation.Resource


@Slf4k
@FXMLController
class JasyptCoderToolController {

    @Resource
    private lateinit var jasyptRecordService: IJasyptRecordService
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

    @FXML
    fun onGenerateSalt(){
        // 生成盐值
        val length = RandomChar.random.nextInt(16)
        var saltStr = ""
        for (i in 0 until length){
            val index = RandomChar.random.nextInt(RandomChar.randomChars.size)
            saltStr += RandomChar.randomChars[index]
        }
        log.info("生成的随机盐为:$saltStr，随机盐长度为:$length")
        // 将生成的随机盐写入salt输入框
        salt.text = saltStr
    }

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
        jasyptRecordService.saveRecord(clearStr, saltStr, algorithmStr, cipherStr)
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
        try {
            // 调用 JasyptHandler 对密文进行解密
            val clearStr = JasyptHandler.decode(cipherStr, saltStr, algorithmStr)
            // 将加密后的密文设置到密文输入框中
            this.clearText.text = clearStr
            jasyptRecordService.saveRecord(clearStr, saltStr, algorithmStr, cipherStr)
        }catch (e: EncryptionOperationNotPossibleException){
            log.error("解密失败", e)
            Alert(Alert.AlertType.ERROR, "解密失败").showAndWait()
        }

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
    fun onRecordButtonClick(event: Event) {
        JasyptApplication.showUI(JasyptRecordUI::class.java, Modality.WINDOW_MODAL)
        JasyptRecordController().loadRecord()
    }


    /**
     * 关闭按钮点击事件
     */
    @FXML
    fun onCloseButtonClick() {
        // 获取当前窗口
        val stage = clearText.scene.window
        //TODO 打开遮罩,处理错误数据

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
}
