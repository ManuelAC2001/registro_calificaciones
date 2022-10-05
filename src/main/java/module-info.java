module control_calificaciones {
    requires javafx.controls;
    requires javafx.fxml;
    
    requires java.sql;
    requires java.mail;
    requires java.dotenv;
    requires itextpdf;

    opens control_calificaciones.controllers to javafx.fxml;
    exports control_calificaciones;
}
