package control_calificaciones;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;


public class Test {
    public static void main(String[] args) throws IOException {

        String filePath = System.getProperty("user.dir");

        File htmlSource = new File(filePath + "\\index.html");
        File pdfDest = new File(filePath + "\\index.pdf");

        try (OutputStream os = new FileOutputStream(pdfDest)) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withFile(htmlSource);
            builder.toStream(os);
            builder.run();
        }
        
    }
}
