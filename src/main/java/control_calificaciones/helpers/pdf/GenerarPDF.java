package control_calificaciones.helpers.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDateTime;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPRow;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import control_calificaciones.data.Conexion;

public class GenerarPDF {

    public static void generarPDF(File file) {

        String NOMBRE_ARCHIVO = file.toString() + ".pdf";
        // String ruta = "";

        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(NOMBRE_ARCHIVO));
            documento.open();

            try {
                Image logo = Image.getInstance("./logo.png");
                logo.setAlignment(Image.ALIGN_CENTER);
                logo.scalePercent(5f);
                documento.add(logo);
            } catch (Exception e) {
                e.getMessage();
            }

            PdfWriter.getInstance(documento, new FileOutputStream(new File(NOMBRE_ARCHIVO)));

            documento.open();

            Paragraph nombreInstituto = new Paragraph("Instituto Hispanoamericano Mexicano",
            FontFactory.getFont("Arial", 30, Font.BOLD, BaseColor.BLUE));
            nombreInstituto.setAlignment(Element.ALIGN_CENTER);
            nombreInstituto.setAlignment(Element.ALIGN_CENTER);

            String diaActual = Integer.toString(LocalDateTime.now().getDayOfMonth());
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
            tablaBitacora.addCell(new Paragraph("Usuario", FontFactory.getFont("Arial", 10, Font.BOLD)));
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
            } finally {
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
}
