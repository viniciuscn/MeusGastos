package nichele.meusgastos.Classes;

public class Conta {

    public int codigo;
    public String nome;

    public Conta(int pcodigo, String pnome){
        codigo = pcodigo;
        nome = pnome;
    }

    public String getCodigoString() {
        return String.valueOf(codigo);
    }
    public int getCodigoInt() { return codigo; }
    public String getNome() { return nome; }

    @Override
    public String toString(){
        return nome;
    }
}
