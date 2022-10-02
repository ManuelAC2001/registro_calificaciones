package control_calificaciones.controllers;

import java.io.IOException;

import control_calificaciones.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}