module com.groupthree.mancala {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens com.groupthree.mancala to javafx.fxml;
    exports com.groupthree.mancala;
    exports com.groupthree.mancala.controllers;
    opens com.groupthree.mancala.controllers to javafx.fxml;
}