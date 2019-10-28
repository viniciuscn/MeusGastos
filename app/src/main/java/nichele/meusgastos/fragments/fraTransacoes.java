package nichele.meusgastos.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ddz.floatingactionbutton.FloatingActionMenu;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nichele.meusgastos.BancoSQLite;
import nichele.meusgastos.Classes.Transacao;
import nichele.meusgastos.adapters.ExtratoAdapter;
import nichele.meusgastos.R;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.util.rotinas;

public class fraTransacoes extends Fragment {

   private View view;
   private Toolbar toolbar;
   private int primariaescura, primaria;
   private TipoDado listar;
   public fraTransacoes() {
      // Required empty public constructor
   }


   public fraTransacoes(TipoDado plistar) {
      listar = plistar;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      view = inflater.inflate(R.layout.fragment_transacoes, container, false);

      toolbar = getActivity().findViewById(R.id.toolbar);
      toolbar.setTitle("Transações");

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         toolbar.setElevation(0);
      }
      definecores();

      filtraperiodo();
      mostradados();

      FloatingActionMenu fabmenu = getActivity().findViewById(R.id.fabmenu);

//      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)fabmenu.getLayoutParams();
//      params.setMargins(0,0,0,200 );
//      //params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//      params.addRule(RelativeLayout.ALIGN_BOTTOM, R.id.navegacao);
//      fabmenu.setLayoutParams(params);


      BottomNavigationView bnv = view.findViewById(R.id.navegacao);
      if(listar.equals(TipoDado.entradas))
         bnv.getMenu().findItem(R.id.mnuentradas).setChecked(true);
      else if(listar.equals(TipoDado.saidas))
         bnv.getMenu().findItem(R.id.mnusaidas).setChecked(true);
      else
         bnv.getMenu().findItem(R.id.mnuextrato).setChecked(true);


      bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
               case R.id.mnuextrato:
                  listar=TipoDado.extrato;
                  break;
               case R.id.mnuentradas:
                  listar =TipoDado.entradas;
                  break;
               case R.id.mnusaidas:
                  listar =TipoDado.saidas;
                  break;
            }
            definecores();
            mostradados();
            return true;
         }
      });
      return view;
   }



   private void definecores(){
      if(listar == TipoDado.entradas){
         primariaescura = getResources().getColor( R.color.verdeescuro );
         primaria = getResources().getColor( R.color.verde );
      }else if(listar == TipoDado.saidas){
         primariaescura = getResources().getColor( R.color.vermelhoescuro );
         primaria = getResources().getColor( R.color.vermelho );
      }else if(listar == TipoDado.extrato){
         primariaescura = getResources().getColor( R.color.colorPrimaryDark );
         primaria = getResources().getColor( R.color.colorPrimary );
      }
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         Window window = getActivity().getWindow();
         window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         window.setStatusBarColor( primariaescura );
      }
      toolbar.setBackgroundColor(primaria);
      RelativeLayout layfiltro = view.findViewById(R.id.layfiltro);
      layfiltro.setBackgroundColor( primaria );

   }

   public void mostradados(){
      BancoSQLite db = new BancoSQLite(getContext());
      ArrayList<Transacao> dados = db.getTransacoes(listar,lbldatinicial.getText().toString(),lbldatfinal.getText().toString());

      float sldanterior = db.buscavalores(TipoDado.sldanterior, lbldatinicial.getText().toString(), "");
      float receitas = db.buscavalores(TipoDado.entradas, lbldatinicial.getText().toString(),lbldatfinal.getText().toString());
      float despesas = db.buscavalores(TipoDado.saidas, lbldatinicial.getText().toString(),lbldatfinal.getText().toString());
      float balmensal = receitas - despesas;
      float sldatual = sldanterior+balmensal;

      TextView lblbalmensal = view.findViewById(R.id.tvbalmensal);
      lblbalmensal.setText(rotinas.formatavalorBR(balmensal));
      rotinas.setColorCampoValor(getContext(),lblbalmensal);

      TextView tvsldatual = view.findViewById(R.id.tvsldatual);
      tvsldatual.setText(rotinas.formatavalorBR(sldatual));
      //rotinas.setColorCampoValor(getContext(),tvsldatual);

      db.close();

      RecyclerView recyclerView = view.findViewById(R.id.lista_dados);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new LinearLayoutManager( getContext()));
      ExtratoAdapter adapter = new ExtratoAdapter(dados);
      recyclerView.setAdapter(adapter);

      //GroupAdapter customAdapter = new GroupAdapter(getContext(), gdados);
      //recyclerView.setAdapter(customAdapter);
   }

   @Override
   public void onStart() {
      super.onStart();
   }
   @Override
   public void onResume() {
      super.onResume();
      mostradados();
   }
   @Override
   public void onStop() { super.onStop(); }
   @Override
   public void onDetach(){
      super.onDetach();
   }

   private TextView lblmesextenso, lbldatinicial, lbldatfinal;
   private ImageButton cmdant, cmdnext;
   private GregorianCalendar gc = new GregorianCalendar();

   private void filtraperiodo(){

      cmdant = view.findViewById(R.id.cmdant);
      lblmesextenso = view.findViewById(R.id.lblmes_extenso);
      cmdnext = view.findViewById(R.id.cmdnext);

      lbldatinicial = view.findViewById(R.id.lbldatinicial);
      lbldatfinal = view.findViewById(R.id.lbldatfinal);

      lblmesextenso.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            gc = new GregorianCalendar();
            mostradatas();
            //Toast.makeText(context,new Date().toString(),Toast.LENGTH_SHORT).show();
         }
      });
      mostradatas();
      cmdant.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            gc.set(GregorianCalendar.YEAR, Integer.valueOf(lbldatinicial.getText().subSequence(0,4).toString()));
            gc.set(GregorianCalendar.MONTH, Integer.valueOf(lbldatinicial.getText().subSequence(5,7).toString())-1);
            gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(lbldatinicial.getText().subSequence(8,10).toString()));
            gc.setTime( DateAdd( DateInterval.mes,-1, gc.getTime()) );
            mostradatas();
         }
      });
      cmdnext.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            gc.set(GregorianCalendar.YEAR, Integer.valueOf(lbldatinicial.getText().subSequence(0,4).toString()));
            gc.set(GregorianCalendar.MONTH, Integer.valueOf(lbldatinicial.getText().subSequence(5,7).toString())-1);
            gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(lbldatinicial.getText().subSequence(8,10).toString()));
            gc.setTime( DateAdd( DateInterval.mes,1, gc.getTime()) );
            mostradatas();
         }
      });
   }

   private void mostradatas(){
      lbldatinicial.setText( gc.get(Calendar.YEAR) + "-" + String.format("%02d", gc.get(Calendar.MONTH)+1 ) + "-01" );
      lbldatfinal.setText( gc.get(Calendar.YEAR) +"-" + String.format("%02d", gc.get(Calendar.MONTH)+1 ) +"-" + String.format("%02d", gc.getActualMaximum(Calendar.DAY_OF_MONTH)) );
      lblmesextenso.setText(setMesPorExtenso(gc.getTime()));
      mostradados();
   }

   enum DateInterval{
      dia,
      mes,
      ano
   }

   private Date DateAdd(DateInterval interval, int number, Date datevalue){
      Calendar c = Calendar.getInstance();
      c.setTime(datevalue);

      switch (interval){
         case dia:
            c.add(Calendar.DATE, number);
            break;

         case mes:
            c.add(Calendar.MONTH, number);
            break;

         case ano:
            c.add(Calendar.YEAR, number);
            break;
      }
      return c.getTime();
   }

   private String setMesPorExtenso(Date data){
      SimpleDateFormat sdf;
      //if (data.getYear() == new Date().getYear())
      sdf= new SimpleDateFormat("MMMM, yyyy");
      //else
      //sdf= new SimpleDateFormat("MMM/yy");
      return upperCaseFirst(sdf.format(data));
   }

   private String upperCaseFirst(String value) {

      // Convert String to char array.
      char[] array = value.toCharArray();
      // Modify first element in array.
      array[0] = Character.toUpperCase(array[0]);
      // Return string.
      return new String(array);
   }
}
