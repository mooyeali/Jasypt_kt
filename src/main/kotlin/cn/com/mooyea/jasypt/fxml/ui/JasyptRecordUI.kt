package cn.com.mooyea.jasypt.fxml.ui

import cn.com.mooyea.jasypt.fxml.controller.JasyptRecordController
import de.felixroske.jfxsupport.AbstractFxmlView
import de.felixroske.jfxsupport.FXMLView
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Screen
import javafx.stage.Stage
import org.springframework.context.ApplicationContext

import org.springframework.context.annotation.Lazy
import javax.annotation.Resource

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
@FXMLView(value = "/fxml/record.fxml",encoding = "UTF-8", title = "历史记录")
class JasyptRecordUI : AbstractFxmlView()
