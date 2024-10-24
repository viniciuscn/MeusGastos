package nichele.meusgastos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

//import com.ddz.floatingactionbutton.FloatingActionButton;
//import com.ddz.floatingactionbutton.FloatingActionMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.w9jds.FloatingActionMenu;

import java.util.ArrayList;
import java.util.Locale;

import nichele.meusgastos.fragments.fraTransacoes;
import nichele.meusgastos.fragments.fraVisaoGeral;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.util.rotinas;

public class MainActivity extends AppCompatActivity {

   public Toolbar toolbar;
   public Drawer.Result navigationDrwarerLeft;
   public FloatingActionMenu fabmenu;

   private ArrayList<String> smsList = new ArrayList<>();
   private ListView listView;
   private static final int READ_SMS_PERMISSION_CODE = 1;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      rotinas.regiao = new Locale("pt", "BR");

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
      //rotinas.startAlertAtParticularTime(this);

      toolbar = findViewById(R.id.toolbar);
      toolbar.setTitle("Visão Geral");
      //mToolbar.setSubtitle("subtitulo");

      setSupportActionBar(toolbar);

      abrefragment(new fraVisaoGeral());
      //abreactivity("categorias",TipoDado.nenhum);

      //abrefragment(new fraTransacoes_Manutencao());
      fabmenu = findViewById(R.id.fabmenu);
      FloatingActionButton fabrec = findViewById(R.id.fabrec);
      fabrec.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            abreactivity("manutencao", TipoDado.entradas);
        //    fabmenu.collapse();
            fabmenu.close();
         }
      });

      FloatingActionButton fabdes = findViewById(R.id.fabdes);
      fabdes.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            //abrefragment(new fraTransacoes_Manutencao(TipoDado.saidas));
            abreactivity("manutencao", TipoDado.saidas);
            //fabmenu.collapse();
            fabmenu.close();
         }
      });

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
      //comentado para publicar e atender o prazo de atualização do app
      //navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Resumido por conta").withIcon(getResources().getDrawable(R.drawable.menu_ajustes)));
      //navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Resumido por categoria").withIcon(getResources().getDrawable(R.drawable.menu_ajustes)));



      if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
         ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_SMS}, READ_SMS_PERMISSION_CODE);
      } else {
         readSms();
      }

      //listView = findViewById(R.id.listView);
      //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, smsList);
      //listView.setAdapter(adapter);

   }

   private void readSms() {
      ContentResolver contentResolver = getContentResolver();
      Cursor cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            null,
            null,
            null,
            null);

      if (cursor != null && cursor.moveToFirst()) {
         do {
            String address = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
            String body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
            smsList.add("Sender: " + address + "\nMessage: " + body);
         } while (cursor.moveToNext());
      }

      if (cursor != null) {
         cursor.close();
      }
   }

//   @Override
//   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                          @NonNull int[] grantResults) {
//      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//      if (requestCode == READ_SMS_PERMISSION_CODE) {
//         if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            readSms();
//            ArrayAdapter<String> adapter = (ArrayAdapter<String>) listView.getAdapter();
//            adapter.notifyDataSetChanged();
//         }
//      }
//   }

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
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode==1 && resultCode == 1)
         mostrar_visaogeral = true;
      // abrefragment(new fraVisaoGeral());
//      navigationDrwarerLeft.setSelection(0);
//      if (requestCode == 1) {
//         if(resultCode == Activity.RESULT_OK){
//            String result=data.getStringExtra("result");
//         }
//         if (resultCode == Activity.RESULT_CANCELED) {
//            //Write your code if there's no result
//         }
//      }
   }//onAct



   int backButtonCount;
   @Override
   public void onBackPressed() {
      //fabmenu.setVisibility(View.VISIBLE);
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