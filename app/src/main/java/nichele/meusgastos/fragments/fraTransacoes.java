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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nichele.meusgastos.BancoSQLite;
import nichele.meusgastos.Classes.Transacao;
import nichele.meusgastos.ExtratoAdapter;
import nichele.meusgastos.R;
import nichele.meusgastos.TipoDado;

public class fraTransacoes extends Fragment {

   private View view;
   private Toolbar toolbar;
   private int primariaescura, primaria;
   private TipoDado listar;
   public fraTransacoes() {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

      view = inflater.inflate(R.layout.fragment_transacoes, container, false);
      primariaescura = getResources().getColor(R.color.verdeescuro);
      primaria = getResources().getColor(R.color.verde);
      toolbar = getActivity().findViewById(R.id.toolbar);
      toolbar.setTitle("Transações");
      listar = TipoDado.entradas;
      definecores();

      BancoSQLite db = new BancoSQLite(getContext());
      db.zerabanco();
      db.gravatransacoes("INC",1,"15/09/2019","ENT",1,1,"Teste de Entrada 1", "1000,85");
      db.gravatransacoes("INC",2,"16/09/2019","ENT",1,1,"Teste de Entrada 2", "589,56");
      db.gravatransacoes("INC",3,"16/09/2019","ENT",1,1,"Teste de Entrada 2", "20,00");

      db.gravatransacoes("INC",4,"24/09/2019","BAI",1,1,"SÃO MATHEUS", "19,50");
      db.close();
      filtraperiodo();
      mostradados();


      BottomNavigationView bnv = view.findViewById(R.id.navegacao);
      bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
         @Override
         public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
               case R.id.mnuentradas:
                  primariaescura = getResources().getColor(R.color.verdeescuro);
                  primaria = getResources().getColor(R.color.verde);
                  listar =TipoDado.entradas;
                  break;
               case R.id.mnusaidas:
                  primariaescura = getResources().getColor(R.color.vermelhoescuro);
                  primaria = getResources().getColor(R.color.vermelho);
                  listar =TipoDado.saidas;
                  break;
               case R.id.mnuextrato:
                  primariaescura = getResources().getColor(R.color.cinzaescuro);
                  primaria = getResources().getColor(R.color.cinzaescuro);
                  listar=TipoDado.extrato;
                  break;
            }
            mostradados();
            definecores();
            return true;
         }
      });
      return view;
   }

   private void definecores(){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
         Window window = getActivity().getWindow();
         window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         window.setStatusBarColor( primariaescura);
      }
      toolbar.setBackgroundColor(primaria);
   }

   private void mostradados(){
      BancoSQLite db = new BancoSQLite(getContext());
      ArrayList<Transacao> dados = db.getTransacoes(listar,lbldatinicial.getText().toString(),lbldatfinal.getText().toString());
      db.close();
      ExtratoAdapter adapter = new ExtratoAdapter(dados, getContext());

      RecyclerView recyclerView = view.findViewById(R.id.lsvtransacoes);
      recyclerView.setAdapter(adapter);
      recyclerView.setLayoutManager(new LinearLayoutManager( getContext()));
//      switch (listar){
//         case entradas:
//
//         case saidas:
//
//         case extrato:
//      }
   }

   @Override
   public void onStart() {
      super.onStart();
   }

   @Override
   public void onStop() {
      super.onStop();

   }

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
