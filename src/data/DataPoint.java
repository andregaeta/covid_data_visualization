package data;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Classe para completamente representar uma peça de dado fornecida pelo .csv
 *
 */

public class DataPoint implements Serializable {
    private String state;
    private String city;
    private String date;
    private String placeType;
    private int confirmed;
    private int deaths;
    private int orderForPlace;
    private boolean isLast;
    private String population2019;
    private String population;
    private String ibgeCode;
    private float confirmedPer100k;
    private float deathRate;

    public DataPoint(String string) {
        String[] lines = string.split(",");

        for (String line : lines){
            if(line.isEmpty()){
                line = "";
            }
        }

        date = lines[0];
        state = lines[1];
        city = lines[2];
        placeType = lines[3];
        confirmed = Integer.parseInt(lines[4]);
        deaths = Integer.parseInt(lines[5]);
        orderForPlace = Integer.parseInt(lines[6]);
        isLast = (lines[7].equals("True"));
        population2019 = lines[8];;
        population = lines[9];
        ibgeCode = lines[10];
        try{
            confirmedPer100k = Float.parseFloat(lines[11]);
        }
        catch (NumberFormatException e){
            confirmedPer100k = 0;
        }
        deathRate = Float.parseFloat(lines[12]);
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getDate() {
        return date;
    }

    public String getPlaceType() {
        return placeType;
    }

    public int getConfirmed() {
        return confirmed;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getOrderForPlace() {
        return orderForPlace;
    }

    public boolean isLast() {
        return isLast;
    }

    public String getPopulation2019() {
        return population2019;
    }

    public String getPopulation() {
        return population;
    }

    public String getIbgeCode() {
        return ibgeCode;
    }

    public float getConfirmedPer100k() {
        return confirmedPer100k;
    }

    public float getDeathRate() {
        return deathRate;
    }
}
