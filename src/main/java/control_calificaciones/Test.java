package control_calificaciones;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import control_calificaciones.data.usuarios.UsuarioDAOH;
import control_calificaciones.models.usuarios.UsuarioH;


public class Test {
    public static void main(String[] args) throws IOException {

        UsuarioDAOH usuarioDAO = new UsuarioDAOH();
        UsuarioH user = new UsuarioH(17);
        user = usuarioDAO.buscarPorId(user);
        
        user.setNombreUsuario("margarita");
        usuarioDAO.actualizar(user);        
    }


    public static void agregarAlumno(Document documentHTML) {
        Element alumnoRow = documentHTML.createElement("tr");
        documentHTML.getElementById("tbody__alumnos").appendChild(alumnoRow);
        alumnoRow.append("<td>" + "supa" + "</td>");
        alumnoRow.append("<td>" + "info" + "</td>");
        alumnoRow.append("<td>" + "info" + "</td>");

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
        documentHTML.getElementById("tobody__rasgos").appendChild(rasgosRow);
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");
        rasgosRow.append("<td> - </td>");

        // aqui sera muy variable el resultado
        Element formacionRow = documentHTML.createElement("tr");
        documentHTML.getElementById("tobody__formacion").appendChild(formacionRow);
        formacionRow.append("<td> - </td>");
        formacionRow.append("<td> - </td>");
        formacionRow.append("<td> - </td>");
        formacionRow.append("<td> - </td>");
        formacionRow.append("<td> - </td>");
        formacionRow.append("<td> - </td>");
        formacionRow.append("<td> - </td>");
        formacionRow.append("<td> - </td>");
    }

    public static void generarListaPDF() throws IOException {
        String filePath = System.getProperty("user.dir");
        File templateHTML = new File(filePath + "\\index.html");
        File listaPDF = new File(filePath + "\\index.pdf");
        File listaHTML = new File(filePath + "\\listas.html");

        // Manipulacion del html
        Document documentHTML = Jsoup.parse(templateHTML);
        documentHTML.outputSettings().syntax(Document.OutputSettings.Syntax.xml);

        agregarAlumno(documentHTML);

        // obtencion del contenido de la plantilla HTML
        String contenidoHTML = documentHTML.html();
        System.out.println(contenidoHTML);

        // Escribiendo en el archivo lista HTML
        try {
            BufferedWriter bfr = new BufferedWriter(new FileWriter(listaHTML));
            bfr.write(contenidoHTML);
            bfr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convirtiendo en PDF el archivo lista.html
        try (OutputStream os = new FileOutputStream(listaPDF)) {
            PdfRendererBuilder pdfBuilder = new PdfRendererBuilder();

            // Arreglando bug de caracteres UTF-8
            W3CDom w3cDom = new W3CDom(); // org.jsoup.helper.W3CDom
            org.w3c.dom.Document w3cDoc = w3cDom.fromJsoup(documentHTML);

            pdfBuilder.useFastMode();
            // pdfBuilder.withFile(listaHTML);
            pdfBuilder.withW3cDocument(w3cDoc, null);

            pdfBuilder.toStream(os);
            pdfBuilder.run();
        }
    }
}