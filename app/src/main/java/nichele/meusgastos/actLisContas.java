package nichele.meusgastos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

//import com.ddz.floatingactionbutton.FloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.Classes.Conta;
import nichele.meusgastos.adapters.CategoriaArrayAdapter;
import nichele.meusgastos.adapters.ContasArrayAdapter;
import nichele.meusgastos.fragments.TabsPagerAdapter;

public class actLisContas extends AppCompatActivity {

   Context context;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lis_contas);
      overridePendingTransition(R.anim.filho_entrando, R.anim.main_saindo);
      Toolbar toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      context=this;

      FloatingActionButton fab = findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            Intent intent = new Intent(context, actContas.class );
            //intent.putExtra("tipdado", (categorias.get(position).funcao.equals("E") ? TipoDado.entradas : TipoDado.saidas) );
            intent.putExtra("situacao", "INC");
            startActivity(intent);

         }
      });
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      mostradados();
   }

   public void mostradados(){
      BancoSQLite db = new BancoSQLite(context);
      ArrayList<Conta> cnts = db.listacontas();
      db.close();
      ContasArrayAdapter rst = new ContasArrayAdapter(cnts);
      RecyclerView recyclerView = findViewById(R.id.lista_dados);
      recyclerView.setAdapter(rst);
      recyclerView.setLayoutManager(new LinearLayoutManager( context));
      recyclerView.setItemAnimator(new DefaultItemAnimator());
   }

   @Override
   protected void onResume() {
      super.onResume();
      mostradados();
   }

   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }

}
