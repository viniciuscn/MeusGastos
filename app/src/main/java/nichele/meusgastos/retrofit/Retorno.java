package nichele.meusgastos.retrofit;

import java.util.ArrayList;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.Classes.Conta;
import nichele.meusgastos.Classes.Transacao;

public class Retorno {

   public int tpStatus;
   public String msg;
   public ArrayList<Categoria> categorias;
   public ArrayList<Conta> contas;
   public ArrayList<Transacao> transacoes;


   public Retorno(int ptpstatus, String pmsg, ArrayList<Conta> pcontas, ArrayList<Categoria> cats, ArrayList<Transacao> pmov)   {
      tpStatus=ptpstatus;
      msg=pmsg;
      contas=pcontas;
      categorias=cats;
      transacoes = pmov;
   }
}
