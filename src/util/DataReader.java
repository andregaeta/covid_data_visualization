package util;

import data.DataGroup;
import data.DataPoint;
import data.DataPointSimple;
import data.RankInfo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;

/**
 *
 * Classe que realiza a leitura do arquivo .csv e os interpreta como objetos.
 */

public class DataReader {
    private static final String path = "./src/resources/caso.csv.gz";

    /**
     * Procura e retorna dados completos sobre o local desejado.
     *
     * @param name nome do local
     * @param isCity identifica se o nome se refere à uma cidade ou um estado
     * @return lista com as peças de dados encontrados
     */
    public static ArrayList<DataPoint> getDataByLocation(String name, boolean isCity){
        ArrayList<DataPoint> dataPoints = new ArrayList<DataPoint>();
        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(path));
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzip))){
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                DataPoint dataPoint = new DataPoint(line);

                 if(isCity && dataPoint.getCity().toLowerCase().equals(name.toLowerCase())){
                    dataPoints.add(dataPoint);
                }
                else if (!isCity && dataPoint.getState().toLowerCase().equals(name.toLowerCase()) && dataPoint.getCity().equals("")){
                    dataPoints.add(dataPoint);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro ao carregar o arquivo.");
        }
        return dataPoints;
    }

    /**
     * Procura e retorna dados simples sobre o local desejado.
     *
     * @param name nome do local
     * @param isCity identifica se o nome se refere à uma cidade ou um estado
     * @return lista com as peças de dados encontrados
     */
    public static ArrayList<DataPointSimple> getSimpleDataByLocation(String name, boolean isCity){
        ArrayList<DataPointSimple> dataPoints = new ArrayList<DataPointSimple>();
        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(path));
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzip))){
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                DataPoint dataPoint = new DataPoint(line);
                if(name.equals("")){
                    return getSimpleDataTotal();
                }
                else if(isCity && dataPoint.getCity().toLowerCase().equals(name.toLowerCase())){
                    dataPoints.add(new DataPointSimple(dataPoint, DataGroup.CASOS));
                    dataPoints.add(new DataPointSimple(dataPoint, DataGroup.MORTES));
                }
                else if (!isCity && dataPoint.getState().toLowerCase().equals(name.toLowerCase()) && dataPoint.getCity().equals("")){
                    dataPoints.add(new DataPointSimple(dataPoint, DataGroup.CASOS));
                    dataPoints.add(new DataPointSimple(dataPoint, DataGroup.MORTES));
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro ao carregar o arquivo.");
        }
        return dataPoints;
    }

    /**
     * Procura e retorna dados simples do país inteiro
     *
     * @return lista com as peças de dados encontrados
     */
    public static ArrayList<DataPointSimple> getSimpleDataTotal(){
        ArrayList<DataPointSimple> dataPoints = new ArrayList<DataPointSimple>();
        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(path));
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzip))){
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                DataPoint dataPoint = new DataPoint(line);
                if(!dataPoint.getCity().equals(""))
                    continue;
                boolean found = false;
                for(DataPointSimple dataPointSimple : dataPoints){
                    if(dataPoint.getDate().equals(dataPointSimple.getDate())){
                        if(dataPointSimple.getGroup() == DataGroup.CASOS) {
                            dataPointSimple.setValue(dataPointSimple.getValue() + dataPoint.getConfirmed());
                            found = true;
                        }
                        else if (dataPointSimple.getGroup() == DataGroup.MORTES) {
                            dataPointSimple.setValue(dataPointSimple.getValue() + dataPoint.getDeaths());
                            found = true;
                        }
                    }
                }
                if(!found){
                    dataPoints.add(new DataPointSimple(dataPoint, DataGroup.CASOS));
                    dataPoints.add(new DataPointSimple(dataPoint, DataGroup.MORTES));
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro ao carregar o arquivo.");
        }
        return dataPoints;
    }

    /**
     * Retorna uma lista com o ranking das 10 cidades com maiores casos/100k habitantes
     * @return lista ordenada do ranking
     */
    public static ArrayList<RankInfo> getMaiorCasos100k(){
        ArrayList<RankInfo> list = new ArrayList<>();
        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(path));
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzip))){
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                DataPoint dataPoint = new DataPoint(line);
                if(!dataPoint.isLast() || dataPoint.getCity().isBlank() || dataPoint.getConfirmedPer100k() == 0 || dataPoint.getCity().equals("Importados/Indefinidos")){
                    continue;
                }
                int i = 0;
                boolean added = false;
                for(RankInfo info : list.toArray(new RankInfo[0])){
                    if(i > 9) break;
                    if(dataPoint.getConfirmedPer100k() > info.getValor()){
                        list.add(i, new RankInfo(dataPoint.getCity(), dataPoint.getDate(), dataPoint.getConfirmedPer100k()));
                        added = true;
                        break;
                    }
                    i++;
                }
                if(list.size() < 10 && !added)
                    list.add(i, new RankInfo(dataPoint.getCity(), dataPoint.getDate(), dataPoint.getConfirmedPer100k()));
                else if (list.size() > 10 && added){
                    list.remove(10);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro ao carregar o arquivo.");
        }

        return list;
    }

    /**
     * Retorna uma lista com o ranking das 10 cidades com menores casos/100k habitantes
     * @return lista ordenada do ranking
     */
    public static ArrayList<RankInfo> getMenorCasos100k(){
        ArrayList<RankInfo> list = new ArrayList<>();
        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(path));
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzip))){
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                DataPoint dataPoint = new DataPoint(line);
                if(!dataPoint.isLast() || dataPoint.getCity().isBlank() || dataPoint.getConfirmedPer100k() == 0 || dataPoint.getCity().equals("Importados/Indefinidos")){
                    continue;
                }
                int i = 0;
                boolean added = false;
                for(RankInfo info : list.toArray(new RankInfo[0])){
                    if(i > 9) break;
                    if(dataPoint.getConfirmedPer100k() < info.getValor()){
                        list.add(i, new RankInfo(dataPoint.getCity(), dataPoint.getDate(), dataPoint.getConfirmedPer100k()));
                        added = true;
                        break;
                    }
                    i++;
                }
                if(list.size() < 10 && !added)
                    list.add(i, new RankInfo(dataPoint.getCity(), dataPoint.getDate(), dataPoint.getConfirmedPer100k()));
                else if (list.size() > 10 && added){
                    list.remove(10);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro ao carregar o arquivo.");
        }

        return list;
    }
    /**
     * Retorna uma lista com o ranking das 10 cidades com maior taxa de mortalidade
     * @return lista ordenada do ranking
     */

    public static ArrayList<RankInfo> getMaiorMortalidade(){
        ArrayList<RankInfo> list = new ArrayList<>();
        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(path));
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzip))){
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                DataPoint dataPoint = new DataPoint(line);
                if(!dataPoint.isLast() || dataPoint.getCity().isBlank() || dataPoint.getDeathRate() == 0f || dataPoint.getCity().equals("Importados/Indefinidos")){
                    continue;
                }
                int i = 0;
                boolean added = false;
                for(RankInfo info : list.toArray(new RankInfo[0])){
                    if(i > 9) break;
                    if(dataPoint.getDeathRate() > info.getValor()){
                        list.add(i, new RankInfo(dataPoint.getCity(), dataPoint.getDate(), dataPoint.getDeathRate()));
                        added = true;
                        break;
                    }
                    i++;
                }
                if(list.size() < 10 && !added)
                    list.add(i, new RankInfo(dataPoint.getCity(), dataPoint.getDate(), dataPoint.getDeathRate()));
                else if (list.size() > 10 && added){
                    list.remove(10);
                }

            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro ao carregar o arquivo.");
        }

        return list;
    }
    /**
     * Retorna uma lista com o ranking das 10 cidades com menor taxa de mortalidade
     * @return lista ordenada do ranking
     */
    public static ArrayList<RankInfo> getMenorMortalidade(){
        ArrayList<RankInfo> list = new ArrayList<>();
        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(path));
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzip))){
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                DataPoint dataPoint = new DataPoint(line);
                if(!dataPoint.isLast() || dataPoint.getCity().isBlank() || dataPoint.getDeathRate() == 0f || dataPoint.getCity().equals("Importados/Indefinidos")){
                    continue;
                }
                int i = 0;
                boolean added = false;
                for(RankInfo info : list.toArray(new RankInfo[0])){
                    if(i > 9) break;
                    if(dataPoint.getDeathRate() < info.getValor()){
                        list.add(i, new RankInfo(dataPoint.getCity(), dataPoint.getDate(), dataPoint.getDeathRate()));
                        added = true;
                        break;
                    }
                    i++;
                }
                if(list.size() < 10 && !added)
                    list.add(i, new RankInfo(dataPoint.getCity(), dataPoint.getDate(), dataPoint.getDeathRate()));
                else if (list.size() > 10 && added){
                    list.remove(10);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro ao carregar o arquivo.");
        }

        return list;
    }

    /**
     * Retorna uma lista com o ranking das 10 cidades com maior taxa de crescimento de casos no último mês
     * @return lista ordenada do ranking
     */
    public static ArrayList<RankInfo> getMaiorTaxaCrescimento(){
        ArrayList<RankInfo> list = new ArrayList<>();
        try (GZIPInputStream gzip = new GZIPInputStream(new FileInputStream(path));
             BufferedReader reader = new BufferedReader(new InputStreamReader(gzip))){
            String line;
            reader.readLine();
            DataPoint mesAtual = null;
            DataPoint mesPassado = null;
            while((line = reader.readLine()) != null){
                DataPoint dataPoint = new DataPoint(line);
                if(dataPoint.getCity().isBlank() || dataPoint.getCity().equals("Importados/Indefinidos")){
                    continue;
                }
                if(dataPoint.getDate().equals("2020-10-01")){
                    mesAtual = dataPoint;
                }
                else if(dataPoint.getDate().equals("2020-09-01") && dataPoint.getCity().equals(mesAtual.getCity())){
                    mesPassado = dataPoint;

                    if(mesPassado.getConfirmed() <= 0) continue;
                    int i = 0;
                    boolean added = false;
                    float taxa = (float) mesAtual.getConfirmed()/mesPassado.getConfirmed() - 1;
                    for(RankInfo info : list.toArray(new RankInfo[0])){
                        if(i > 9) break;
                        if(taxa > info.getValor()){
                            list.add(i, new RankInfo(mesAtual.getCity(), mesAtual.getDate(), taxa));
                            added = true;
                            break;
                        }
                        i++;
                    }
                    if(list.size() < 10 && !added) {
                        list.add(i, new RankInfo(mesAtual.getCity(), mesAtual.getDate(), taxa));
                        added = true;
                    }
                    else if (list.size() > 10 && added){
                        list.remove(10);
                    }
                    if(added){
                        mesAtual = null;
                        mesPassado = null;
                    }
                }


            }
        }
        catch (IOException e){
            e.printStackTrace();
            System.out.println("Erro ao carregar o arquivo.");
        }

        return list;
    }



}
