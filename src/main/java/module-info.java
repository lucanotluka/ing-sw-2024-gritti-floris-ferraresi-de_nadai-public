module it.polimi.ingsw {
    // Requires: Dipendenze necessarie
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires java.rmi;
    requires java.desktop;

    // Exports: Pacchetti esportati

    //exports it.polimi.ingsw.view.gui;

    exports it.polimi.ingsw.view;
    exports it.polimi.ingsw.listener;
    exports it.polimi.ingsw.view.gui.controllers;
    exports it.polimi.ingsw.client to java.rmi;
    exports it.polimi.ingsw.view.gui to javafx.graphics;
    exports it.polimi.ingsw.server to java.rmi;

    // Opens: Pacchetti aperti a specifici moduli
    opens it.polimi.ingsw.view.gui.controllers to javafx.fxml, javafx.graphics;
    opens it.polimi.ingsw.model to json.simple;

}
