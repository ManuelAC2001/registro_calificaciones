package control_calificaciones.helpers.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
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
import control_calificaciones.helpers.estadisticas.alumnos.EstadisticaBasica;

public class GenerarPDF {

    public static void generarEstadisticasBasicas(File file, List<EstadisticaBasica> estadisticas,
            List<EstadisticaBasica> estadisticasGrados, Integer totalAlumnos) {

        try {

            String NOMBRE_ARCHIVO = file.toString() + ".pdf";
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(new File(NOMBRE_ARCHIVO)));
            documento.open();

            // agregando imagen
            String nombreLogo = "logo.png";
            String rutaLogo = new File(nombreLogo).getAbsolutePath();
            Image logo = Image.getInstance(rutaLogo);
            logo.setAlignment(Image.ALIGN_CENTER);
            logo.scalePercent(5f);
            documento.add(logo);

            Paragraph informacion1 = new Paragraph("C.C.T: 12PR0396H         Instituto Hispanoamericano Mexicano",
                    FontFactory.getFont("Arial", 10, Font.NORMAL, BaseColor.BLACK));
            informacion1.setAlignment(Element.ALIGN_LEFT);
            informacion1.setSpacingBefore(10);
            documento.add(informacion1);

            Paragraph informacion2 = new Paragraph(
                    "Turno: 100         Domicilio: C. VICENTE GUERRERO NO 49         ZONA:048",
                    FontFactory.getFont("Arial", 10, Font.NORMAL, BaseColor.BLACK));
            informacion2.setAlignment(Element.ALIGN_LEFT);
            informacion2.setSpacingBefore(10);
            documento.add(informacion2);

            Paragraph informacion3 = new Paragraph("Localidad: ACAPULCO DE JUAREZ       Ciclo Escolar: 2022 - 2023",
                    FontFactory.getFont("Arial", 10, Font.NORMAL, BaseColor.BLACK));
            informacion3.setAlignment(Element.ALIGN_LEFT);
            informacion3.setSpacingBefore(10);
            documento.add(informacion3);
            
            Paragraph informacion4 = new Paragraph(
                    "Municipio: ACAPULCO DE JUAREZ       id.Docto: " + ((Math.random() * 2001) + 1),
                    FontFactory.getFont("Arial", 10, Font.NORMAL, BaseColor.BLACK));
            informacion4.setAlignment(Element.ALIGN_LEFT);
            informacion4.setSpacingBefore(10);
            documento.add(informacion4);

            // Creamos la tabla (Bitacora)
            PdfPTable tablaBitacora = new PdfPTable(5);
            tablaBitacora.setSpacingBefore(30);

            // Le damos titulo a cada columna
            tablaBitacora.addCell(new Paragraph("GRADO",    FontFactory.getFont("Arial", 12, Font.BOLD)));
            tablaBitacora.addCell(new Paragraph("GRUPO",    FontFactory.getFont("Arial", 12, Font.BOLD)));
            tablaBitacora.addCell(new Paragraph("HOMBRES",  FontFactory.getFont("Arial", 12, Font.BOLD)));
            tablaBitacora.addCell(new Paragraph("MUJERES",  FontFactory.getFont("Arial", 12, Font.BOLD)));
            tablaBitacora.addCell(new Paragraph("TOTAL",    FontFactory.getFont("Arial", 12, Font.BOLD)));

            estadisticas.forEach(estadistica -> {
                tablaBitacora.addCell(estadistica.getGradoNombre());
                tablaBitacora.addCell(estadistica.getGrupoNombre());
                tablaBitacora.addCell(estadistica.getCantidadHombres().toString());
                tablaBitacora.addCell(estadistica.getCantidadMujeres().toString());
                tablaBitacora.addCell(estadistica.getCantidadAlumnosTotal().toString());
            });

            Integer cantidadAlumnosHombres = EstadisticaBasica.alumnos.stream().filter(a -> a.getGenero().equals('H'))
                    .collect(Collectors.toList()).size();
            Integer cantidadAlumnosMujeres = EstadisticaBasica.alumnos.stream().filter(a -> a.getGenero().equals('M'))
                    .collect(Collectors.toList()).size();

            tablaBitacora.addCell("Total General");
            tablaBitacora.addCell("");
            tablaBitacora.addCell(cantidadAlumnosHombres.toString());
            tablaBitacora.addCell(cantidadAlumnosMujeres.toString());
            tablaBitacora.addCell(totalAlumnos.toString());
            documento.add(tablaBitacora);

            Paragraph informacion5 = new Paragraph("SUPERVISOR (A)              DIRECTOR (A)",
                    FontFactory.getFont("Arial", 10, Font.NORMAL, BaseColor.BLACK));
            informacion5.setAlignment(Element.ALIGN_CENTER);
            informacion5.setSpacingBefore(10);
            documento.add(informacion5);

            documento.close();

        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

    }

    public static void generarPDF(File file) {

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

            Paragraph nombreInstituto = new Paragraph("Instituto Hispanoamericano Mexicano",
                    FontFactory.getFont("Arial", 30, Font.BOLD, BaseColor.BLUE));
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
            // fin de la conexion a la base de datos

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