package nichele.meusgastos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.ddz.floatingactionbutton.FloatingActionButton;
import com.ddz.floatingactionbutton.FloatingActionMenu;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import nichele.meusgastos.fragments.fraTransacoes;
import nichele.meusgastos.fragments.fraVisaoGeral;

public class MainActivity extends AppCompatActivity {

   public Toolbar toolbar;
   private Drawer.Result navigationDrwarerLeft;
   FloatingActionMenu fabmenu;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      toolbar = findViewById(R.id.toolbar);
      toolbar.setTitle("Visão Geral");
      //mToolbar.setSubtitle("subtitulo");

      setSupportActionBar(toolbar);
      AbreFragment(new fraVisaoGeral());

      //AbreFragment(new fraTransacoes_Manutencao());
      fabmenu = findViewById(R.id.fabmenu);
      FloatingActionButton fabrec = findViewById(R.id.fabrec);
      fabrec.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            //AbreFragment(new fraTransacoes_Manutencao(TipoDado.entradas));
            abreactivity("manutencao",TipoDado.entradas);
            fabmenu.collapse();
         }
      });

      FloatingActionButton fabdes = findViewById(R.id.fabdes);
      fabdes.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            //AbreFragment(new fraTransacoes_Manutencao(TipoDado.saidas));
            abreactivity("manutencao", TipoDado.saidas);
            fabmenu.collapse();
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
                          Toast.makeText(getApplicationContext(),"visao geral",Toast.LENGTH_SHORT).show();
                          AbreFragment( new fraVisaoGeral());
                          break;
                       case 1:
                          Toast.makeText(getApplicationContext(),"transações",Toast.LENGTH_SHORT).show();
                          AbreFragment( new fraTransacoes(TipoDado.extrato));
                          break;
//                       case 2:
//                          Toast.makeText(getApplicationContext(),"receitas",Toast.LENGTH_SHORT).show();
//                          AbreFragment( new fraTransacoes(TipoDado.entradas));
//                          //AbreFragment( new fraTransacoes_Manutencao());
//                          break;
//                       case 3:
//                          Toast.makeText(getApplicationContext(),"despesas",Toast.LENGTH_SHORT).show();
//                          AbreFragment( new fraTransacoes(TipoDado.saidas));
//                          //AbreFragment( new fraTransacoes_Manutencao());
//                          break;
                       case 7:
                          abreactivity("configuracoes",TipoDado.nenhum);
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
      //1
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName( "Visão Geral").withIcon(getResources().getDrawable(R.drawable.ic_visaogeral_black_24dp)));
      //2
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Transações").withIcon(getResources().getDrawable(R.drawable.ic_extrato)));

//      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Receitas").withIcon(getResources().getDrawable(R.drawable.ic_up_black)));

//      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Despesas").withIcon(getResources().getDrawable(R.drawable.ic_down_black)));
      //5
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Contas").withIcon(getResources().getDrawable(R.drawable.ic_contas_black_24dp)));
      //6
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Categorias").withIcon(getResources().getDrawable(R.drawable.ic_categorias_black_24dp)));
      //7
      navigationDrwarerLeft.addItem(new PrimaryDrawerItem().withName("Configurações").withIcon(getResources().getDrawable(R.drawable.ic_configacoes_black_24dp)));
   }

   private void AbreFragment(Fragment f){
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

            startActivityForResult(intent, 0);
            break;
         case "configurações":
            intent = new Intent(this, actConfiguracoes.class);
            startActivityForResult(intent, 0);
            break;
      }
   }

   int backButtonCount;
   @Override
   public void onBackPressed()
   {
      fabmenu.setVisibility(View.VISIBLE);
      if(backButtonCount >= 1)
      {
//         Intent intent = new Intent(Intent.ACTION_MAIN);
//         intent.addCategory(Intent.CATEGORY_HOME);
//         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//         startActivity(intent);
         System.exit(0 );
      }
      else
      {
         Toast.makeText(this, "Pressione novamente para sair", Toast.LENGTH_SHORT).show();
         backButtonCount++;
      }
   }
}
