package util;

import data.RankInfo;

import java.io.*;
import java.util.ArrayList;

/**
 * Classe para criação de arquivos de ranking
 */

public class FileBuilder {
    /**
     * Cria um arquivo de ranking com os dados informados
     * @param list lista com dados do ranking
     * @param path local do arquivo a ser criado
     */
    private static void createRanking(ArrayList<RankInfo> list, String path){
        File file = new File(path);

        try{
            file.createNewFile();
        }
        catch (IOException e){
            System.out.println("Não foi possível gerar o arquivo.");
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            StringBuilder sb = new StringBuilder();
            for(RankInfo rankInfo : list){
                sb.append(rankInfo.getCidade() +  " - "+ rankInfo.getData() + " - " + rankInfo.getValor() + "\n");
            }
            bw.write(sb.toString());
        }
        catch (IOException e){
            System.out.println("Não foi possível gerar o arquivo.");
        }
    }

    /**
     * Cria um arquivo de ranking com os 10 países com maior número de casos/100k habitantes
     */
    public static void createMaiorCasos100k(){
        ArrayList<RankInfo> list =  DataReader.getMaiorCasos100k();
        createRanking(list, "./src/output/maior_casos_100k.tsv");
    }
    /**
     * Cria um arquivo de ranking com os 10 países com menor número de casos/100k habitantes
     */
    public static void createMenorCasos100k(){
        ArrayList<RankInfo> list =  DataReader.getMenorCasos100k();
        createRanking(list, "./src/output/menor_casos_100k.tsv");
    }
    /**
     * Cria um arquivo de ranking com os 10 países com maior taxa de mortalidade
     */
    public static void createMaiorMortalidade(){
        ArrayList<RankInfo> list =  DataReader.getMaiorMortalidade();
        createRanking(list, "./src/output/maior_mortalidade.tsv");
    }
    /**
     * Cria um arquivo de ranking com os 10 países com menor taxa de mortalidade
     */
    public static void createMenorMortalidade(){
        ArrayList<RankInfo> list =  DataReader.getMenorMortalidade();
        createRanking(list, "./src/output/menor_mortalidade.tsv");
    }

    /**
     * Cria um arquivo de ranking com os 10 países com maior taxa de crescimento do número de casos no último mês
     */
    public static void createMaiorTaxaCrescimento(){
        ArrayList<RankInfo> list =  DataReader.getMaiorTaxaCrescimento();
        createRanking(list, "./src/output/maior_taxa_crescimento.tsv");
    }

}
