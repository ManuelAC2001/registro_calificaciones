package control_calificaciones;
public class Main {

    public static void main(String[] args) {

        // try {

        //     InputStream inputStream = App.class.getResourceAsStream("boletaInterna.html");
            
        //     BufferedReader reader;
        //     reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

        //     StringBuilder htmlBuilder = new StringBuilder();

        //     for (String line : reader.lines().collect(Collectors.toList())) {
        //         htmlBuilder.append(line);
        //     }

        //     Document documentHTML = Jsoup.parse(htmlBuilder.toString());
        //     documentHTML.outputSettings().syntax(org.jsoup.nodes.Document.OutputSettings.Syntax.xml);
        //     String contenidoHTML = documentHTML.html();

        //     System.out.println(contenidoHTML);
        // } catch (UnsupportedEncodingException e) {
        //     e.printStackTrace();
        // }

        App.main(args);
    }

}
