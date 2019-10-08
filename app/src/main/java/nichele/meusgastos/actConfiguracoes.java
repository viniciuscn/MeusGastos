package nichele.meusgastos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import nichele.meusgastos.fragments.fraVisaoGeral;

public class actConfiguracoes extends AppCompatActivity {

   private Toolbar mToolbar;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_configuracoes);
      overridePendingTransition(R.anim.filho_entrando, R.anim.main_saindo);
      //setTheme(R.style.AppTheme);

      mToolbar = findViewById(R.id.toolbar);
      mToolbar.setTitle("Configurações");
      //mToolbar.setSubtitle("subtitulo");
      setSupportActionBar(mToolbar);
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

   }

   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }
}
