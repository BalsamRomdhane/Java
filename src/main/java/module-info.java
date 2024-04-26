module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    exports com.example.demo;
    exports Controllers;
    opens Controllers to javafx.fxml; // Ouvrir le package Controllers pour le module javafx.fxml
    opens com.example.demo to javafx.fxml;
    opens Models; // Open the Models package to allow reflective access
}
