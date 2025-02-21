package main.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MainController {

    @FXML
    private void handleStartGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Game Started!", ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
