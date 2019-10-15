package nichele.meusgastos.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;

import nichele.meusgastos.BancoSQLite;
import nichele.meusgastos.R;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.util.datautil;
import nichele.meusgastos.util.rotinas;

public class fraVisaoGeral extends Fragment  {

   private View view;
   Toolbar toolbar;
   TextView textview;

   public fraVisaoGeral() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      view = inflater.inflate(R.layout.fragment_visaogeral, container, false);
      toolbar = getActivity().findViewById(R.id.toolbar);
      //toolbar.setTitle("Visão Geral");
      definecores();

      textview = view.findViewById(R.id.txtsaldo);

      rotinas.animateTextView(0,200, textview);

      Button cmd = view.findViewById(R.id.cmdrodanumeros);
      cmd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            rotinas.animateTextView(0,200.50f,textview);
         }
      });

      montacardvalores();
      return view;
   }

   private void definecores(){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         Window window = getActivity().getWindow();
         window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         window.setStatusBarColor(  getResources().getColor(R.color.colorPrimaryDark));
      }
      toolbar.setBackgroundColor( getResources().getColor(R.color.colorPrimary));
   }


   private void  montacardvalores(){
      TextView card_mes = view.findViewById(R.id.card_mes);
      TextView txtsldanterior = view.findViewById(R.id.card_sldanterior);
      TextView txtreceitas = view.findViewById(R.id.card_vlrreceitas);
      TextView txtdespesas = view.findViewById(R.id.card_vlrdespesas);
      TextView txtsldrd = view.findViewById(R.id.card_sldrd);
      TextView txtsldfinal = view.findViewById(R.id.card_sldfinal);

      card_mes.setText(datautil.formatadata(new Date(),"mmm, yy"));


      BancoSQLite db = new BancoSQLite(getContext());
      String datinicial = datautil.primeirodiadomes(new Date());
      String datfinal = datautil.ultimodiadomes(new Date());

      float sldanterior = db.buscavalores(TipoDado.sldanterior, datinicial, "");
      float receitas = db.buscavalores(TipoDado.entradas, datinicial, datfinal);
      float despesas = db.buscavalores(TipoDado.saidas, datinicial, datfinal);
      float sldrd = receitas - despesas;
      float sldfinal = sldanterior+sldrd;
      db.close();

      txtsldanterior.setText(rotinas.formatavalorBR(sldanterior));
      txtreceitas.setText(rotinas.formatavalorBR(receitas));
      txtdespesas.setText(rotinas.formatavalorBR(despesas));
      txtsldrd.setText(rotinas.formatavalorBR(sldrd));
      txtsldfinal.setText(rotinas.formatavalorBR(sldfinal));

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




}
