package data;

/**
 * Classe para representar um dado simples, já optimizado para ser utilizado na criação de gráficos que não precisam de mais valores.
 *
 */

public class DataPointSimple {
    private String date;
    private int value;
    private DataGroup group;

    public DataPointSimple(DataPoint dataPoint, DataGroup group) {
        this.setDate(dataPoint.getDate());
        this.setGroup(group);
        if(group == DataGroup.CASOS){
            setValue(dataPoint.getConfirmed());
        }
        else if (group == DataGroup.MORTES){
            setValue(dataPoint.getDeaths());
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public DataGroup getGroup() {
        return group;
    }

    public void setGroup(DataGroup group) {
        this.group = group;
    }
}
