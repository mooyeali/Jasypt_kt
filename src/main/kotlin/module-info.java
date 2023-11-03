module cn.com.mooyea.jasypt {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;

    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens cn.com.mooyea.jasypt to javafx.fxml;
    exports cn.com.mooyea.jasypt;
}