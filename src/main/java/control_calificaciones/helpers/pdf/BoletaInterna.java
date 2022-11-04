package control_calificaciones.helpers.pdf;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import control_calificaciones.data.AlumnoDAOH;
import control_calificaciones.data.PeriodoDAOH;
import control_calificaciones.models.AlumnoH;
import control_calificaciones.models.AsignaturaH;
import control_calificaciones.models.CalificacionH;
import control_calificaciones.models.CicloEscolarH;
import control_calificaciones.models.InasistenciaH;
import control_calificaciones.models.PeriodoH;

public class BoletaInterna {

    public static void generarPDF(File file, List<CalificacionH> calificacionesBoleta) {

        final String RUTA_LISTA_PDF = file.toString() + ".pdf";

        String filePath = System.getProperty("user.dir");
        File templateHTML = new File(filePath + "\\boletaInterna.html");
        File boletaHTML = new File(filePath + "\\boletaInternaGenerada.html");
        File boletaPDF = new File(RUTA_LISTA_PDF);

        try {
            Document documentHTML;
            documentHTML = Jsoup.parse(templateHTML);
            documentHTML.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);

            // manipulando el DOM

            agregarInformacioPersonal(documentHTML, calificacionesBoleta);

            // ....para materias academicas
            agregarMateriasAcademicas(documentHTML, calificacionesBoleta);

            agregarCalificacionesAcademicasMensual(documentHTML, calificacionesBoleta, "diagnostico",
                    "tbody__diagnostico");
            agregarCalificacionesAcademicasMensual(documentHTML, calificacionesBoleta, "septiembre",
                    "tbody__septiembre");
            agregarCalificacionesAcademicasMensual(documentHTML, calificacionesBoleta, "octubre", "tbody__octubre");
            agregarCalificacionesAcademicasMensual(documentHTML, calificacionesBoleta, "noviembre/diciembre",
                    "tbody__noviembre_diciembre");
            agregarCalificacionesAcademicasMensual(documentHTML, calificacionesBoleta, "enero", "tbody__enero");
            agregarCalificacionesAcademicasMensual(documentHTML, calificacionesBoleta, "febrero", "tbody__febrero");
            agregarCalificacionesAcademicasMensual(documentHTML, calificacionesBoleta, "marzo", "tbody__marzo");
            agregarCalificacionesAcademicasMensual(documentHTML, calificacionesBoleta, "abril", "tbody__abril");
            agregarCalificacionesAcademicasMensual(documentHTML, calificacionesBoleta, "mayo", "tbody__mayo");
            agregarCalificacionesAcademicasMensual(documentHTML, calificacionesBoleta, "junio", "tbody__junio");

            agregarPromedioTrimestralAcademico(documentHTML, calificacionesBoleta);

            // ....para materias complementarias
            agregarMateriasComplementarias(documentHTML, calificacionesBoleta);

            agregarCalificacionesComplementariasMensual(documentHTML, calificacionesBoleta, "diagnostico",
                    "tbody__diagnostico_complementaria");
            agregarCalificacionesComplementariasMensual(documentHTML, calificacionesBoleta, "septiembre",
                    "tbody__septiembre_complementaria");
            agregarCalificacionesComplementariasMensual(documentHTML, calificacionesBoleta, "octubre",
                    "tbody__octubre_complementaria");
            agregarCalificacionesComplementariasMensual(documentHTML, calificacionesBoleta, "noviembre/diciembre",
                    "tbody__noviembre_diciembre_complementaria");
            agregarCalificacionesComplementariasMensual(documentHTML, calificacionesBoleta, "enero",
                    "tbody__enero_complementaria");
            agregarCalificacionesComplementariasMensual(documentHTML, calificacionesBoleta, "febrero",
                    "tbody__febrero_complementaria");
            agregarCalificacionesComplementariasMensual(documentHTML, calificacionesBoleta, "marzo",
                    "tbody__marzo_complementaria");
            agregarCalificacionesComplementariasMensual(documentHTML, calificacionesBoleta, "abril",
                    "tbody__abril_complementaria");
            agregarCalificacionesComplementariasMensual(documentHTML, calificacionesBoleta, "mayo",
                    "tbody__mayo_complementaria");
            agregarCalificacionesComplementariasMensual(documentHTML, calificacionesBoleta, "junio",
                    "tbody__junio_complementaria");

            agregarPromedioTrimestralomplementaria(documentHTML, calificacionesBoleta);

            // obtencion del contenido de la plantilla HTML
            String contenidoHTML = documentHTML.html();

            // Escribiendo en el archivo boleta HTML
            BufferedWriter bfr = new BufferedWriter(new FileWriter(boletaHTML));
            bfr.write(contenidoHTML);
            bfr.close();

            // Convirtiendo en PDF el archivo boleta.html
            OutputStream os = new FileOutputStream(boletaPDF);
            PdfRendererBuilder pdfBuilder = new PdfRendererBuilder();

            // Arreglando bug de caracteres UTF-8
            W3CDom w3cDom = new W3CDom(); // org.jsoup.helper.W3CDom
            org.w3c.dom.Document w3cDoc = w3cDom.fromJsoup(documentHTML);

            pdfBuilder.useFastMode();
            pdfBuilder.withW3cDocument(w3cDoc, null);
            pdfBuilder.toStream(os);
            pdfBuilder.run();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void agregarInformacioPersonal(Document documentHTML, List<CalificacionH> calificacionesBoleta) {

        // obteniendo al alumno
        AlumnoH alumno = calificacionesBoleta.get(0).getAlumno();
        String gradoInfo = calificacionesBoleta.get(0).getGrado().getNombre().toUpperCase();
        String grupoInfo = calificacionesBoleta.get(0).getGrupo().getNombre().toUpperCase();
        String gradoGrupoInfo = gradoInfo + " " + grupoInfo;

        String cicloEscolarInfo = calificacionesBoleta.get(0).getCicloEscolar().getNombre();

        String nombreInfo = alumno.getNombreCompleto().toUpperCase();

        // Obteniendo el numero de lista
        Integer numeroLista = 0;
        List<AlumnoH> alumnosLista = new AlumnoDAOH().listar().stream().filter(a -> {
            return a.getAula().equals(alumno.getAula());
        })
                .collect(Collectors.toList());
        for (int i = 0; i < alumnosLista.size(); i++) {
            if (alumnosLista.get(i).getCurp().equalsIgnoreCase(alumno.getCurp())) {
                numeroLista = i;
                break;
            }
        }

        documentHTML.getElementById("grado__grupo_info").append("GRADO Y GRUPO: " + gradoGrupoInfo);
        documentHTML.getElementById("ciclo__escolar_info").append("CICLO ESCOLAR: " + cicloEscolarInfo);
        documentHTML.getElementById("nombre__alumno").append("ALUMNO(A): " + nombreInfo);
        documentHTML.getElementById("no__lista").append("NO. LISTA: " + (numeroLista + 1));

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
            trMateria.append("<td rowspan='0'>" + c.getNombre() + "</td>");
            tbodyMaterias.appendChild(trMateria);
        });

