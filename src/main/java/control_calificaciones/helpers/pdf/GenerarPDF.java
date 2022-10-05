package control_calificaciones.helpers.pdf;


import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import control_calificaciones.data.Conexion;

public class GenerarPDF {
    
    public static void generarPDF() {

        Document documento = new Document();

        try {
            String ruta = new File(".").getCanonicalPath();
            String NOMBRE_ARCHIVO = ruta + "/bitacora-pdf.pdf";
            PdfWriter.getInstance(documento, new FileOutputStream(new File(NOMBRE_ARCHIVO)));
            //PdfWriter.getInstance(documento, new FileOutputStream("D:/TEC/7mo Semestre/Gestion Proyectos/registro_calificaciones/pdfs")); // Al final de la ruta concatenar el nombre del archivo PDF
            
            documento.open();

            /*
             * Paragraph paragraphHello = new Paragraph();
             * paragraphHello.add("Hello iText paragraph!");
             * paragraphHello.setAlignment(Element.ALIGN_JUSTIFIED);
             * 
             * document.add(paragraphHello);
             * 
             * Font f = new Font();
             * f.setFamily(FontFamily.COURIER.name());
             * f.setStyle(Font.BOLDITALIC);
             * f.setSize(8);
             */

            Font font = new Font();
            font.setFamily(FontFamily.HELVETICA.name());
            font.setStyle(Font.BOLD);
            font.setSize(10);

            Paragraph nombreInstituto = new Paragraph();
            nombreInstituto.add("Instituto Hispanoamericano Mexicano");
            nombreInstituto.setAlignment(Element.ALIGN_CENTER);
            documento.add(nombreInstituto);

            // Creamos la tabla (Bitacora)
            PdfPTable tablaBitacora = new PdfPTable(4);
            tablaBitacora.setSpacingBefore(12);
            // Le damos titulo a cada columna
            tablaBitacora.addCell("Usuario");
            tablaBitacora.addCell("Fecha que ingreso al sistema");
            tablaBitacora.addCell("Hora de entrada al sistema");
            tablaBitacora.addCell("Hora de salida del sistema");

            // Conexión a la base de datos
            Connection cn = null;
            
            try {
                cn = Conexion.getConnection();
                
                PreparedStatement select = cn.prepareStatement("select * from usuarios");
                ResultSet resultado = select.executeQuery();

                if( resultado.next() ) {
                    do {
                        tablaBitacora.addCell(resultado.getString(1));
                        tablaBitacora.addCell(resultado.getString(2));
                        tablaBitacora.addCell(resultado.getString(3));
                        tablaBitacora.addCell(resultado.getString(4));
                    } while (resultado.next());
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
            
            tablaBitacora.addCell("Director");
            tablaBitacora.addCell("05/Octubre/2022");
            tablaBitacora.addCell("4:00 p.m");
            tablaBitacora.addCell("4:30 p.m");
            
            // Añadimos la tabla dentro del documento PDF
            documento.add(tablaBitacora);

            documento.close();
            System.out.println("SE creo PDF!!!");
        } catch ( Exception e ) {
            System.out.println("Error al generar PDF" + e);
        }
    }
}
