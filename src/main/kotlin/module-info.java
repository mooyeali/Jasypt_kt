module cn.com.mooyea.jasypt {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires lombok;
    requires jasypt;

    opens cn.com.mooyea.jasypt to javafx.fxml;
    opens cn.com.mooyea.jasypt.controller to javafx.fxml;
    exports cn.com.mooyea.jasypt;
}
