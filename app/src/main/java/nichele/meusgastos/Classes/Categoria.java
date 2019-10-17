package nichele.meusgastos.Classes;

public class Categoria {

    public int codigo;
    public String nome;
    String tipo;

    public Categoria(int pcodigo, String pnome, String ptipo){
        codigo = pcodigo;
        nome = pnome;
        tipo = ptipo;
    }

   public String getCodigoString() {
       return String.valueOf(codigo);
   }
   public int getCodigoInt() { return codigo; }
   public String getNome() {
       return nome;
   }
   public String getTipo() {
      return tipo;
   }

   @Override
   public String toString(){
      return nome;
   }
}
