package nichele.meusgastos;

import android.os.Bundle;

import com.ddz.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.View;

import nichele.meusgastos.fragments.TabsPagerAdapter;

public class actLisCategorias extends AppCompatActivity {

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_lis_categorias);
      overridePendingTransition(R.anim.filho_entrando, R.anim.main_saindo);
      Toolbar toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);

      FloatingActionButton fab = findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
         }
      });
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);


      //@SuppressLint("WrongViewCast")
      TabLayout tabs = findViewById(R.id.tabs);
      ViewPager pager = (ViewPager) findViewById(R.id.pager);
      TabsPagerAdapter adapter = new TabsPagerAdapter(getSupportFragmentManager(), getResources().getStringArray(R.array.categorias));

      pager.setAdapter(adapter);
      tabs.setupWithViewPager(pager);
   }

   @Override
   public void finish() {
      super.finish();
      overridePendingTransition(R.anim.main_entrando, R.anim.filho_saindo);
   }

}
