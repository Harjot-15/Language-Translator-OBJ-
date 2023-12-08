module com.example.languagetranslator {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.web;
    requires java.net.http;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.json;

    opens com.example.languagetranslator to javafx.fxml;
    exports com.example.languagetranslator;
    exports com.example.languagetranslator.model;
    opens com.example.languagetranslator.model to javafx.fxml;
    opens com.example.languagetranslator.controller to javafx.fxml;
}

