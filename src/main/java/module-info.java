module control_calificaciones {
    
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;
    requires java.dotenv;
    requires itextpdf;
    requires openhtmltopdf.core;
    requires openhtmltopdf.java2d;
    requires openhtmltopdf.mathml.support;
    requires openhtmltopdf.pdfbox;
    requires openhtmltopdf.rtl.support;
    requires openhtmltopdf.slf4j;
    requires openhtmltopdf.svg.support;

    opens control_calificaciones.controllers to javafx.fxml;
    opens control_calificaciones.models to javafx.base;
    
    exports control_calificaciones;
}
