package nichele.meusgastos.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import nichele.meusgastos.BancoSQLite;
import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.Classes.Conta;
import nichele.meusgastos.Classes.Transacao;
import nichele.meusgastos.MainActivity;
import nichele.meusgastos.MapsActivity;
import nichele.meusgastos.R;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.util.datautil;
import nichele.meusgastos.util.rotinas;

public class fraVisaoGeral extends Fragment  {

   private View view;
   Toolbar toolbar;
   TextView textview;
   EditText txtSQL;

   TextView lblgeo;


   public fraVisaoGeral() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,                            Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_visaogeral, container, false);
      toolbar = getActivity().findViewById(R.id.toolbar);
      //toolbar.setTitle("Visão Geral");
      definecores();

      //textview = view.findViewById(R.id.txtsaldo);

      //rotinas.animateTextView(0,200, textview);

//      Button cmd = view.findViewById(R.id.cmdrodanumeros);
//      cmd.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            rotinas.animateTextView(0,200.50f,textview);
//         }
//      });

      montacardvalores();


      return view;
   }


   private void definecores(){

      txtSQL = view.findViewById(R.id.txtSQL);

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         Window window = getActivity().getWindow();
         window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         window.setStatusBarColor(  getResources().getColor(R.color.colorPrimaryDark));
      }
      toolbar.setBackgroundColor( getResources().getColor(R.color.colorPrimary) );

      Button cmdgeo = view.findViewById(R.id.cmdgeo);
      cmdgeo.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            startActivityForResult(new Intent(getActivity(),MapsActivity.class), 0);
//            MapsActivity tela = new MapsActivity();
//            getActivity().getSupportFragmentManager().beginTransaction()
//            .replace()
//            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            ft.commit();

            BancoSQLite db = new BancoSQLite(getContext());
            ArrayList<Conta> contas = db.listacontas();
            ArrayList<Categoria> categorias = db.listacategorias(TipoDado.nenhum);
            ArrayList<Transacao> transacoes = db.getTransacoes(TipoDado.extrato,"1900/01/01","2999/12/31");
            String sql = "";
            for (Conta c : contas){
               sql += "INSERT INTO CONTAS VALUES("+c.getCodigoString() +", '"+c.getNome()+"'\n";
            }
            for (Categoria c : categorias){
               sql += "INSERT INTO CATEGORIAS VALUES("+c.getCodigoString() +", '"+c.getNome()+"', '"+c.getTipo() +"')\n";
            }
            for (Transacao t : transacoes){
               sql += "INSERT INTO TRANSACOES VALUES("+t.id +", '"+
                     t.data+"', '"+
                     t.funcao + "', "+
                     t.conta.getCodigoString() + ", "+
                     t.categoria.getCodigoString() + ", '"+
                     t.getDescricao() + "', "+
                     t.getValorString() + ", '"+
                     t.getQuitado() + "')\n";
            }

//            txtSQL.setScroller(new Scroller(getContext()));
//            txtSQL.setMaxLines(10);
//            txtSQL.setVerticalScrollBarEnabled(true);
//            txtSQL.setMovementMethod(new ScrollingMovementMethod());

            txtSQL.setText(sql);


            db.close();
         }
      });
//            txtSQL.setOnTouchListener(new View.OnTouchListener() {
//               @Override
//               public boolean onTouch(View v, MotionEvent event) {
//                  if (v.getId() == R.id.inputFormComments) {
//                     v.getParent().requestDisallowInterceptTouchEvent(true);
//                     switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                        case MotionEvent.ACTION_UP:
//                           v.getParent().requestDisallowInterceptTouchEvent(false);
//                           break;
//                     }
//                  }
//                  return false;
//               }
//            });
      lblgeo = view.findViewById(R.id.lblgeo);
   }


   private void  montacardvalores(){
      TextView card_mes = view.findViewById(R.id.card_mes);
      TextView tvsldanterior = view.findViewById(R.id.card_sldanterior);
      RelativeLayout layE = view.findViewById(R.id.layE);
      TextView tvreceitas = view.findViewById(R.id.card_vlrreceitas);
      RelativeLayout layS = view.findViewById(R.id.layS);
      TextView tvdespesas = view.findViewById(R.id.card_vlrdespesas);
      TextView tvsldrd = view.findViewById(R.id.card_sldrd);
      TextView tvsldfinal = view.findViewById(R.id.card_sldfinal);

      card_mes.setText(datautil.formatadata(new Date(),"mmm, yy"));


      BancoSQLite db = new BancoSQLite(getContext());
      String datinicial = datautil.primeirodiadomes(new Date());
      String datfinal = datautil.ultimodiadomes(new Date());

      float sldanterior = db.buscavalores(TipoDado.sldanterior, datinicial, "");
      float receitas = db.buscavalores(TipoDado.entradas, datinicial, datfinal);
      float despesas = db.buscavalores(TipoDado.saidas, datinicial, datfinal);
      float sldrd = receitas - despesas;
      float sldatual = sldanterior+sldrd;
      db.close();

      tvsldanterior.setText(rotinas.formatavalorBR(sldanterior));
      tvreceitas.setText(rotinas.formatavalorBR(receitas));
      tvdespesas.setText(rotinas.formatavalorBR(despesas));
      tvsldrd.setText(rotinas.formatavalorBR(sldrd));
      tvsldfinal.setText(rotinas.formatavalorBR(sldatual));
      if (sldatual < 0)
         tvsldfinal.setTextColor(getResources().getColor(R.color.vermelho));
      else
         tvsldfinal.setTextColor(getResources().getColor(R.color.verde));

      layE.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            ((MainActivity)getActivity()).fabmenu.collapse();
            ((MainActivity)getActivity()).navigationDrwarerLeft.setSelection(1);
            ((MainActivity)getActivity()).abrefragment(new fraTransacoes(TipoDado.entradas));
         }
      });
      layS.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            ((MainActivity)getActivity()).fabmenu.collapse();
            ((MainActivity)getActivity()).navigationDrwarerLeft.setSelection(1);
            ((MainActivity)getActivity()).abrefragment(new fraTransacoes(TipoDado.saidas));
         }
      });
   }

   @Override
   public void onAttach(Context context) { super.onAttach(context); }

   @Override
   public void onResume() {
      super.onResume();
      montacardvalores();
   }

   @Override
   public void onDetach() { super.onDetach(); }

   @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {
      super.onActivityResult(requestCode, resultCode, data);

      if (resultCode == getActivity().RESULT_OK && requestCode == 1) {
         String resposta = "latitude: " + data.getStringExtra("latitude") + " long: "+data.getStringExtra("longitude");
         //Toast.makeText(getActivity(),"Mensagem Recebida da SegundaActivity:\n" + resposta, Toast.LENGTH_LONG).show();
         Toast.makeText(getActivity(),"Mensagem Recebida da SegundaActivity:\n" + resposta, Toast.LENGTH_LONG).show();
         lblgeo.setText(resposta);
      }
   }


}
