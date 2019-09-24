package nichele.meusgastos.Classes;

public class Transacao {

    public int id;
    public String data;
    public String funcao;
    public Conta conta;
    public Categoria categoria;
    public String descricao;
    public float valor;


    public Transacao(int pid, String pdata, String pfuncao, Conta pconta, Categoria pcat, String pdescricao, float pvalor){
        id=pid;
        data=pdata;
        funcao=pfuncao;
        conta=pconta;
        categoria=pcat;
        descricao=pdescricao;
        valor=pvalor;
    }

    public String getDescricao() { return descricao; }

    public String getValorString(){
        return String.valueOf(valor);
    }

    public String getFuncao() {
        return funcao;
    }

    public String getData() {
        return data;
    }
}
