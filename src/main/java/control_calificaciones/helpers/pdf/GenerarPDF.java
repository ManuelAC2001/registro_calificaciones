package control_calificaciones.helpers.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import control_calificaciones.data.Conexion;
import control_calificaciones.helpers.Helpers;
import javafx.scene.paint.Color;

public class GenerarPDF {

    public static void generarPDF() {

        Document documento = new Document();

        try {
            String ruta = new File("./pdfs").getAbsolutePath();
            String NOMBRE_ARCHIVO = ruta + "/bitacora-pdf-" + Helpers.obtenerFechaActual() + ".pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(new File(NOMBRE_ARCHIVO)));

            documento.open();

            try {
                Image logo = Image.getInstance("./logo.png");
                logo.setAlignment(Image.ALIGN_LEFT);
                logo.scalePercent(5f);
                documento.add(logo);
            } catch (Exception e) {
                e.getMessage();
            }
            
            Paragraph nombreInstituto = new Paragraph("Instituto Hispanoamericano Mexicano",
                FontFactory.getFont("Arial", 30, Font.BOLD, BaseColor.BLUE));
            nombreInstituto.setAlignment(Element.ALIGN_CENTER);
            
            String diaActual =  Integer.toString(LocalDateTime.now().getDayOfMonth());
            String mesActual = LocalDateTime.now().getMonth().toString();
            String anioActual = Integer.toString(LocalDateTime.now().getYear());
            String fechaActual = diaActual + " " + mesActual + " " + anioActual;

            Paragraph subtitulo = new Paragraph("Bitacora de Inicio de Sesión de Usuarios del Día " + fechaActual,
            FontFactory.getFont("Arial", 20, Font.BOLD));
            subtitulo.setSpacingBefore(10);
            subtitulo.setAlignment(Element.ALIGN_CENTER);

            documento.add(nombreInstituto);
            documento.add(subtitulo);

            // Creamos la tabla (Bitacora)
            PdfPTable tablaBitacora = new PdfPTable(6);
            tablaBitacora.setSpacingBefore(30);
            // Le damos titulo a cada columna
            tablaBitacora.addCell( new Paragraph("Usuario", FontFactory.getFont("Arial", 10, Font.BOLD)));
            tablaBitacora.addCell(new Paragraph("Día", FontFactory.getFont("Arial", 10, Font.BOLD)));
            tablaBitacora.addCell(new Paragraph("Año", FontFactory.getFont("Arial", 10, Font.BOLD)));
            tablaBitacora.addCell(new Paragraph("Mes", FontFactory.getFont("Arial", 10, Font.BOLD)));
            tablaBitacora.addCell(new Paragraph("Hora de Entrada", FontFactory.getFont("Arial", 10, Font.BOLD)));
            tablaBitacora.addCell(new Paragraph("Hora de Salida", FontFactory.getFont("Arial", 10, Font.BOLD)));

            // Conexión a la base de datos
            String procedureCall = "{CALL getbitacoraSesionUsuarios()}";
            CallableStatement cstmt = null;
            Connection cn = null;
            ResultSet rs = null;
            
            try {

                cn = Conexion.getConnection();
                cstmt = cn.prepareCall(procedureCall);

                // cstmt.setInt(1, LocalDate.now().getDayOfMonth());
                // cstmt.setString(2, LocalDate.now().getMonth().toString());
                // cstmt.setInt(3, LocalDate.now().getYear());


                rs = cstmt.executeQuery();

                if (rs.next()) {
                    do {
                        tablaBitacora.addCell(rs.getString(2));
                        tablaBitacora.addCell(rs.getString(3));
                        tablaBitacora.addCell(rs.getString(4));
                        tablaBitacora.addCell(rs.getString(5));
                        tablaBitacora.addCell(rs.getString(6));
                        tablaBitacora.addCell(rs.getString(7));
                    } while (rs.next());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } 
            finally {
                Conexion.close(rs);
                Conexion.close(cstmt);
                Conexion.close(cn);
            }

            boolean b = true;
            for (PdfPRow r : tablaBitacora.getRows()) {
                for (PdfPCell c : r.getCells()) {
                    c.setBackgroundColor(b ? BaseColor.LIGHT_GRAY : BaseColor.WHITE);
                }
                b = !b;
            }

            // Añadimos la tabla dentro del documento PDF
            documento.add(tablaBitacora);

            documento.close();
        } catch (Exception e) {
            System.out.println("Error al generar PDF" + e);
        }
    } 

/*     public static void htmlToPDF () throws FileNotFoundException {
        InputStream inSt = new FileInputStream("./bitacora.html");

        FileOutputStream outSt = new FileOutputStream("../pdf/demo.pdf");
        // Document es la clase más utilizada en com.itextpdf.text inyectada por maven,
        // que representa una instancia de pdf.
        // Si desea generar un documento PDF, debe llamar al método open () y al método
        // close () de la clase Document.
        Document document = new Document();
        // PdfWriter es inyectado por maven
        // La clase bajo com.itextpdf.text.pdf, donde el método estático getInstance ()
        // se usa para asociar el objeto del documento con el objeto outputStream.
        PdfWriter writer = PdfWriter.getInstance(document, outSt);

        document.open();

        // XMLWorkerHelper es una clase bajo com.itextpdf.tool.xml inyectada por maven,
        // cuya función principal es convertir html a pdf
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, inSt, Charset.forName("UTF-8"));

        document.close();  
    } */
}
