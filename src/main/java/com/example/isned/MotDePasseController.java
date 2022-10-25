package com.example.isned;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MotDePasseController {

    @FXML
    private Button buttonSortir;

    @FXML
    void sortir(ActionEvent event) {
        Platform.exit();
    }
}
