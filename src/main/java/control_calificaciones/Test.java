package control_calificaciones;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

public class Test {
    public static void main(String[] args) throws IOException {

        String filePath = System.getProperty("user.dir");
        File templateHTML = new File(filePath + "\\index.html");
        File listaPDF = new File(filePath + "\\index.pdf");
        File listaHTML = new File(filePath + "\\listas.html");

        // Manipulacion del html
        Document documentHTML = Jsoup.parse(templateHTML);
        documentHTML.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
        // documentHTML.getElementById("container").append("<p>Agregado desde JAVA
        // 3</p>");
        // documentHTML.getElementById("container").append("<img src='./logo.png'
        // width='100px'/>");

        // obtencion del contenido de la plantilla HTML
        String contenidoHTML = documentHTML.html();
        // System.out.println(contenidoHTML);

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
            pdfBuilder.useFastMode();
            pdfBuilder.withFile(listaHTML);
            pdfBuilder.toStream(os);
            pdfBuilder.run();
        }
    }
}
