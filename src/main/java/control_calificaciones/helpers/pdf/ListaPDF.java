package control_calificaciones.helpers.pdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

// import com.itextpdf.text.BaseColor;
// // import com.itextpdf.text.Document;
// // import com.itextpdf.text.Element;
// import com.itextpdf.text.Font;
// import com.itextpdf.text.FontFactory;
// import com.itextpdf.text.Image;
// import com.itextpdf.text.Paragraph;
// import com.itextpdf.text.pdf.PdfPTable;
// import com.itextpdf.text.pdf.PdfWriter;

import control_calificaciones.models.Alumno;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

// import control_calificaciones.models.Alumno;
import control_calificaciones.models.Asignatura;

public class ListaPDF {

    // public static void listaPDF(File file, ArrayList<Alumno> alumnos) {
    // String NOMBRE_ARCHIVO = file.toString() + ".pdf";
    // Document documento = new Document();

    // try {
    // PdfWriter.getInstance(documento, new FileOutputStream(new
    // File(NOMBRE_ARCHIVO)));
    // documento.open();

    // try {
    // String nombreLogo = "logo.png";
    // String rutaLogo = new File(nombreLogo).getAbsolutePath();
    // Image logo = Image.getInstance(rutaLogo);
    // logo.setAlignment(Image.ALIGN_CENTER);
    // logo.scalePercent(5f);
    // documento.add(logo);
    // } catch (Exception e) {
    // e.getMessage();
    // }

    // Paragraph nombreInstituto = new Paragraph("INSTITUTO
    // HISPANOAMERICANO-MEXICANO",
    // FontFactory.getFont("Arial", 15, Font.BOLD, BaseColor.BLUE));
    // nombreInstituto.setAlignment(Element.ALIGN_CENTER);

    // Paragraph eslogan = new Paragraph(
    // "PRESTIGIO Y CALIDAD ACADEMICA PARA TU DESARROLLO VICENTE GUERREO #49,
    // CENTRO",
    // FontFactory.getFont("Arial", 12, Font.BOLD, BaseColor.BLACK));
    // eslogan.setAlignment(Element.ALIGN_CENTER);

    // Paragraph gradoText = new Paragraph("GRADO : " +
    // alumnos.get(0).getNombre_grado(),
    // FontFactory.getFont("Arial", 8, Font.BOLD, BaseColor.BLACK));
    // gradoText.setAlignment(Element.ALIGN_CENTER);

    // Paragraph grupoText = new Paragraph("GRUPO : " +
    // alumnos.get(0).getNombre_grupo(),
    // FontFactory.getFont("Arial", 8, Font.BOLD, BaseColor.BLACK));
    // grupoText.setAlignment(Element.ALIGN_CENTER);

    // documento.add(nombreInstituto);
    // documento.add(eslogan);
    // documento.add(gradoText);
    // documento.add(grupoText);

    // PdfPTable tabla = new PdfPTable(4);
    // tabla.setSpacingBefore(20);
    // tabla.setWidthPercentage(80);
    // tabla.setWidths(new float[] { 2, 1, 4, 14 });

    // PdfPTable tablaMaterias = new PdfPTable(10);
    // PdfPTable tablaCalificaciones = new PdfPTable(10);

    // // llamar a travez de la BD el nombre de las materias
    // tablaMaterias.addCell(new Paragraph("M1", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tablaMaterias.addCell(new Paragraph("M2", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tablaMaterias.addCell(new Paragraph("M3", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tablaMaterias.addCell(new Paragraph("M4", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tablaMaterias.addCell(new Paragraph("M5", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tablaMaterias.addCell(new Paragraph("M6", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tablaMaterias.addCell(new Paragraph("M7", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tablaMaterias.addCell(new Paragraph("M8", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tablaMaterias.addCell(new Paragraph("M9", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tablaMaterias.addCell(new Paragraph("M10", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));

