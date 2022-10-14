package control_calificaciones.helpers.pdf;

import java.io.File;
import java.io.FileOutputStream;

// import com.itextpdf.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

// import javafx.scene.control.Cell;

public class ListaPDF {

    public static void listaPDF(File file) {
        String NOMBRE_ARCHIVO = file.toString() + ".pdf";
        Document documento = new Document(PageSize.A4.rotate());

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(new File(NOMBRE_ARCHIVO)));
            documento.open();

            try {
                String nombreLogo = "logo.png";
                String rutaLogo = new File(nombreLogo).getAbsolutePath();
                Image logo = Image.getInstance(rutaLogo);
                logo.setAlignment(Image.ALIGN_LEFT);
                logo.scalePercent(5f);
            } catch (Exception e) {
                e.getMessage();
            }

            Paragraph nombreInstituto = new Paragraph("INSTITUTO HISPANOAMERICANO-MEXICANO",
                    FontFactory.getFont("Arial", 15, Font.BOLD, BaseColor.BLUE));
            nombreInstituto.setAlignment(Element.ALIGN_CENTER);

            Paragraph eslogan = new Paragraph(
                    "PRESTIGIO Y CALIDAD ACADEMICA PARA TU DESARROLLO \nVICENTE GUERREO #49, CENTRO",
                    FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.BLACK));
            eslogan.setAlignment(Element.ALIGN_CENTER);

            Paragraph materiasTitulo = new Paragraph(
                    "viva el americaa",
                    FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.BLACK));
            eslogan.setAlignment(Element.ALIGN_TOP);

            documento.add(nombreInstituto);
            documento.add(eslogan);
            

            PdfPTable tabla = new PdfPTable(4);
            tabla.setSpacingBefore(20);
            // tabla.setWidthPercentage(80);
            tabla.setWidths(new float[] { 2, 2, 5, 5 });

            PdfPTable tablaMaterias = new PdfPTable(10);
            
            tablaMaterias.addCell(new Paragraph("M1", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tablaMaterias.addCell(new Paragraph("M2", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tablaMaterias.addCell(new Paragraph("M3", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tablaMaterias.addCell(new Paragraph("M4", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tablaMaterias.addCell(new Paragraph("M5", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tablaMaterias.addCell(new Paragraph("M6", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tablaMaterias.addCell(new Paragraph("M7", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tablaMaterias.addCell(new Paragraph("M8", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tablaMaterias.addCell(new Paragraph("M9", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tablaMaterias.addCell(new Paragraph("M10", FontFactory.getFont("Arial", 5, Font.BOLD)));

            tabla.addCell(new Paragraph("No.", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tabla.addCell(new Paragraph("SEXO", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tabla.addCell(new Paragraph("NOMBRE DEL ALUMNO(A)", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tabla.addCell(tablaMaterias);


            // inicio de la conexion a la base de datos

            // finde la conexion a la base de datos

            documento.add(tabla);

            documento.close();
        } catch (Exception e) {
            System.out.println("Error al generar la lista" + e);
        }
    }
}
