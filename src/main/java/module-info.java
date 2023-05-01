module com.groupthree.mancala {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;
    requires java.desktop;

    opens com.groupthree.mancala to javafx.fxml;
    exports com.groupthree.mancala;
    exports com.groupthree.mancala.models;
    exports com.groupthree.mancala.gameplay;
    opens com.groupthree.mancala.models to com.fasterxml.jackson.databind;
    opens com.groupthree.mancala.models.serializers to com.fasterxml.jackson.databind;
    opens com.groupthree.mancala.models.deserializers to com.fasterxml.jackson.databind;
}