package control_calificaciones.helpers.pdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import org.jsoup.Jsoup;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import control_calificaciones.models.Alumno;

public class ListaPDF {

    public static void listaPDF(File file, ArrayList<Alumno> alumnos) {
        String NOMBRE_ARCHIVO = file.toString() + ".pdf";
        Document documento = new Document();

        try {
            PdfWriter.getInstance(documento, new FileOutputStream(new File(NOMBRE_ARCHIVO)));
            documento.open();

            try {
                String nombreLogo = "logo.png";
                String rutaLogo = new File(nombreLogo).getAbsolutePath();
                Image logo = Image.getInstance(rutaLogo);
                logo.setAlignment(Image.ALIGN_CENTER);
                logo.scalePercent(5f);
                documento.add(logo);
            } catch (Exception e) {
                e.getMessage();
            }

            Paragraph nombreInstituto = new Paragraph("INSTITUTO HISPANOAMERICANO-MEXICANO",
                    FontFactory.getFont("Arial", 15, Font.BOLD, BaseColor.BLUE));
            nombreInstituto.setAlignment(Element.ALIGN_CENTER);

            Paragraph eslogan = new Paragraph(
                    "PRESTIGIO Y CALIDAD ACADEMICA PARA TU DESARROLLO VICENTE GUERREO #49, CENTRO",
                    FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.BLACK));
            eslogan.setAlignment(Element.ALIGN_CENTER);

            Paragraph gradoText = new Paragraph("GRADO : " + alumnos.get(0).getNombre_grado(),
                    FontFactory.getFont("Arial", 8, Font.BOLD, BaseColor.BLACK));
            gradoText.setAlignment(Element.ALIGN_CENTER);

            Paragraph grupoText = new Paragraph("GRUPO : " + alumnos.get(0).getNombre_grupo(),
                    FontFactory.getFont("Arial", 8, Font.BOLD, BaseColor.BLACK));
            grupoText.setAlignment(Element.ALIGN_CENTER);

            documento.add(nombreInstituto);
            documento.add(eslogan);
            documento.add(gradoText);
            documento.add(grupoText);

            PdfPTable tabla = new PdfPTable(4);
            tabla.setSpacingBefore(20);
            tabla.setWidthPercentage(80);
            tabla.setWidths(new float[] { 2, 1, 4, 14 });

            PdfPTable tablaMaterias = new PdfPTable(10);
            PdfPTable tablaCalificaciones = new PdfPTable(10);

            // llamar a travez de la BD el nombre de las materias
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

            // llamar las califaciones desde la BD
            tablaCalificaciones.addCell(" ");
            tablaCalificaciones.addCell(" ");
            tablaCalificaciones.addCell(" ");
            tablaCalificaciones.addCell(" ");
            tablaCalificaciones.addCell(" ");
            tablaCalificaciones.addCell(" ");
            tablaCalificaciones.addCell(" ");
            tablaCalificaciones.addCell(" ");
            tablaCalificaciones.addCell(" ");
            tablaCalificaciones.addCell(" ");

            tabla.addCell(" ");
            tabla.addCell(" ");
            tabla.addCell(" ");
            tabla.addCell(new Paragraph("Materias", FontFactory.getFont("Arial", 5, Font.BOLD)));

            tabla.addCell(new Paragraph("CURP", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tabla.addCell(new Paragraph("SEXO", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tabla.addCell(new Paragraph("NOMBRE DEL ALUMNO(A)", FontFactory.getFont("Arial", 5, Font.BOLD)));
            tabla.addCell(tablaMaterias);

            // registros de los alumnos desde la BD

            alumnos.forEach(alumno -> {
                tabla.addCell(new Paragraph(alumno.getCurp(), FontFactory.getFont("Arial", 5, Font.BOLD)));
                tabla.addCell(new Paragraph(alumno.getGenero().toString(), FontFactory.getFont("Arial", 5, Font.BOLD)));
                tabla.addCell(new Paragraph(alumno.getNombreCompleto(), FontFactory.getFont("Arial", 5, Font.BOLD)));
                tabla.addCell(tablaCalificaciones);
            });

            documento.add(tabla);

            documento.close();
        } catch (Exception e) {
            System.out.println("Error al generar la lista" + e);
        }
    }

    public static void listaHTMLPDF(File file) {

        final String RUTA_LISTA_PDF = file.toString() + ".pdf";

        String filePath = System.getProperty("user.dir");
        File templateHTML = new File(filePath + "\\index.html");
        File listaHTML = new File(filePath + "\\listas.html");
        File listaPDF = new File(RUTA_LISTA_PDF);

        try {
            org.jsoup.nodes.Document documentHTML;
            documentHTML = Jsoup.parse(templateHTML);
            documentHTML.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);

            // obtencion del contenido de la plantilla HTML
            String contenidoHTML = documentHTML.html();

            // Escribiendo en el archivo lista HTML
            BufferedWriter bfr = new BufferedWriter(new FileWriter(listaHTML));
            bfr.write(contenidoHTML);
            bfr.close();

            // Convirtiendo en PDF el archivo lista.html
            OutputStream os = new FileOutputStream(listaPDF);
            PdfRendererBuilder pdfBuilder = new PdfRendererBuilder();
            pdfBuilder.useFastMode();
            pdfBuilder.withFile(listaHTML);
            pdfBuilder.toStream(os);
            pdfBuilder.run();
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
