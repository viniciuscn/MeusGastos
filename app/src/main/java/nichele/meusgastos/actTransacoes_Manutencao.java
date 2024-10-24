package nichele.meusgastos;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.Classes.Conta;
import nichele.meusgastos.Classes.Transacao;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.util.datautil;
import nichele.meusgastos.util.rotinas;

public class actTransacoes_Manutencao extends AppCompatActivity  {

   Context context;
   Transacao t;
   String situacao = "";
   TipoDado tipodado;

   TextView txtchave;
   int chave;
   EditText txtvalor;

   GregorianCalendar gc = new GregorianCalendar();
   ImageButton cmdant;
   TextView lbldata, txtdata;
   ImageButton cmdnext;

   TextView txtdescricao;

   ArrayList<Conta> lstcontas;
   ArrayList<Categoria> lstcategorias;
   Spinner cmbconta, cmbcategoria;
   int codconta, codcategoria;

   CheckBox chk;

   Switch optrepetir;
   EditText txtrepetir;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_transacoes_manutencao);
      overridePendingTransition(R.anim.filho_entrando, R.anim.main_saindo);
      Intent intent = getIntent();
      situacao = intent.getStringExtra("situacao");
      tipodado = (TipoDado)intent.getSerializableExtra("tipdado");
      t = rotinas.transacao;
      carregatela();
   }

   private void carregatela(){
      context = this;

      //declarações dos campos
      Toolbar toolbar =  findViewById(R.id.toolbar);
      if (tipodado.equals(TipoDado.entradas))
         toolbar.setTitle("Nova Receita");
      else
         toolbar.setTitle("Nova Despesa");





      //mToolbar.setSubtitle("subtitulo");

      setSupportActionBar(toolbar);
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      txtchave = findViewById(R.id.man_chave);
      txtvalor = findViewById(R.id.man_txtvalor);
      txtvalor.requestFocus();
      cmdant = findViewById(R.id.man_cmdant);
      lbldata = findViewById(R.id.man_lbldata);
      txtdata = findViewById(R.id.man_txtdata);
      cmdnext = findViewById(R.id.man_cmdnext);
      txtdescricao = findViewById(R.id.man_txtdescricao);
      cmbconta = findViewById(R.id.man_cmbconta);
      cmbcategoria = findViewById(R.id.man_cmbcategoria);
      chk=findViewById(R.id.man_chkrecpag);
      Button cmdsalvar = findViewById(R.id.cmdsalvar);
      ImageView addcat = findViewById(R.id.man_addcat);


      //txtvalor.addTextChangedListener(new MoneyTextWatcher(txtvalor));
      txtvalor.requestFocus();
      txtvalor.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View v) {
            return true;
         }
      });


      carregacombos();


      //tratamentos - eventos, valores iniciais
      lbldata.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            //Toast.makeText(context, lbldata.getText(), Toast.LENGTH_SHORT).show();
            final Calendar c = Calendar.getInstance();

            // on below line we are getting
            // our day, month and year.
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // on below line we are creating a variable for date picker dialog.
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                  // on below line we are passing context.
                  context,
                  new DatePickerDialog.OnDateSetListener() {
                     @Override
                     public void onDateSet(DatePicker view, int year,
                                           int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our edit text.
                        txtdata.setText(year+"-"+ String.format("%02d", monthOfYear+1)+"-"+ String.format("%02d", dayOfMonth) );

                        gc.set(GregorianCalendar.YEAR, Integer.valueOf(txtdata.getText().subSequence(0,4).toString()));
                        gc.set(GregorianCalendar.MONTH, Integer.valueOf(txtdata.getText().subSequence(5,7).toString())-1);
                        gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(txtdata.getText().subSequence(8,10).toString()));

                        lbldata.setText(datautil.formatadata(gc.getTime(),"ddd, dd mmm yyyy"));
                        //txtdata.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                     }
                  },
                  // on below line we are passing year,
                  // month and day for selected date in our date picker.
                  year, month, day);
            // at last we are calling show to
            // display our date picker dialog.
            datePickerDialog.show();
         }
      });

      txtvalor.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

         }
      });

      BancoSQLite db = new BancoSQLite(context);
      if (situacao.equals("INC")) {
         chave=db.ultimocodigo("transacoes","id")+1;
         lbldata.setText(datautil.formatadata(new Date(), "ddd, dd mmm yyyy"));
         txtdata.setText(datautil.formatadata(new Date(), "yyyy-mm-dd"));
      }
      else{
         chave=t.id;

         //txtvalor.setText( rotinas.formatvalor_edicao(t.getValorString()) );
         txtvalor.setText( t.getValorString() ) ;

         txtdescricao.setText(t.descricao);

         String data = t.getData();
         gc.set(GregorianCalendar.YEAR, Integer.valueOf(data.subSequence(0,4).toString()));
         gc.set(GregorianCalendar.MONTH, Integer.valueOf(data.subSequence(5,7).toString())-1);
         gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(data.subSequence(8,10).toString()));

         lbldata.setText(datautil.formatadata(gc.getTime(), "ddd, dd mmm yyyy"));
         txtdata.setText(datautil.formatadata(gc.getTime(), "yyyy-mm-dd"));


         for(int i = 0; i < cmbconta.getCount(); i++){
            if(cmbconta.getItemAtPosition(i).toString().equals(t.conta.getNome())){
               cmbconta.setSelection(i);
               break;
            }
         }
         for(int i = 0; i < cmbcategoria.getCount(); i++){
            if(cmbcategoria.getItemAtPosition(i).toString().equals(t.categoria.getNome())){
               cmbcategoria.setSelection(i);
               break;
            }
         }
         //chk.setChecked( t.getQuitado().equals("S") ? true : false );
         chk.setChecked(t.getQuitado().equals("S"));

      }
      db.close();
      txtchave.setText(String.valueOf(chave));

      cmdant.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            gc.set(GregorianCalendar.YEAR, Integer.valueOf(txtdata.getText().subSequence(0,4).toString()));
            gc.set(GregorianCalendar.MONTH, Integer.valueOf(txtdata.getText().subSequence(5,7).toString())-1);
            gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(txtdata.getText().subSequence(8,10).toString()));
            gc.setTime( datautil.DateAdd( datautil.DateInterval.dia,-1, gc.getTime()) );
            mostradatas();
         }
      });
      cmdnext.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            gc.set(GregorianCalendar.YEAR, Integer.valueOf(txtdata.getText().subSequence(0,4).toString()));
            gc.set(GregorianCalendar.MONTH, Integer.valueOf(txtdata.getText().subSequence(5,7).toString())-1);
            gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(txtdata.getText().subSequence(8,10).toString()));
            gc.setTime( datautil.DateAdd( datautil.DateInterval.dia,1, gc.getTime()) );
            mostradatas();
         }
      });

      if (tipodado == TipoDado.entradas)
         chk.setText("Recebido");
      else
         chk.setText("Pago");
      cmdsalvar.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            salvar();
         }
      });
      db.close();

      addcat.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent intent = new Intent(context, actCategorias.class);
            intent.putExtra("situacao", "inc");

            startActivityForResult(intent, 1);
         }
      });

      CalendarView frmCalendario=(CalendarView) findViewById(R.id.calendario);

