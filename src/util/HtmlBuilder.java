package util;

import data.DataPointSimple;

import java.io.*;
import java.util.ArrayList;


/**
 * Classe para criação de gráficos em html
 *
 */
public class HtmlBuilder {
    private static String top = "<!DOCTYPE HTML>\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "  <meta content=\"text/html;charset=utf-8\" http-equiv=\"Content-Type\">\n" +
            "  <meta content=\"utf-8\" http-equiv=\"encoding\">\n" +
            "  <title>Gráfico COVID-19</title>\n" +
            "\n" +
            "  <style type=\"text/css\">\n" +
            "    body, html {\n" +
            "      font-family: sans-serif;\n" +
            "    }\n" +
            "  </style>\n" +
            "\n" +
            "<script type=\"text/javascript\" src=\"https://momentjs.com/downloads/moment-with-locales.min.js\"></script>\n" +
            "<script type=\"text/javascript\" src=\"https://unpkg.com/vis-timeline@latest/standalone/umd/vis-timeline-graph2d.min.js\"></script>\n" +
            "<link href=\"https://unpkg.com/vis-timeline@latest/styles/vis-timeline-graph2d.min.css\" rel=\"stylesheet\" type=\"text/css\" />\n" +
            "</head>\n" +
            "<body>\n" +
            "<h2>Gráfico COVID-19</h2>\n" +
            "<br />\n" +
            "<div id=\"visualization\"></div>\n" +
            "\n" +
            "<script type=\"text/javascript\">\n" +
            "\n" +
            "    var container = document.getElementById('visualization');\n" +
            "\n" +
            "    var groups = new vis.DataSet([\n" +
            "            {id: \"Casos\", content: \"Casos\"},\n" +
            "            {id: \"Mortes\", content: \"Mortes\"}\n" +
            "        ]\n" +
            "    );\n" +
            "\n" +
            "    var items = [";
    private static String bottom = "];\n" +
            "\n" +
            "    var dataset = new vis.DataSet(items);\n" +
            "    var options = {\n" +
            "        start: '2020-02-10',\n" +
            "        end: '2020-02-18',\n" +
            "        legend: true,\n" +
            "        locale: 'pt-br'\n" +
            "    };\n" +
            "  var graph2d = new vis.Graph2d(container, dataset, groups, options);\n" +
            "</script>\n" +
            "</body>\n" +
            "</html>";

    /**
     * Cria um arquivo .html com um gráfico dos dados informados
     * @param list lista com os dados
     */
    public static void createHTML(ArrayList<DataPointSimple> list){
        String items = "";
        StringBuilder sb = new StringBuilder(top);
        for (DataPointSimple data : list){
            sb.append("{x: \'"+ data.getDate() + "\', y: " + data.getValue() + ", group: \"" + data.getGroup().toString().substring(0, 1).toUpperCase() + data.getGroup().toString().toLowerCase().substring(1) + "\"},");
        }
        sb.append(bottom);

        File file = new File("./src/output/grafico.html");

        try(BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            bw.write(sb.toString());
        }
        catch (IOException e){
            System.out.println("Não foi possível gerar o arquivo.");
        }





    }


}
