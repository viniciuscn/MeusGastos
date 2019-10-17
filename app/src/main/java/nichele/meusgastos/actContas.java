package nichele.meusgastos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.Classes.Conta;
import nichele.meusgastos.util.rotinas;

public class actContas extends AppCompatActivity {

   Context context; String situacao = "";
   Menu menu;
   Conta c;
   TextView txtchave;
   int chave;
   TextView txtnome;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_contas);
      Intent intent = getIntent();
      situacao = intent.getStringExtra("situacao").toLowerCase();
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
            salvar();
         }
      });

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      AppBarLayout mAppBarLayout = findViewById(R.id.app_bar);
      mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
         boolean isShow = false;
         int scrollRange = -1;
         @Override
         public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            if (scrollRange == -1) {
               scrollRange = appBarLayout.getTotalScrollRange();
            }
            if (scrollRange + verticalOffset == 0) {
               isShow = true;
               showOption(R.id.mnusalvar);
            } else if (isShow) {
               isShow = false;
               hideOption(R.id.mnusalvar);
            }
         }
      });
      txtnome = findViewById(R.id.cnt_txtnome);
      BancoSQLite db = new BancoSQLite(context);
      if (situacao.equals("inc")) {
         chave=db.ultimocodigo("contas","codconta")+1;
      }
      else{
         chave = c.getCodigoInt();
         txtnome.setText(c.getNome());
      }
      db.close();
      txtchave.setText(String.valueOf(chave));
   }

   @Override
   public boolean onCreateOptionsMenu(Menu pmenu){
      menu = pmenu;
      getMenuInflater().inflate(R.menu.mnu_crud, menu);
      hideOption(R.id.mnusalvar);
      if (situacao.equals("inc"))
         hideOption(R.id.mnuexcluir);
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
   private void hideOption(int id) {
      MenuItem item = menu.findItem(id);
      item.setVisible(false);
   }
   private void showOption(int id) {
      MenuItem item = menu.findItem(id);
      item.setVisible(true);
   }

   private void salvar(){
      BancoSQLite db = new BancoSQLite(context);
      if (situacao.equals("inc")) {
         if (db.aceitar_cadastro("contas", txtnome.getText().toString()) == false) {
            rotinas.alertCurto(context, "Conta já cadastrada");
            return;
         }
      }
      String status = db.gravaconta(situacao, chave, txtnome.getText().toString());
      rotinas.logcat(status);
      db.close();
      finish();
   }

   private void excluir() {
      BancoSQLite db = new BancoSQLite(context);
      if (!db.cadastro_em_uso("codconta", chave)){
         db.deletaconta(chave);
         finish();
      }else
         rotinas.msgboxOk(context, "Conta em uso, não pode ser excluída.");
      db.close();
   }

   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }
}