        Element trMateria = documentHTML.createElement("tr");
        trMateria.append("<td> PROMEDIO MENSUAL </td>");
        tbodyMaterias.appendChild(trMateria);

    }

    private static void agregarMateriasComplementarias(Document documentHTML,
            List<CalificacionH> calificacionesBoleta) {

        // obtenemos al alumno
        AlumnoH alumno = calificacionesBoleta.get(0).getAlumno();

        Element tbodyMaterias = documentHTML.getElementById("tbody___complementarias");

        List<AsignaturaH> asignaturasComplementarias = alumno.getAula().getGrado().getAsignaturas().stream()
                .filter(c -> {
                    return c.getTipoAsignatura().getNombre().equals("complementaria");
                })
                .collect(Collectors.toList());

        asignaturasComplementarias.forEach(c -> {
            // creamos un tr para cada materia
            Element trMateria = documentHTML.createElement("tr");
            trMateria.append("<td>" + c.getNombre() + "</td>");
            tbodyMaterias.appendChild(trMateria);
        });

        Element trMateria = documentHTML.createElement("tr");
        trMateria.append("<td> PROMEDIO MENSUAL </td>");
        tbodyMaterias.appendChild(trMateria);

        Element trWhiteSpace = documentHTML.createElement("tr");
        trWhiteSpace.append("<td style='border: none; color: white;'>" + "-" + "</td>");
        tbodyMaterias.appendChild(trWhiteSpace);

        Element trInasistencias = documentHTML.createElement("tr");
        trInasistencias.append("<td> INASISTENCIAS </td>");
        tbodyMaterias.appendChild(trInasistencias);

    }

    private static void agregarCalificacionesAcademicasMensual(Document documentHTML,
            List<CalificacionH> calificacionesBoleta, String mesNombre, String tbodyName) {

        List<CalificacionH> calificacionesAcademiicasMensuales = calificacionesBoleta
                .stream()
                .filter(c -> {
                    return c.getAsignatura().getTipoAsignatura().getNombre().equals("academica")
                            &&
                            c.getMes().getNombre().equals(mesNombre);
                })
                .collect(Collectors.toList());

        if (calificacionesAcademiicasMensuales.isEmpty()) {

            Integer numeroMaterias = getNumberoMaterias(calificacionesBoleta, "academica");
            Element tbodyHelper = documentHTML.getElementById(tbodyName);

            for (int i = 0; i < numeroMaterias; i++) {
                Element trCalificacion = documentHTML.createElement("tr");
                trCalificacion.append("<td style='color:white;'>" + " - " + "</td>");
                tbodyHelper.appendChild(trCalificacion);
            }
            Element trCalificacion = documentHTML.createElement("tr");
            trCalificacion.append("<td style='color:white;'>" + " - " + "</td>");
            tbodyHelper.appendChild(trCalificacion);

            return;
        }

        Element tbody = documentHTML.getElementById(tbodyName);

        Integer numeroMaterias = calificacionesAcademiicasMensuales.size();
        Double promedio = 0.0;

        for (CalificacionH c : calificacionesAcademiicasMensuales) {
            Element trCalificacion = documentHTML.createElement("tr");
            trCalificacion.append("<td>" + c.getResultado() + "</td>");
            tbody.appendChild(trCalificacion);
            promedio += c.getResultado();
        }

        promedio /= numeroMaterias;

        Element trCalificacion = documentHTML.createElement("tr");
        trCalificacion.append("<td>" + String.format("%.2f", promedio) + "</td>");
        tbody.appendChild(trCalificacion);

    }

    private static void agregarCalificacionesComplementariasMensual(Document documentHTML,
            List<CalificacionH> calificacionesBoleta, String mesNombre, String tbodyName) {

        AlumnoH alumno = calificacionesBoleta.get(0).getAlumno();
        CicloEscolarH cicloEscolar = calificacionesBoleta.get(0).getCicloEscolar();

        List<CalificacionH> calificacionesAcademiicasMensuales = calificacionesBoleta
                .stream()
                .filter(c -> {
                    return c.getAsignatura().getTipoAsignatura().getNombre().equals("complementaria")
                            &&
                            c.getMes().getNombre().equals(mesNombre);
                })
                .collect(Collectors.toList());

        if (calificacionesAcademiicasMensuales.isEmpty()) {

            Integer numeroMaterias = getNumberoMaterias(calificacionesBoleta, "complementaria");
            Element tbodyHelper = documentHTML.getElementById(tbodyName);

            for (int i = 0; i < numeroMaterias; i++) {
                Element trCalificacion = documentHTML.createElement("tr");
                trCalificacion.append("<td style='color:white;'>" + " - " + "</td>");
                tbodyHelper.appendChild(trCalificacion);
            }

            Element trPromedioMensual = documentHTML.createElement("tr");
            trPromedioMensual.append("<td style='color:white;'>" + " - " + "</td>");
            tbodyHelper.appendChild(trPromedioMensual);

            Element trWhiteSpace = documentHTML.createElement("tr");
            trWhiteSpace.append("<td style='border: none; color: white;'>" + "-" + "</td>");
            tbodyHelper.appendChild(trWhiteSpace);

            Element trInasistencia = documentHTML.createElement("tr");
            trInasistencia.append("<td style='color:white;'>" + "-" + "</td>");
            tbodyHelper.appendChild(trInasistencia);

            return;
        }

        Element tbody = documentHTML.getElementById(tbodyName);

        Integer numeroMaterias = calificacionesAcademiicasMensuales.size();
        Double promedio = 0.0;

        for (CalificacionH c : calificacionesAcademiicasMensuales) {
            Element trCalificacion = documentHTML.createElement("tr");
            trCalificacion.append("<td>" + c.getResultado() + "</td>");
            tbody.appendChild(trCalificacion);
            promedio += c.getResultado();
        }

        // PROMEDIO MENSUAL
        promedio /= numeroMaterias;
        Element trCalificacion = documentHTML.createElement("tr");
        trCalificacion.append("<td>" + String.format("%.2f", promedio) + "</td>");
        tbody.appendChild(trCalificacion);

        // AGREGAMOS LA INFORMACION DEL NUMERO DE INASISTENCIAS
        List<InasistenciaH> inasistenciasMensual = alumno.getInasistencias().stream().filter(i -> {
            return i.getMes().getNombre().equals(mesNombre)
                    &&
                    i.getCicloEscolar().equals(cicloEscolar);
        })
                .collect(Collectors.toList());

        InasistenciaH inasistencia = inasistenciasMensual.get(0);
        Integer numeroInasistencias = inasistencia.getCantidad();

        Element trWhiteSpace = documentHTML.createElement("tr");
        trWhiteSpace.append("<td style='border: none; color: white;'>" + "-" + "</td>");
        tbody.appendChild(trWhiteSpace);

        Element trInasistencia = documentHTML.createElement("tr");
        trInasistencia.append("<td>" + numeroInasistencias + "</td>");
        tbody.appendChild(trInasistencia);

    }

    private static void agregarPromedioTrimestralAcademico(Document documentoHTML,
            List<CalificacionH> calificacionesBoleta) {

        AlumnoH alumno = calificacionesBoleta.get(0).getAlumno();

        // obtenemos las materias del alumno
        List<AsignaturaH> materias = alumno.getAula().getGrado().getAsignaturas().stream().filter(c -> {
            return c.getTipoAsignatura().getNombre().equals("academica");
        })
                .collect(Collectors.toList());

        List<CalificacionH> calificacionesMaterias = calificacionesBoleta.stream().filter(c -> {
            return c.getAsignatura().getTipoAsignatura().getNombre().equals("academica");
        })
                .collect(Collectors.toList());

        Double promedioFinalTrimestre1 = 0.0;
        Double promedioFinalTrimestre2 = 0.0;
        Double promedioFinalTrimestre3 = 0.0;

        Double promedioFinalMateria = 0.0;

        Double promedioFinal = 0.0;

        Boolean  trimestreCalificado1 =  trimestreCalificado(calificacionesBoleta, "1°");
        Boolean  trimestreCalificado2 =  trimestreCalificado(calificacionesBoleta, "2°");
        Boolean  trimestreCalificado3 =  trimestreCalificado(calificacionesBoleta, "3°");

        for (AsignaturaH m : materias) {
            Double promedioTrimestre1 = 0.0;
            Double promedioTrimestre2 = 0.0;
            Double promedioTrimestre3 = 0.0;

            Element tbodyTrimestre = documentoHTML.getElementById("tbody__trimestre");
            Element trTrimestre = documentoHTML.createElement("tr");

            for (CalificacionH c : calificacionesMaterias) {

                if (m.getNombre().equals(c.getAsignatura().getNombre())
                        && c.getMes().getPeriodo().getNombre().equals("1°")) {
                    promedioTrimestre1 += c.getResultado();
                }

                if (m.getNombre().equals(c.getAsignatura().getNombre())
                        && c.getMes().getPeriodo().getNombre().equals("2°")) {
                    promedioTrimestre2 += c.getResultado();
                }

                if (m.getNombre().equals(c.getAsignatura().getNombre())
                        && c.getMes().getPeriodo().getNombre().equals("3°")) {
                    promedioTrimestre3 += c.getResultado();
                }

            }

            promedioTrimestre1 /= 3;
            promedioFinalTrimestre1 += promedioTrimestre1;

            promedioTrimestre2 /= 3;
            promedioFinalTrimestre2 += promedioTrimestre2;

            promedioTrimestre3 /= 3;
            promedioFinalTrimestre3 += promedioTrimestre3;

            promedioFinalMateria = promedioTrimestre1 + promedioTrimestre2 + promedioTrimestre3;
            promedioFinalMateria /= 3;
            promedioFinal += promedioFinalMateria;

            trTrimestre.append("<td>" + String.format("%.2f", promedioTrimestre1) + "</td>");
            trTrimestre.append("<td>" + String.format("%.2f", promedioTrimestre2) + "</td>");
            trTrimestre.append("<td>" + String.format("%.2f", promedioTrimestre3) + "</td>");

            trTrimestre.append("<td>" + String.format("%.2f", promedioFinalMateria) + "</td>");

            if (!trimestreCalificado1) {
                trTrimestre.child(0).attr("style", "color:white");
            }
    
            if (!trimestreCalificado2) {
                trTrimestre.child(1).attr("style", "color:white");
            }
    
            if (!trimestreCalificado3) {
                trTrimestre.child(2).attr("style", "color:white");
                trTrimestre.child(3).attr("style", "color:white");
            }

            tbodyTrimestre.appendChild(trTrimestre);
        }

        
        // promedios finales de cada trimestre
        promedioFinalTrimestre1 /= materias.size();
        promedioFinalTrimestre2 /= materias.size();
        promedioFinalTrimestre3 /= materias.size();
        
        promedioFinal /= materias.size();

        
        Element tbodyTrimestre = documentoHTML.getElementById("tbody__trimestre");
        Element trTrimestre = documentoHTML.createElement("tr");
        trTrimestre.append("<td>" + String.format("%.2f", promedioFinalTrimestre1) + "</td>");
        trTrimestre.append("<td>" + String.format("%.2f", promedioFinalTrimestre2) + "</td>");
        trTrimestre.append("<td>" + String.format("%.2f", promedioFinalTrimestre3) + "</td>");
        trTrimestre.append("<td>" + String.format("%.2f", promedioFinal) + "</td>");

        if (!trimestreCalificado1) {
            trTrimestre.child(0).attr("style", "color:white");
        }

        if (!trimestreCalificado2) {
            trTrimestre.child(1).attr("style", "color:white");
        }

        if (!trimestreCalificado3) {
            trTrimestre.child(2).attr("style", "color:white");
            trTrimestre.child(3).attr("style", "color:white");
        }


        tbodyTrimestre.appendChild(trTrimestre);
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

    private static void agregarPromedioTrimestralomplementaria(Document documentoHTML,
            List<CalificacionH> calificacionesBoleta) {

        AlumnoH alumno = calificacionesBoleta.get(0).getAlumno();

        // obtenemos las materias del alumno
        List<AsignaturaH> materias = alumno.getAula().getGrado().getAsignaturas().stream().filter(c -> {
            return c.getTipoAsignatura().getNombre().equals("complementaria");
        })
                .collect(Collectors.toList());

        List<CalificacionH> calificacionesMaterias = calificacionesBoleta.stream().filter(c -> {
            return c.getAsignatura().getTipoAsignatura().getNombre().equals("complementaria");
        })
                .collect(Collectors.toList());

        Double promedioFinalTrimestre1 = 0.0;
        Double promedioFinalTrimestre2 = 0.0;
        Double promedioFinalTrimestre3 = 0.0;

        Double promedioFinalMateria = 0.0;

        Double promedioFinal = 0.0;

        Boolean  trimestreCalificado1 =  trimestreCalificado(calificacionesBoleta, "1°");
        Boolean  trimestreCalificado2 =  trimestreCalificado(calificacionesBoleta, "2°");
        Boolean  trimestreCalificado3 =  trimestreCalificado(calificacionesBoleta, "3°");

        for (AsignaturaH m : materias) {

            Double promedioTrimestre1 = 0.0;
            Double promedioTrimestre2 = 0.0;
            Double promedioTrimestre3 = 0.0;

            Element tbodyTrimestre = documentoHTML.getElementById("tbody__trimestre_complementaria");
            Element trTrimestre = documentoHTML.createElement("tr");

            for (CalificacionH c : calificacionesMaterias) {

                if (m.getNombre().equals(c.getAsignatura().getNombre())
                        && c.getMes().getPeriodo().getNombre().equals("1°")) {
                    promedioTrimestre1 += c.getResultado();
                }

                if (m.getNombre().equals(c.getAsignatura().getNombre())
                        && c.getMes().getPeriodo().getNombre().equals("2°")) {
                    promedioTrimestre2 += c.getResultado();
                }

                if (m.getNombre().equals(c.getAsignatura().getNombre())
                        && c.getMes().getPeriodo().getNombre().equals("3°")) {
                    promedioTrimestre3 += c.getResultado();
                }

            }

            promedioTrimestre1 /= 3;
            promedioFinalTrimestre1 += promedioTrimestre1;

            promedioTrimestre2 /= 3;
            promedioFinalTrimestre2 += promedioTrimestre2;

            promedioTrimestre3 /= 3;
            promedioFinalTrimestre3 += promedioTrimestre3;

            promedioFinalMateria = promedioTrimestre1 + promedioTrimestre2 + promedioTrimestre3;
            promedioFinalMateria /= 3;
            promedioFinal += promedioFinalMateria;

            trTrimestre.append("<td>" + String.format("%.2f", promedioTrimestre1) + "</td>");
            trTrimestre.append("<td>" + String.format("%.2f", promedioTrimestre2) + "</td>");
            trTrimestre.append("<td>" + String.format("%.2f", promedioTrimestre3) + "</td>");
            trTrimestre.append("<td>" + String.format("%.2f", promedioFinalMateria) + "</td>");

            if (!trimestreCalificado1) {
                trTrimestre.child(0).attr("style", "color:white");
            }
    
            if (!trimestreCalificado2) {
                trTrimestre.child(1).attr("style", "color:white");
            }
    
            if (!trimestreCalificado3) {
                trTrimestre.child(2).attr("style", "color:white");
                trTrimestre.child(3).attr("style", "color:white");
            }

            tbodyTrimestre.appendChild(trTrimestre);
        }

        // promedios finales de cada trimestre
        promedioFinalTrimestre1 /= materias.size();
        promedioFinalTrimestre2 /= materias.size();
        promedioFinalTrimestre3 /= materias.size();

        promedioFinal /= materias.size();

        Element tbodyTrimestre = documentoHTML.getElementById("tbody__trimestre_complementaria");
        Element trTrimestre = documentoHTML.createElement("tr");
        trTrimestre.append("<td>" + String.format("%.2f", promedioFinalTrimestre1) + "</td>");
        trTrimestre.append("<td>" + String.format("%.2f", promedioFinalTrimestre2) + "</td>");
        trTrimestre.append("<td>" + String.format("%.2f", promedioFinalTrimestre3) + "</td>");
        trTrimestre.append("<td>" + String.format("%.2f", promedioFinal) + "</td>");

        if (!trimestreCalificado1) {
            trTrimestre.child(0).attr("style", "color:white");
        }

        if (!trimestreCalificado2) {
            trTrimestre.child(1).attr("style", "color:white");
        }

        if (!trimestreCalificado3) {
            trTrimestre.child(2).attr("style", "color:white");
            trTrimestre.child(3).attr("style", "color:white");
        }

        tbodyTrimestre.appendChild(trTrimestre);

        // DEJAMOS EL ESPACIO EN BLANCO PARA EL FORMATO
        Element trWhiteSpace = documentoHTML.createElement("tr");
        trWhiteSpace.append("<td style='border: none; color: white;'>" + "-" + "</td>");
        trWhiteSpace.append("<td style='border: none; color: white;'>" + "-" + "</td>");
        trWhiteSpace.append("<td style='border: none; color: white;'>" + "-" + "</td>");
        trWhiteSpace.append("<td style='border: none; color: white;'>" + "-" + "</td>");
        tbodyTrimestre.appendChild(trWhiteSpace);

        // AGREGAMOS las inasistencias
        Integer inasistenciasPeriodo1 = getInasistenciasTrimestralesByPeriodo(calificacionesBoleta, "1°");
        Integer inasistenciasPeriodo2 = getInasistenciasTrimestralesByPeriodo(calificacionesBoleta, "2°");
        Integer inasistenciasPeriodo3 = getInasistenciasTrimestralesByPeriodo(calificacionesBoleta, "3°");
        Integer inasistenciasTotal = inasistenciasPeriodo1 + inasistenciasPeriodo2 + inasistenciasPeriodo3;

        Boolean inasistenciasCompletaPeriodo1 = sonInasistenciasCompletaByPeriodo(calificacionesBoleta, "1°");
        Boolean inasistenciasCompletaPeriodo2 = sonInasistenciasCompletaByPeriodo(calificacionesBoleta, "2°");
        Boolean inasistenciasCompletaPeriodo3 = sonInasistenciasCompletaByPeriodo(calificacionesBoleta, "3°");

        Element trInasistencias = documentoHTML.createElement("tr");

        trInasistencias.append("<td>" + inasistenciasPeriodo1 + "</td>");
        trInasistencias.append("<td>" + inasistenciasPeriodo2 + "</td>");
        trInasistencias.append("<td>" + inasistenciasPeriodo3 + "</td>");
        trInasistencias.append("<td>" + inasistenciasTotal + "</td>");

        if (!inasistenciasCompletaPeriodo1) {
            trInasistencias.child(0).attr("style", "color:white");
        }

        if (!inasistenciasCompletaPeriodo2) {
            trInasistencias.child(1).attr("style", "color:white");
        }

        if (!inasistenciasCompletaPeriodo3) {
            trInasistencias.child(2).attr("style", "color:white");
            trInasistencias.child(3).attr("style", "color:white");
        }

        tbodyTrimestre.appendChild(trInasistencias);

    }
    // FUNCIONES "HELPERS"

    private static Boolean sonInasistenciasCompletaByPeriodo(List<CalificacionH> calificacionesBoleta,
            String nombrePeriodo) {
        return getListInasistenciasByPeriodo(calificacionesBoleta, nombrePeriodo).size() == 3;
    }

    private static List<InasistenciaH> getListInasistenciasByPeriodo(List<CalificacionH> calificacionesBoleta,
            String nombrePeriodo) {

        AlumnoH alumno = calificacionesBoleta.get(0).getAlumno();
        CicloEscolarH cicloEscolar = calificacionesBoleta.get(0).getCicloEscolar();

        List<InasistenciaH> inasistenciasTrimestre = alumno.getInasistencias().stream().filter(i -> {
            return i.getMes().getPeriodo().getNombre().equals(nombrePeriodo)
                    &&
                    i.getCicloEscolar().equals(cicloEscolar);
        })
                .collect(Collectors.toList());
        return inasistenciasTrimestre;

    }

    private static Integer getInasistenciasTrimestralesByPeriodo(List<CalificacionH> calificacionesBoleta,
            String nombrePeriodo) {

        Integer numeroInasistencias = 0;
        List<InasistenciaH> inasistenciasTrimestre = getListInasistenciasByPeriodo(calificacionesBoleta, nombrePeriodo);

        for (InasistenciaH i : inasistenciasTrimestre) {
            numeroInasistencias += i.getCantidad();
        }

        return numeroInasistencias;

    }

    private static Integer getNumberoMaterias(List<CalificacionH> calificacionesBoleta, String tipo) {
        return calificacionesBoleta.get(0).getGrado().getAsignaturas().stream().filter(c -> {
            return c.getTipoAsignatura().getNombre().equalsIgnoreCase(tipo);
        })
                .collect(Collectors.toList())
                .size();

    }
}
