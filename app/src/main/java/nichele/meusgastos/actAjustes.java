package nichele.meusgastos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class actAjustes extends AppCompatActivity {

   private Toolbar mToolbar;

   LinearLayout con_lay_comecardozero;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_ajustes);
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
            BancoSQLite db = new BancoSQLite(getApplicationContext());
            db.zerabanco();
            db.close();
            Intent intent=new Intent();
            setResult(1,intent);
            finish();
         }
      });
   }

   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }
}
