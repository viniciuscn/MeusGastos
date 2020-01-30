package nichele.meusgastos;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import nichele.meusgastos.backup.CriaBackup;

public class actAjustesPreferencias extends AppCompatActivity {


   Toolbar toolbar;
   Context context;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_ajustes_preferencias);
      overridePendingTransition(R.anim.filho_entrando,R.anim.main_saindo);
      carregatela();
   }

   private void carregatela(){
      toolbar = findViewById(R.id.toolbar);
      toolbar.setTitle("Preferências");

      context = this;
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

   }

   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }
}
