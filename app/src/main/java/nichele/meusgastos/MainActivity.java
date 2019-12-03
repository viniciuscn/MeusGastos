package nichele.meusgastos;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.ddz.floatingactionbutton.FloatingActionButton;
import com.ddz.floatingactionbutton.FloatingActionMenu;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.util.Locale;

import nichele.meusgastos.fragments.fraTransacoes;
import nichele.meusgastos.fragments.fraVisaoGeral;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.util.rotinas;

public class MainActivity extends AppCompatActivity {

   public Toolbar toolbar;
   public Drawer.Result navigationDrwarerLeft;
   public FloatingActionMenu fabmenu;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      PedePermissao();

      rotinas.regiao = new Locale("pt", "BR");

      VerificaPrimeiroAcesso();
      carregatela();
      monta_menu(savedInstanceState);
      monta_adMob();

      criapastabkp();
   }

   private void PedePermissao(){
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
         requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
      }
   }
   @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] garantResults){
//      switch (requestCode){//         case 1000://      }
   }

   private void criapastabkp(){
      try{
         //rotinas.cambkp = Environment.getExternalStorageDirectory() + "/"+ getResources().getString(R.string.app_name);
         File folder = new File(rotinas.cambkp);
         if (!folder.exists()) {
            folder.mkdir();
         }
      } catch (Exception e) {
         e.printStackTrace();
         rotinas.logcat(e.getMessage());
      }
   }

   private void VerificaPrimeiroAcesso(){
      BancoSQLite db = new BancoSQLite(this);
      SharedPreferences sp = getSharedPreferences(rotinas.cfg, Context.MODE_PRIVATE);
      String firstopen = sp.getString(rotinas.cfg_keyfirstopen, "S");
      if (firstopen == "S"){
         db.zerabanco();
         SharedPreferences.Editor editor = sp.edit();
         editor.putString(rotinas.cfg_keyfirstopen,"N");
         editor.apply();

         editor = sp.edit();
         editor.putBoolean(rotinas.cfg_keybkpativo,false);
         editor.apply();
      }
      db.close();
   }

   private void carregatela(){
      toolbar = findViewById(R.id.toolbar);
      toolbar.setTitle("Visão Geral");
      setSupportActionBar(toolbar);
      abrefragment(new fraVisaoGeral());
      fabmenu = findViewById(R.id.fabmenu);
      FloatingActionButton fabrec = findViewById(R.id.fabrec);
      fabrec.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            abreactivity("manutencao", TipoDado.entradas);
            fabmenu.collapse();
         }
      });
      FloatingActionButton fabdes = findViewById(R.id.fabdes);
      fabdes.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            //abrefragment(new fraTransacoes_Manutencao(TipoDado.saidas));
            abreactivity("manutencao", TipoDado.saidas);
            fabmenu.collapse();
         }
      });
   }

   private void monta_menu(Bundle savedInstanceState){
      navigationDrwarerLeft =new Drawer()
            .withActivity(this)
            .withToolbar(toolbar)
            .withDisplayBelowToolbar(true)
            .withActionBarDrawerToggleAnimated(true)
            .withDrawerGravity(Gravity.LEFT)
            .withSavedInstance(savedInstanceState)
            .withSelectedItem(0)
            .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
               @Override
               public void onItemClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                  switch(position){
                     case 0:
//                          Toast.makeText(getApplicationContext(),"visao geral",Toast.LENGTH_SHORT).show();
                        abrefragment( new fraVisaoGeral());
                        break;
                     case 1:
//                          Toast.makeText(getApplicationContext(),"transações",Toast.LENGTH_SHORT).show();
                        abrefragment( new fraTransacoes(TipoDado.extrato));
                        break;
                     case 2:
//                          Toast.makeText(getApplicationContext(),"receitas",Toast.LENGTH_SHORT).show();
                        abrefragment( new fraTransacoes(TipoDado.entradas));
                        break;
                     case 3:
//                          Toast.makeText(getApplicationContext(),"despesas",Toast.LENGTH_SHORT).show();
                        abrefragment( new fraTransacoes(TipoDado.saidas));
                        break;
                     case 4:
                        abreactivity("contas",TipoDado.nenhum);
                        break;
                     case 5:
                        abreactivity("categorias",TipoDado.nenhum);
                        break;
                     case 6:
                        abreactivity("ajustes",TipoDado.nenhum);
                        break;
                  }
               }
            })
            .withOnDrawerItemLongClickListener(new Drawer.OnDrawerItemLongClickListener() {
               @Override
               public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id, IDrawerItem drawerItem) {
                  Toast.makeText(MainActivity.this  ,"onItemLongClick",Toast.LENGTH_SHORT).show();
                  return false;
               }
            })
            .build();
      //0
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName( "Visão Geral").withIcon(getResources().getDrawable(R.drawable.menu_visaogeral)));
      //1
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Transações").withIcon(getResources().getDrawable(R.drawable.ic_extrato)));
      //2
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Receitas").withIcon(getResources().getDrawable(R.drawable.menu_receitas)));
      //3
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Despesas").withIcon(getResources().getDrawable(R.drawable.menu_despesas)));
      //4
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Contas").withIcon(getResources().getDrawable(R.drawable.menu_contas)));
      //5
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Categorias").withIcon(getResources().getDrawable(R.drawable.menu_categorias)));
      //6
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Ajustes").withIcon(getResources().getDrawable(R.drawable.menu_ajustes)));
   }

   private void monta_adMob(){
      MobileAds.initialize(this, new OnInitializationCompleteListener() {
         @Override
         public void onInitializationComplete(InitializationStatus initializationStatus) {
         }
      });
      AdView mAdView = findViewById(R.id.adView);
      AdRequest adRequest = new AdRequest.Builder().build();
      mAdView.loadAd(adRequest);
   }

   public void abrefragment(Fragment f){
      FragmentManager fm = getSupportFragmentManager();
      FragmentTransaction ft = fm.beginTransaction();
      ft.replace(R.id.frameLayout, f);
      ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
      ft.commit();
   }

   private void abreactivity(String tela, TipoDado tipdado) {
      Intent intent;
      switch (tela){
         case "manutencao":
            intent = new Intent(this, actTransacoes_Manutencao.class);
            intent.putExtra("tipdado", tipdado);
            intent.putExtra("situacao", "INC");
            startActivityForResult(intent, 0);
            break;
         case "contas":
            //intent = new Intent(this, actContas.class);
            intent = new Intent(this, actLisContas.class);
            //intent.putExtra("situacao", "INC");
            startActivityForResult(intent, 0);
            break;
         case "categorias":
            //intent = new Intent(this, actCategorias.class);
            intent = new Intent(this, actLisCategorias.class);
            //intent.putExtra("situacao", "INC");
            startActivityForResult(intent, 0);

            break;
         case "ajustes":
            intent = new Intent(this, actAjustes.class);
            startActivityForResult(intent, 1);
            break;
      }
   }

   boolean mostrar_visaogeral=false;

   @Override
   public void onResume() {
      super.onResume();

      if(mostrar_visaogeral){
         mostrar_visaogeral=false;
         abrefragment(new fraVisaoGeral());
      }
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode==1 && resultCode == 1){
         mostrar_visaogeral = true;
      }else if(requestCode==2){
//         Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//         handleSignInResult(task);
      }
   }

