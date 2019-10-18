package nichele.meusgastos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class actAjustes extends AppCompatActivity {

   Context context;
   private Toolbar mToolbar;

   LinearLayout con_lay_comecardozero;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_ajustes);
      context=this;
      overridePendingTransition(R.anim.filho_entrando, R.anim.main_saindo);
      //setTheme(R.style.AppTheme);

      mToolbar = findViewById(R.id.toolbar);
      mToolbar.setTitle("Ajustes");
      //mToolbar.setSubtitle("subtitulo");
      setSupportActionBar(mToolbar);
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      carregatela();
   }

   public void carregatela(){
      con_lay_comecardozero = findViewById(R.id.aju_lay_comecardozero);
      con_lay_comecardozero.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            final AlertDialog dialogBuilder = new AlertDialog.Builder(context).create();
            View dialogView = LayoutInflater.from(context).inflate(R.layout.confirma_comecardozero, null);

            final EditText confirma = dialogView.findViewById(R.id.confirma);
            Button btnnao =  dialogView.findViewById(R.id.btnnao);
            Button btnsim = dialogView.findViewById(R.id.btnsim);

            btnsim.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  if (confirma.getText().toString().trim().toLowerCase().equals("recomeçar")) {
                     BancoSQLite db = new BancoSQLite(getApplicationContext());
                     db.zerabanco();
                     db.close();
                     Intent intent = new Intent();
                     setResult(1, intent);
                     dialogBuilder.dismiss();
                     finish();
                  }
               }
            });
            btnnao.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                  // DO SOMETHINGS
                  dialogBuilder.dismiss();
               }
            });

            dialogBuilder.setView(dialogView);
            dialogBuilder.show();
         }
      });
   }

   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }
}
