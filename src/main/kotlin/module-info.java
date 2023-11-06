module cn.com.mooyea.jasypt {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires jasypt;
    requires java.xml.crypto;
    requires org.apache.logging.log4j;
    requires java.sql;

    opens cn.com.mooyea.jasypt to javafx.fxml;
    opens cn.com.mooyea.jasypt.controller to javafx.fxml;
    opens cn.com.mooyea.jasypt.handler to javafx.fxml;
    opens cn.com.mooyea.jasypt.annotations to javafx.fxml;
    opens cn.com.mooyea.jasypt.entity to javafx.base;
    exports cn.com.mooyea.jasypt;
}
