package nichele.meusgastos.Classes;

public class Categoria {

    public int codigo;
    public String nome;

    public Categoria(int pcodigo, String pnome){
        codigo = pcodigo;
        nome = pnome;
    }

   public String getCodigo() {
       return String.valueOf(codigo);
   }

   public String getNome() {
       return nome;
   }

   @Override
   public String toString(){
      return nome;
   }
}