//   private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
//      try {
//         account = completedTask.getResult(ApiException.class);
//         // Signed in successfully, show authenticated UI.
//         //updateUI(account);
//      } catch (ApiException e) {
//         rotinas.alertCurto(this,"erro");
//         // The ApiException status code indicates the detailed failure reason.
//         // Please refer to the GoogleSignInStatusCodes class reference for more information.
//         Log.w(rotinas.tag, "signInResult:failed code=" + e.getStatusCode());
//         //updateUI(null);
//      }
//   }



   int backButtonCount;
   @Override
   public void onBackPressed() {
      fabmenu.setVisibility(View.VISIBLE);
      if (backButtonCount >= 1) {
//         Intent intent = new Intent(Intent.ACTION_MAIN);
//         intent.addCategory(Intent.CATEGORY_HOME);
//         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//         startActivity(intent);
         System.exit(0);
      } else {
         rotinas.logcat( getSupportFragmentManager().findFragmentById(R.id.frameLayout).getClass().getSimpleName());
         if(!getSupportFragmentManager().findFragmentById(R.id.frameLayout).getClass().getSimpleName().equals("fraVisaoGeral")){
            abrefragment(new fraVisaoGeral());
         }else
            Toast.makeText(this, "Pressione novamente para sair", Toast.LENGTH_SHORT).show();
      }
      backButtonCount++;
   }
}