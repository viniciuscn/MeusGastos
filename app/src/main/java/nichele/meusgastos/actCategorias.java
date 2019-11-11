package nichele.meusgastos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.util.rotinas;
import petrov.kristiyan.colorpicker.ColorPicker;

public class actCategorias extends AppCompatActivity {

   Context context; String situacao = "";
   Menu menu;
   Categoria c;
   TextView txtchave;
   int chave;
   TextView txtnome;
   RadioButton optE;
   RadioButton optS;

   ImageButton btncor;


   Toolbar toolbar; AppBarLayout mAppBarLayout; FloatingActionButton fab;

   int cor = Color.RED;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_cadcategorias);
      overridePendingTransition(R.anim.filho_entrando,R.anim.main_saindo);
      Intent intent = getIntent();
      situacao = intent.getStringExtra("situacao").toLowerCase();
      c = rotinas.categoria;
      carregatela();
   }

   private void carregatela(){
      toolbar = findViewById(R.id.toolbar);
      mAppBarLayout = findViewById(R.id.app_bar);
      fab = findViewById(R.id.fab);

      txtchave = findViewById(R.id.cat_chave);
      txtnome = findViewById(R.id.cat_txtnome);
      txtnome.requestFocus();
      optE = findViewById(R.id.cat_optE);
      optS = findViewById(R.id.cat_optS);

      btncor = findViewById(R.id.btncor);

      carregatela2();
   }
   private void carregatela2(){
      context = this;
      setSupportActionBar(toolbar);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            salvar();
         }
      });
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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


      BancoSQLite db = new BancoSQLite(context);

      if (situacao.equals("inc")) {
         chave=db.ultimocodigo("categorias","codcategoria")+1;
      }
      else{
         chave = c.getCodigoInt();
         txtnome.setText(c.getNome());

         if(c.getTipo().equals("E")){
            optE.setChecked(true);
            optS.setEnabled(false);
         }else{
            optS.setChecked(true);
            optE.setEnabled(false);
         }
         //pega cor gravada
         //btncor

      }
      GradientDrawable gradientDrawable = (GradientDrawable) btncor.getBackground().mutate();
      gradientDrawable.setColor(cor);
      db.close();
      txtchave.setText(String.valueOf(chave));

      btncor.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            final ColorPicker colorPicker = new ColorPicker(actCategorias.this);
            colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
               @Override
               public void onChooseColor(int position, int color) {
                  // put code
                  //Toast.makeText(MainActivity.this, "aqui",Toast.LENGTH_SHORT).show();
                  //btn.setColorFilter(color);
                  GradientDrawable gradientDrawable = (GradientDrawable) btncor.getBackground().mutate();
                  gradientDrawable.setColor(color);
                  cor = color;
               }

               @Override
               public void onCancel() {
                  // put code
               }
            })
//						.addListenerButton("newButton", new ColorPicker.OnButtonListener() {
//							@Override
//							public void onClick(View v, int position, int color) {
//								// put code
//							}
//						})
                  //.disableDefaultButtons(true)
                  //Color.parseColor("#f84c44")
                  .setDefaultColorButton(cor)
                  .setRoundColorButton(true)
                  .setColumns(5)
                  //.setDialogFullHeight()
                  .show();
         }
      });
   }

//   @Override
//   public boolean onPrepareOptionsMenu(Menu menu) {
//      super.onPrepareOptionsMenu(menu);
//      menu.findItem(R.id.mnusalvar).setVisible(false);
//      if (situacao.equals("INC"))
//         menu.findItem(R.id.mnuexcluir).setVisible(false);
//
//      return true;
//   }

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
         if (db.aceitar_cadastro("categorias", txtnome.getText().toString()) == false) {
            rotinas.alertCurto(context, "Categoria já cadastrada");
            return;
         }
      }
      String status = db.gravacategoria(situacao, chave, txtnome.getText().toString(), (optE.isChecked() == true ? "E" : "S") );
      rotinas.logcat(status);

      db.close();
      Intent returnIntent = new Intent();
      returnIntent.putExtra("id",chave);
      setResult(Activity.RESULT_OK,returnIntent);
      finish();
   }

   private void excluir() {
      BancoSQLite db = new BancoSQLite(context);
      if (!db.cadastro_em_uso("codcategoria", chave)){
         db.deletacategoria(chave);
         finish();
      }else
         rotinas.msgboxOk(context, "Categoria em uso, não pode ser excluída.");
      db.close();

   }

   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }
}
