package nichele.meusgastos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import nichele.meusgastos.util.rotinas;

public class actCategorias extends AppCompatActivity {
   Context context;
   String situacao = "";

   TextView txtnome;
   RadioGroup opttipo;
   RadioButton optrec;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_categorias);
      Intent intent = getIntent();
      situacao = intent.getStringExtra("situacao");
      carregatela();
   }

   private void carregatela(){
      context = this;
      Toolbar toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      FloatingActionButton fab = findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            gravar();
         }
      });
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      txtnome = findViewById(R.id.cat_txtnome);
      opttipo = findViewById(R.id.cat_opttipo);
      optrec = findViewById(R.id.cat_optE);

   }

   private void gravar(){

      BancoSQLite db = new BancoSQLite(context);
      if (db.aceitar_cadastro("categorias", txtnome.getText().toString()) == false){
         rotinas.alertCurto(context, "Categoria já cadastrada");
         return;
      }
      String status = db.gravacategoria(situacao, 0, txtnome.getText().toString(), (optrec.isChecked() == true ? "E" : "S") );
      rotinas.logcat(status);

      db.close();
      finish();
   }
}
