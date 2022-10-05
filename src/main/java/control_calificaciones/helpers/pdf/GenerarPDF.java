package control_calificaciones.helpers.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.time.LocalDate;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import control_calificaciones.data.Conexion;
import control_calificaciones.helpers.Helpers;

public class GenerarPDF {

    public static void generarPDF() {

        Document documento = new Document();

        try {
            String ruta = new File("./pdfs").getAbsolutePath();
            String NOMBRE_ARCHIVO = ruta + "/bitacora-pdf-" + Helpers.obtenerFechaActual() + ".pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(new File(NOMBRE_ARCHIVO)));

            documento.open();

            Font font = new Font();
            font.setFamily(FontFamily.HELVETICA.name());
            font.setStyle(Font.BOLD);
            font.setSize(10);

            Paragraph nombreInstituto = new Paragraph();
            nombreInstituto.add("Instituto Hispanoamericano Mexicano");
            nombreInstituto.setAlignment(Element.ALIGN_CENTER);
            documento.add(nombreInstituto);

            // Creamos la tabla (Bitacora)
            PdfPTable tablaBitacora = new PdfPTable(6);
            tablaBitacora.setSpacingBefore(12);
            // Le damos titulo a cada columna
            tablaBitacora.addCell("Usuario");
            tablaBitacora.addCell("Dia");
            tablaBitacora.addCell("año");
            tablaBitacora.addCell("mes");
            tablaBitacora.addCell("hora de entrada");
            tablaBitacora.addCell("hora de salida");

            // Conexión a la base de datos
            String procedureCall = "{CALL getbitacoraSesionUsuariosActual(?,?,?)}";
            CallableStatement cstmt = null;
            Connection cn = null;
            ResultSet rs = null;
            
            try {

                cn = Conexion.getConnection();
                cstmt = cn.prepareCall(procedureCall);

                cstmt.setInt(1, LocalDate.now().getDayOfMonth());
                cstmt.setString(2, LocalDate.now().getMonth().toString());
                cstmt.setInt(3, LocalDate.now().getYear());

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

            // Añadimos la tabla dentro del documento PDF
            documento.add(tablaBitacora);

            documento.close();
        } catch (Exception e) {
            System.out.println("Error al generar PDF" + e);
        }
    }
}
