package cn.com.mooyea.jasypt.controller

import cn.com.mooyea.jasypt.annotations.Slf4k.Companion.log
import cn.com.mooyea.jasypt.entity.JasyptRecordEntity
import cn.com.mooyea.jasypt.utils.H2JDBCTemplate
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.fxml.FXML
import javafx.scene.control.ChoiceBox
import javafx.scene.control.TableColumn
import javafx.scene.control.TableView
import javafx.scene.control.TextField
import javafx.scene.control.cell.PropertyValueFactory

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
class JasyptRecordController {
    @FXML
    private lateinit var saltText: TextField

    @FXML
    private lateinit var clearText: TextField

    @FXML
    private lateinit var algorithmChoice: ChoiceBox<String>

    @FXML
    private lateinit var recordTable: TableView<JasyptRecordEntity>

    @FXML
    private lateinit var clearTextColumn: TableColumn<JasyptRecordEntity, String>

    @FXML
    private lateinit var saltColumn: TableColumn<JasyptRecordEntity, String>

    @FXML
    private lateinit var algorithmColumn: TableColumn<JasyptRecordEntity, String>

    @FXML
    private lateinit var ciphertextColumn: TableColumn<JasyptRecordEntity, String>

    /**
     * 查询按钮点击事件
     *
     */
    @FXML
    private fun query() {
        var query = "select cleartext,salt,algorithm,encrypt from records where 1=1"
        var i = 0
        val params = HashMap<Int, String>()
        val salt = saltText.text
        if (salt != null && salt.isNotEmpty()) {
            query = "$query and salt like ?"
            i+=1
            params[i] = "%${saltText.text}%"

        }
        val cleartext = clearText.text
        if (cleartext != null && cleartext.isNotEmpty()) {
            query= "$query and cleartext like ?"
            i+=1
            params[i] = "%${clearText.text}%"
        }
        val algorithm = algorithmChoice.value
        if (algorithm != null && algorithm.isNotEmpty()) {
            query= "$query and algorithm = ?"
            i+=1
            params[i] = algorithmChoice.value
        }
//        log.info("即将执行的 SQL:{}",query)
        // 加载表格数据
        val data = H2JDBCTemplate().select(query, params)
        renderingData(data)
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
        val query = "select cleartext,salt,algorithm,encrypt from records"
        // 加载表格数据
        val data = H2JDBCTemplate().select(query, mapOf())
        renderingData(data)
    }

    private fun renderingData(data: ObservableList<JasyptRecordEntity>){
        clearTextColumn.cellValueFactory = PropertyValueFactory("cleartext")
        saltColumn.cellValueFactory = PropertyValueFactory("salt")
        algorithmColumn.cellValueFactory = PropertyValueFactory("algorithm")
        ciphertextColumn.cellValueFactory = PropertyValueFactory("encrypt")
        recordTable.items = data
    }
}