//      optrepetir = findViewById(R.id.optrepetir);
//      txtrepetir = findViewById(R.id.man_txtrepetir);
//      optrepetir.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if (isChecked) {
//               txtrepetir.setVisibility(View.VISIBLE);
//            } else {
//               txtrepetir.setVisibility(View.INVISIBLE);
//               txtrepetir.setText("0");
//            }
//         }
//      });
   }

   private void carregacombos(){
      BancoSQLite db = new BancoSQLite(context);
      lstcontas = db.listacontas();
      ArrayAdapter rstcontas = new ArrayAdapter(context,android.R.layout.simple_spinner_item, lstcontas);
      rstcontas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      cmbconta.setAdapter(rstcontas);

      lstcategorias = db.listacategorias(tipodado);
      ArrayAdapter<Categoria> rstcategorias = new ArrayAdapter<Categoria>(this, R.layout.support_simple_spinner_dropdown_item, lstcategorias);
      rstcategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      cmbcategoria.setAdapter(rstcategorias);
      db.close();
   }

   private void mostradatas(){
      lbldata.setText(datautil.formatadata(gc.getTime(),"ddd, dd mmm yyyy"));
      txtdata.setText( gc.get(Calendar.YEAR) + "-" + String.format("%02d", new Integer(gc.get(Calendar.MONTH)+1 )) + "-" + String.format("%02d", gc.get(Calendar.DAY_OF_MONTH)));
   }

   @Override
   public boolean onPrepareOptionsMenu(Menu menu) {
      super.onPrepareOptionsMenu(menu);
      if (situacao.equals("INC"))
         menu.findItem(R.id.mnuexcluir).setVisible(false);

      return true;
   }
   @Override
   public boolean onCreateOptionsMenu(Menu menu){
      getMenuInflater().inflate(R.menu.menu_act_transacoes_manutencao, menu);
      return true;
   }
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      switch (id){
         case R.id.mnusalvar:
            salvar();
            return true;
         case R.id.mnuexcluir:
            excluir();
            return true;
         default:
            return super.onOptionsItemSelected(item);
      }
   }

   private void salvar() {
      if (txtvalor.getText().toString().isEmpty()){
         rotinas.msgboxOk(context, "Valor deve ser informado");
         return;
      }

      codconta = lstcontas.get(cmbconta.getSelectedItemPosition()).codigo;
      codcategoria = lstcategorias.get(cmbcategoria.getSelectedItemPosition()).codigo;

      String descricao = txtdescricao.getText().toString().trim();
      if(descricao.equals(""))
         descricao = lstcategorias.get(cmbcategoria.getSelectedItemPosition()).nome;

      BancoSQLite db = new BancoSQLite(context);
      String resultado = db.gravatransacoes(situacao, chave, txtdata.getText().toString(),
            tipodado == TipoDado.entradas ? "E" : "S",
            codconta,
            codcategoria,
            descricao,
            txtvalor.getText().toString(),(chk.isChecked() == true ? "S" : "N"));
      db.close();
      rotinas.logcat( resultado);
      finish();
   }

   private void excluir(){
      BancoSQLite db = new BancoSQLite(context);
      db.deletatransacao(chave);
      db.close();
      finish();
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
      super.onActivityResult(requestCode, resultCode, data);
      rotinas.logcat("result");
      carregacombos();
      //categoria
      if (requestCode == 1 && resultCode == Activity.RESULT_OK){
         cmbcategoria.setSelection(lstcategorias.size()-1,true);
      }
   }

   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }
}
