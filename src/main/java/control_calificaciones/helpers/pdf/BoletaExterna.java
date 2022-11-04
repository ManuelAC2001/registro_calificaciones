package control_calificaciones.helpers.pdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import control_calificaciones.data.PeriodoDAOH;
import control_calificaciones.models.AlumnoH;
import control_calificaciones.models.AsignaturaH;
import control_calificaciones.models.CalificacionH;
import control_calificaciones.models.PeriodoH;
import control_calificaciones.models.TutorH;

public class BoletaExterna {

    public static void generarPDF(File file, List<CalificacionH> calificacionesBoleta, Boolean esBoletaOficial) {

        final String RUTA_LISTA_PDF = file.toString() + ".pdf";

        String filePath = System.getProperty("user.dir");
        File templateHTML = new File(filePath + "\\boletaExterna.html");
        File listaHTML = new File(filePath + "\\boletaExternaGenerada.html");
        File listaPDF = new File(RUTA_LISTA_PDF);

        try {
            Document documentHTML;
            documentHTML = Jsoup.parse(templateHTML);
            documentHTML.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);

            // manipulando el DOM
            agregarInformacioPersonal(documentHTML, calificacionesBoleta);
            agregarMateriasAcademicas(documentHTML, calificacionesBoleta);
            agregarPromedioTrimestral(documentHTML, calificacionesBoleta);
            agregarInformacionFecha(documentHTML, calificacionesBoleta);

            setDocumentoOficial(documentHTML, esBoletaOficial);


            // obtencion del contenido de la plantilla HTML
            String contenidoHTML = documentHTML.html();

            // Escribiendo en el archivo lista HTML
            BufferedWriter bfr = new BufferedWriter(new FileWriter(listaHTML));
            bfr.write(contenidoHTML);
            bfr.close();

            // Convirtiendo en PDF el archivo lista.html
            OutputStream os = new FileOutputStream(listaPDF);
            PdfRendererBuilder pdfBuilder = new PdfRendererBuilder();

            // Arreglando bug de caracteres UTF-8
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

    private static Boolean trimestreCalificado(List<CalificacionH> calificacionesBoleta, String nombrePeriodo) {
        
        AlumnoH alumno = calificacionesBoleta.get(0).getAlumno();
        List<AsignaturaH> materias = alumno.getAula().getGrado().getAsignaturas();

        PeriodoH periodo = new PeriodoH(nombrePeriodo);
        periodo = new PeriodoDAOH().buscarNombre(periodo);

        Integer numeroCalificacionesByPeriodo = calificacionesBoleta.stream().filter(c -> {
            return
            c.getMes().getPeriodo().getNombre().equals(nombrePeriodo);
        })
        .collect(Collectors.toList())
        .size();        

        Integer maximoCalificacionesByPeriodo = periodo.getMeses().size() * materias.size();

        return numeroCalificacionesByPeriodo == maximoCalificacionesByPeriodo;

    }

    private static void agregarInformacioPersonal(Document documentHTML, List<CalificacionH> calificacionesBoleta) {

        // obteniendo al alumno
        AlumnoH alumno = calificacionesBoleta.get(0).getAlumno();
        String grupoInfo = calificacionesBoleta.get(0).getGrupo().getNombre().toUpperCase();
        
        String gradoInfo = calificacionesBoleta.get(0).getGrado().getNombre().toUpperCase();
        String cicloEscolarInfo = calificacionesBoleta.get(0).getCicloEscolar().getNombre();

        TutorH tutor = alumno.getTutor();

        String msg = gradoInfo + "GRADO DE EDUCACIÓN PRIMARIA CICLO ESCOLAR " + cicloEscolarInfo;

        documentHTML.getElementById("apellido_paterno").append(alumno.getApellidoPaterno().toUpperCase());
        documentHTML.getElementById("apellido_materno").append(alumno.getApellidoMaterno().toUpperCase());
        documentHTML.getElementById("nombre").append(alumno.getNombre().toUpperCase());
        documentHTML.getElementById("curp").append(alumno.getCurp().toUpperCase());

        documentHTML.getElementById("alumno__grupo").append(grupoInfo);
        documentHTML.getElementById("alumno__grado__ciclo_escolar").append(msg);

        documentHTML.getElementById("tutor__nombre").append(tutor.getNombreCompleto().toUpperCase() + ": ");
    }
    
    private static void agregarMateriasAcademicas(Document documentHTML, List<CalificacionH> calificacionesBoleta) {

        // obtenemos al alumno
        AlumnoH alumno = calificacionesBoleta.get(0).getAlumno();

        Element tbodyMaterias = documentHTML.getElementById("tbody___materias");

        List<AsignaturaH> asignaturasAcademicas = alumno.getAula().getGrado().getAsignaturas().stream().filter(c -> {
            return c.getTipoAsignatura().getNombre().equals("academica");
        })
                .collect(Collectors.toList());

        asignaturasAcademicas.forEach(c -> {
            // creamos un tr para cada materia
            Element trMateria = documentHTML.createElement("tr");
            trMateria.append("<td>" + c.getNombre().toUpperCase() + "</td>");
            tbodyMaterias.appendChild(trMateria);
        });
    }
    
    private static void agregarPromedioTrimestral(Document documentoHTML,
            List<CalificacionH> calificacionesBoleta) {

        AlumnoH alumno = calificacionesBoleta.get(0).getAlumno();

        // obtenemos las materias del alumno
        List<AsignaturaH> materias = alumno.getAula().getGrado().getAsignaturas().stream().filter(c -> {
            return c.getTipoAsignatura().getNombre().equals("academica");
        })
                .collect(Collectors.toList());

        List<CalificacionH> calificacionesMaterias = calificacionesBoleta.stream().filter(c -> {
            return 
            c.getAsignatura().getTipoAsignatura().getNombre().equals("academica");
        })
                .collect(Collectors.toList());
                

        Double promedioFinalTrimestre1 = 0.0;
        Double promedioFinalTrimestre2 = 0.0;
        Double promedioFinalTrimestre3 = 0.0;

        Double promedioFinalMateria = 0.0;

        Double promedioFinal = 0.0;

        Boolean trimestreCalificado1 = trimestreCalificado(calificacionesBoleta, "1°");
        Boolean trimestreCalificado2 = trimestreCalificado(calificacionesBoleta, "2°");
        Boolean trimestreCalificado3 = trimestreCalificado(calificacionesBoleta, "3°");

        for( AsignaturaH m : materias ){

            Double promedioTrimestre1 = 0.0;
            Double promedioTrimestre2 = 0.0;
            Double promedioTrimestre3 = 0.0;


            Element tbodyTrimestre = documentoHTML.getElementById("tbody__trimestre");
            Element trTrimestre = documentoHTML.createElement("tr");

            for (CalificacionH c : calificacionesMaterias) {

                if(m.getNombre().equals(c.getAsignatura().getNombre()) && c.getMes().getPeriodo().getNombre().equals("1°")){
                    promedioTrimestre1 += c.getResultado();
                }

                if(m.getNombre().equals(c.getAsignatura().getNombre()) && c.getMes().getPeriodo().getNombre().equals("2°")){
                    promedioTrimestre2 += c.getResultado();
                }

                if(m.getNombre().equals(c.getAsignatura().getNombre()) && c.getMes().getPeriodo().getNombre().equals("3°")){
                    promedioTrimestre3 += c.getResultado();
                }

            }

            promedioTrimestre1 /= 3;
            promedioFinalTrimestre1 += promedioTrimestre1;

            promedioTrimestre2 /= 3;
            promedioFinalTrimestre2 += promedioTrimestre2;

            promedioTrimestre3 /= 3;
            promedioFinalTrimestre3 += promedioTrimestre3;

            // trTrimestre.append("<td>"+ String.format("%.2f", promedioTrimestre1) +"</td>");
            // trTrimestre.append("<td>"+ String.format("%.2f", promedioTrimestre2) +"</td>");
            // trTrimestre.append("<td>"+ String.format("%.2f", promedioTrimestre3) +"</td>");            
            
            
            promedioFinalMateria = promedioTrimestre1 + promedioTrimestre2 + promedioTrimestre3; 
            promedioFinalMateria /= 3;
            promedioFinal += promedioFinalMateria;

            trTrimestre.append("<td>"+ String.format("%.2f", promedioTrimestre1) +"</td>");
            trTrimestre.append("<td>"+ String.format("%.2f", promedioTrimestre2) +"</td>");
            trTrimestre.append("<td>"+ String.format("%.2f", promedioTrimestre3) +"</td>");
            trTrimestre.append("<td>"+ String.format("%.2f", promedioFinalMateria) +"</td>");            


            if(!trimestreCalificado1){
                trTrimestre.child(0).attr("style", "color:white");
            }

            if(!trimestreCalificado2){
                trTrimestre.child(1).attr("style", "color:white");
            }

            if(!trimestreCalificado3){
                trTrimestre.child(2).attr("style", "color:white");
                trTrimestre.child(3).attr("style", "color:white");
            }

            tbodyTrimestre.appendChild(trTrimestre);

        }

        
        //promedios finales de cada trimestre
        promedioFinalTrimestre1 /= materias.size();
        promedioFinalTrimestre2 /= materias.size();
        promedioFinalTrimestre3 /= materias.size();

        promedioFinal /= materias.size();
        Element tdPromedioFinal = documentoHTML.getElementById("promedio_final");

        if(!trimestreCalificado3){
            tdPromedioFinal.attr("style", "color:white");
        }

        tdPromedioFinal.append(String.format("%.2f", promedioFinal));
    }

    private static void agregarInformacionFecha(Document documentHTML, List<CalificacionH> calificacionesBoleta) {

        LocalDate fecha = LocalDate.now();

        String infoAnio = Integer.toString(fecha.getYear());
        String infoMes = fecha.getMonth().toString();
        String infoMesNumero = Integer.toString(fecha.getMonthValue());
        String infoDia = Integer.toString(fecha.getDayOfMonth());
        

        Element tdAnio =  documentHTML.getElementById("anio");
        Element tdMes =  documentHTML.getElementById("mes");
        Element tdDia =  documentHTML.getElementById("dia");

        tdAnio.append(infoAnio);
        tdMes.append(infoMes);
        tdDia.append(infoDia);

        String folio = calificacionesBoleta.get(0).getIdCalificacion() + infoDia + infoMesNumero + infoAnio; 

        documentHTML.getElementById("folio").append("FOLIO: " + folio);

    }

    private static void setDocumentoOficial(Document documentHTML, Boolean esBoletaOficial) {

        Element divMarcaAgua = documentHTML.getElementById("marca_agua");

        if(esBoletaOficial){
            divMarcaAgua.addClass("hide");
        }

    }

}
