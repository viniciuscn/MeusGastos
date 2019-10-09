package nichele.meusgastos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nichele.meusgastos.util.rotinas;

public class actContas extends AppCompatActivity {
   Context context;
   String situacao = "";

   TextView txtnome;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_contas);
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

      txtnome = findViewById(R.id.cnt_txtnome);


   }

   private void gravar(){
      BancoSQLite db = new BancoSQLite(context);
      String status = db.gravaconta(situacao, 0, txtnome.getText().toString());
      Log.v(rotinas.tag,status);
      db.close();
      finish();
   }
}
