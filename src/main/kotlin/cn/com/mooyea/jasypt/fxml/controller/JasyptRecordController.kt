package cn.com.mooyea.jasypt.fxml.controller

import cn.com.mooyea.jasypt.annotations.Slf4k.Companion.log
import cn.com.mooyea.jasypt.fxml.entity.JasyptRecordEntity
import cn.com.mooyea.jasypt.fxml.service.IJasyptRecordService
import de.felixroske.jfxsupport.FXMLController
import javafx.beans.InvalidationListener
import javafx.collections.FXCollections
import javafx.collections.ListChangeListener
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import javafx.scene.control.cell.TextFieldTableCell
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import java.net.URL
import java.util.*
import javax.annotation.Resource

/**
 * <h1>JasyptRecordController<h1>
 *
 * Copyright (C), 星期一,06,11月,2023
 * <br></br>
 * <hr></hr>
 * <h3>File Info:</h3>
 *
 * FileName: JasyptRecordController
 *
 * Author:   mooye
 *
 * Work_Email： lidy@skyvis.com.cn
 *
 * E-mail： mooyeali@yeah.net
 *
 * Date:     2023/11/6
 *
 * Description: 加密历史记录控制器层
 * <hr></hr>
 * <h3>History:</h3>
 * <hr></hr>
 * <table>
 * <thead>
 * <tr><td style='width:100px;' center>Author</td><td style='width:200px;' center>Time</td><td style='width:100px;' center>Version_Number</td><td style='width:100px;' center>Description</td></tr>
</thead> *
 * <tbody>
 * <tr><td style='width:100px;' center>mooye</td><td style='width:200px;' center>09:48 2023/11/6</td><td style='width:100px;' center>v_1.0.0</td><td style='width:100px;' center>创建</td></tr>
</tbody> *
</table> *
 * <hr></hr>
 * <br></br>
 *
 * @author mooye
</h1></h1> */
@FXMLController
class JasyptRecordController: Initializable {

    @Resource
    lateinit var service: IJasyptRecordService
    @FXML
    private lateinit var saltText: TextField

    @FXML
    private lateinit var clearText: TextField

    @FXML
    private lateinit var algorithmChoice: ChoiceBox<String>

    @FXML
    lateinit var recordTable: TableView<JasyptRecordEntity>

    @FXML
    lateinit var clearTextColumn: TableColumn<JasyptRecordEntity, String>

    @FXML
    lateinit var saltColumn: TableColumn<JasyptRecordEntity, String>

    @FXML
    lateinit var algorithmColumn: TableColumn<JasyptRecordEntity, String>

    @FXML
    lateinit var ciphertextColumn: TableColumn<JasyptRecordEntity, String>

    /**
     * 查询按钮点击事件
     *
     */
    @FXML
    private fun query() {
        log.info("查询")
        if(this::service.isInitialized) {
            log.info("初始化完成")
            log.info("{},{},{}",clearText.text,
                saltText.text,
                algorithmChoice.value)
            renderingData(
                FXCollections.observableArrayList(
                    service.queryRecord(
                        clearText.text,
                        saltText.text,
                        algorithmChoice.value
                    )
                )
            )
        }
    }

    /**
     * 重置按钮点击事件
     *
     */
    @FXML
    private fun reset() {
        saltText.text = ""
        clearText.text = ""
        algorithmChoice.value = ""
        loadRecord()
    }


    fun loadRecord() {
        if (this::service.isInitialized) {
            renderingData(FXCollections.observableList(service.list()))
            recordTable.selectionModel.isCellSelectionEnabled = true
        }
    }

    private fun renderingData(data: ObservableList<JasyptRecordEntity>){
        if (this::clearTextColumn.isInitialized) {
            clearTextColumn.cellValueFactory = PropertyValueFactory("cleartext")
            copyToClipboard(clearTextColumn)
        }
        if (this::saltColumn.isInitialized) {
            saltColumn.cellValueFactory = PropertyValueFactory("salt")
            copyToClipboard(saltColumn)
        }
        if (this::algorithmColumn.isInitialized) {
            algorithmColumn.cellValueFactory = PropertyValueFactory("algorithm")
        }
        if (this::ciphertextColumn.isInitialized) {
            ciphertextColumn.cellValueFactory = PropertyValueFactory("encrypt")
            copyToClipboard(ciphertextColumn)
        }
        if (this::recordTable.isInitialized) {
            recordTable.items = data
        }
    }

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        log.info("初始化")
        loadRecord()
    }

    fun copyToClipboard(column: TableColumn<JasyptRecordEntity, String>){
        column.setCellFactory {
            val cell = TextFieldTableCell<JasyptRecordEntity, String>()
            cell.setOnMouseClicked { event ->
                if (event.clickCount == 2) {
                    val text = cell.text
                    log.info("双击了第${cell.index}行,值为:$text")
                    if (text.isNotEmpty()){
                        // 写入剪切板
                        val clipboardContent = ClipboardContent()
                        clipboardContent.putString(text)
                        Clipboard.getSystemClipboard().setContent(clipboardContent)
                        // 弹出提示框
                        Alert(Alert.AlertType.INFORMATION, "复制成功").showAndWait()
                    }
                }
            }
            return@setCellFactory cell
        }
    }
}