    // // llamar las califaciones desde la BD
    // tablaCalificaciones.addCell(" ");
    // tablaCalificaciones.addCell(" ");
    // tablaCalificaciones.addCell(" ");
    // tablaCalificaciones.addCell(" ");
    // tablaCalificaciones.addCell(" ");
    // tablaCalificaciones.addCell(" ");
    // tablaCalificaciones.addCell(" ");
    // tablaCalificaciones.addCell(" ");
    // tablaCalificaciones.addCell(" ");
    // tablaCalificaciones.addCell(" ");

    // tabla.addCell(" ");
    // tabla.addCell(" ");
    // tabla.addCell(" ");
    // tabla.addCell(new Paragraph("Materias", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));

    // tabla.addCell(new Paragraph("CURP", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tabla.addCell(new Paragraph("SEXO", FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tabla.addCell(new Paragraph("NOMBRE DEL ALUMNO(A)",
    // FontFactory.getFont("Arial", 5, Font.BOLD)));
    // tabla.addCell(tablaMaterias);

    // // registros de los alumnos desde la BD

    // alumnos.forEach(alumno -> {
    // tabla.addCell(new Paragraph(alumno.getCurp(), FontFactory.getFont("Arial", 5,
    // Font.BOLD)));
    // tabla.addCell(new Paragraph(alumno.getGenero().toString(),
    // FontFactory.getFont("Arial", 5, Font.BOLD)));
    // tabla.addCell(new Paragraph(alumno.getNombreCompleto(),
    // FontFactory.getFont("Arial", 5, Font.BOLD)));
    // tabla.addCell(tablaCalificaciones);
    // });

    // documento.add(tabla);

    // documento.close();
    // } catch (Exception e) {
    // System.out.println("Error al generar la lista" + e);
    // }
    // }

    public static void agregarAlumno(Document documentHTML, Alumno alumno) {
        Element alumnoRow = documentHTML.createElement("tr");
        documentHTML.getElementById("tbody__alumnos").appendChild(alumnoRow);
        alumnoRow.append("<td>" + " " + "</td>");
        alumnoRow.append("<td>" + alumno.getGenero() + "</td>");
        alumnoRow.append("<td>" + alumno.getNombreCompleto() + "</td>");

        Element asistenciaRow = documentHTML.createElement("tr");
        documentHTML.getElementById("tobody__asistencias").appendChild(asistenciaRow);
        asistenciaRow.append("<td> - </td>");
        asistenciaRow.append("<td> - </td>");
        asistenciaRow.append("<td> - </td>");
        asistenciaRow.append("<td> - </td>");
        asistenciaRow.append("<td> - </td>");
        asistenciaRow.append("<td> - </td>");
        asistenciaRow.append("<td> - </td>");

        Element inasistenciaRow = documentHTML.createElement("tr");
        documentHTML.getElementById("tobody__inasistencias").appendChild(inasistenciaRow);
        inasistenciaRow.append("<td> - </td>");
        inasistenciaRow.append("<td> - </td>");
        inasistenciaRow.append("<td> - </td>");
        inasistenciaRow.append("<td> - </td>");
        inasistenciaRow.append("<td> - </td>");
        inasistenciaRow.append("<td> - </td>");

        Element rasgosRow = documentHTML.createElement("tr");
        Element tr_promedio_materias = documentHTML.getElementById("tr_promedio_materias");
        documentHTML.getElementById("tobody__rasgos").appendChild(rasgosRow);
        documentHTML.getElementById("tobody__rasgos").after(tr_promedio_materias);

        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
    }

    public static void agregarAsignaturasAcademicas(Document documentHTML, ArrayList<Asignatura> asignaturas,
        String idTr) {

        asignaturas.forEach(asignatura -> {
            Element materiaHead = documentHTML.createElement("th");
            documentHTML.getElementById(idTr).appendChild(materiaHead);
            materiaHead.append("<th>" + asignatura.getNombre_materia() + "</th>");
        });
    }

