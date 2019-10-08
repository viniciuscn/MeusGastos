package nichele.meusgastos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.Classes.Conta;

public class actTransacoes_Manutencao extends AppCompatActivity  {

   Context context;
   String situacao = "";
   TipoDado tipodado;


   TextView txtvalor;

   GregorianCalendar gc = new GregorianCalendar();
   ImageButton cmdant;
   TextView lbldata;
   TextView txtdata;
   ImageButton cmdnext;

   int codconta;

   int codcategoria;
   TextView txtdescricao;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_transacoes_manutencao);
      overridePendingTransition(R.anim.filho_entrando, R.anim.main_saindo);
      Intent intent = getIntent();
      situacao = intent.getStringExtra("situacao");
      tipodado = (TipoDado)intent.getSerializableExtra("tipdado");
      carregatela();


   }

   private void carregatela(){
      context = this;
      //declarações dos campos
      Toolbar toolbar =  findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      txtvalor = findViewById(R.id.man_txtvalor);

      cmdant = findViewById(R.id.man_cmdant);
      lbldata = findViewById(R.id.man_lbldata);
      txtdata = findViewById(R.id.man_txtdata);
      cmdnext = findViewById(R.id.man_cmdnext);


      Spinner cmbconta = findViewById(R.id.man_cmbconta);
      Spinner cmbcategoria = findViewById(R.id.man_cmbcategoria);

      txtdescricao = findViewById(R.id.man_txtdescricao);

      BancoSQLite db = new BancoSQLite(context);
      ArrayList<Conta> lstcontas = db.listacontas();
      ArrayAdapter rstcontas = new ArrayAdapter(context,android.R.layout.simple_spinner_item, lstcontas);
      rstcontas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      cmbconta.setAdapter(rstcontas);

      ArrayList<Categoria> lstcategorias = db.listacategorias(tipodado);
      ArrayAdapter<Categoria> rstcategorias = new ArrayAdapter<Categoria>(this, R.layout.support_simple_spinner_dropdown_item, lstcategorias);
      rstcategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      cmbcategoria.setAdapter(rstcategorias);
      db.close();

      Button cmdsalvar = findViewById(R.id.cmdsalvar);

      //tratamentos - eventos, valores iniciais
      txtvalor.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

         }
      });
      if (situacao.equals("INC")) {
         lbldata.setText(DataUtil.formatadata(new Date(), "ddd, dd mmm yyyy"));
         txtdata.setText(DataUtil.formatadata(new Date(), "yyyy-mm-dd"));


      }


      cmdant.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            gc.set(GregorianCalendar.YEAR, Integer.valueOf(txtdata.getText().subSequence(0,4).toString()));
            gc.set(GregorianCalendar.MONTH, Integer.valueOf(txtdata.getText().subSequence(5,7).toString())-1);
            gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(txtdata.getText().subSequence(8,10).toString()));
            gc.setTime( DataUtil.DateAdd( DataUtil.DateInterval.dia,-1, gc.getTime()) );
            mostradatas();

         }
      });


      cmdnext.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            gc.set(GregorianCalendar.YEAR, Integer.valueOf(txtdata.getText().subSequence(0,4).toString()));
            gc.set(GregorianCalendar.MONTH, Integer.valueOf(txtdata.getText().subSequence(5,7).toString())-1);
            gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(txtdata.getText().subSequence(8,10).toString()));
            gc.setTime( DataUtil.DateAdd( DataUtil.DateInterval.dia,1, gc.getTime()) );
            mostradatas();

         }
      });


      cmdsalvar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            BancoSQLite db = new BancoSQLite(context);

            String resultado = db.gravatransacoes(situacao, 0, txtdata.getText().toString(), (tipodado == TipoDado.entradas ? "E" : "S"),
                    codconta,
                    codcategoria,
                    txtdescricao.getText().toString(),
                    txtvalor.getText().toString());
            db.close();
            finish();
         }
      });

   }

   private void mostradatas(){


      lbldata.setText(DataUtil.formatadata(gc.getTime(),"ddd, dd mmm yyyy"));
      txtdata.setText( gc.get(Calendar.YEAR) + "-" + String.format("%02d", new Integer(gc.get(Calendar.MONTH)+1 )) + "-" + String.format("%02d", gc.get(Calendar.DAY_OF_MONTH)));
   }

   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }
}
