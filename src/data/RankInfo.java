package data;

/**
 *
 * Classe para representar dados mais simplificados, já otimizados para serem utilizados na criação de arquivos de ranking.
 */
public class RankInfo {
    private String cidade;
    private String data;
    private float valor;

    public RankInfo(String cidade, String data, float valor) {
        this.setCidade(cidade);
        this.setData(data);
        this.setValor(valor);
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}