    public static void agregarPromedioAcademico(Document documentHTML, ArrayList<Asignatura> asignaturas, String idTBody) {

        Element materiasRow = documentHTML.createElement("tr");
        documentHTML.getElementById(idTBody).appendChild(materiasRow);
        
        asignaturas.forEach(asignatura -> {
            materiasRow.append("<td> - </td>");
        });
    }
    
    public static void agregarInformacionExtra(Document documentHTML, ArrayList<Alumno> alumnos){

        String gradoInfo = alumnos.get(0).getNombre_grado();
        String grupoInfo = alumnos.get(0).getNombre_grupo();
        String mesInfo = LocalDate.now().getMonth().toString();
        String AnioInfo = String.valueOf(LocalDate.now().getYear());


        documentHTML.getElementById("grado_info").append("GRADO: " + gradoInfo);
        documentHTML.getElementById("grupo_info").append("GRUPO: " + grupoInfo);
        documentHTML.getElementById("mes_info").append("MES: " + mesInfo);
        documentHTML.getElementById("anio_info").append("AÑO: " + AnioInfo);
    }



    public static void listaHTMLPDF(File file, ArrayList<Alumno> alumnos, ArrayList<Asignatura> asignaturas) {

        ArrayList<Asignatura> asignaturasAcademicas = new ArrayList<>();
        ArrayList<Asignatura> asignaturasOpcionales = new ArrayList<>();

        asignaturas.forEach(a -> {
            if(a.getTipo().equals("academica")){
                asignaturasAcademicas.add(a);
            }else{
                asignaturasOpcionales.add(a);

            }
        });


        final String RUTA_LISTA_PDF = file.toString() + ".pdf";

        String filePath = System.getProperty("user.dir");
        File templateHTML = new File(filePath + "\\index.html");
        File listaHTML = new File(filePath + "\\listas.html");
        File listaPDF = new File(RUTA_LISTA_PDF);

        try {
            org.jsoup.nodes.Document documentHTML;
            documentHTML = Jsoup.parse(templateHTML);
            documentHTML.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);

            // manipulando el DOM
            agregarAsignaturasAcademicas(documentHTML, asignaturasAcademicas, "tr_academicas");
            agregarAsignaturasAcademicas(documentHTML, asignaturasOpcionales,"tr_opcionales");
            
            alumnos.forEach(alumno -> {
                agregarAlumno(documentHTML, alumno);
                agregarPromedioAcademico(documentHTML, asignaturasAcademicas, "tobody__formacion");
                agregarPromedioAcademico(documentHTML, asignaturasOpcionales, "tobody__opcionales");
            });
            
            //Agregar row extra para el promedio de materias
            agregarPromedioAcademico(documentHTML, asignaturasAcademicas, "tobody__formacion");
            agregarPromedioAcademico(documentHTML, asignaturasOpcionales, "tobody__opcionales");

            agregarInformacionExtra(documentHTML, alumnos);

            // obtencion del contenido de la plantilla HTML
            String contenidoHTML = documentHTML.html();

            // Escribiendo en el archivo lista HTML
            BufferedWriter bfr = new BufferedWriter(new FileWriter(listaHTML));
            bfr.write(contenidoHTML);
            bfr.close();

            // Convirtiendo en PDF el archivo lista.html
            OutputStream os = new FileOutputStream(listaPDF);
            PdfRendererBuilder pdfBuilder = new PdfRendererBuilder();

            //Arreglando bug de caracteres UTF-8
            W3CDom w3cDom = new W3CDom(); // org.jsoup.helper.W3CDom
            org.w3c.dom.Document w3cDoc = w3cDom.fromJsoup(documentHTML);

            pdfBuilder.useFastMode();
            // pdfBuilder.withFile(listaHTML);
            pdfBuilder.withW3cDocument(w3cDoc, null);
            pdfBuilder.toStream(os);
            pdfBuilder.run();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}