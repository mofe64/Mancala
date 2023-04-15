module com.groupthree.mancala {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;
    requires com.google.gson;

    opens com.groupthree.mancala to javafx.fxml;
    exports com.groupthree.mancala;
    opens com.groupthree.mancala.models to com.fasterxml.jackson.databind;
    opens com.groupthree.mancala.models.serializers to com.fasterxml.jackson.databind;
    opens com.groupthree.mancala.models.deserializers to com.fasterxml.jackson.databind;
}