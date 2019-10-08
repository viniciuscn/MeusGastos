package nichele.meusgastos;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.ddz.floatingactionbutton.FloatingActionMenu;
import com.maltaisn.calcdialog.CalcDialog;

import java.math.BigDecimal;
import java.util.Date;

import nichele.meusgastos.BancoSQLite;
import nichele.meusgastos.FiltraPeriodo.Util;
import nichele.meusgastos.R;
import nichele.meusgastos.TipoDado;
import nichele.meusgastos.calculadora.DialogCalc;

public class actTransacoes_Manutencao extends AppCompatActivity  {

   TipoDado tipodado;

   TextView txtvalor;
   TextView txtdescricao;
TextView txtdata;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_transacoes_manutencao);
      carregatela();

      Intent intent = getIntent();
      tipodado = (TipoDado)intent.getSerializableExtra("tipdado");


//

//      txtvalor.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            abrecalc();
//         }
//      });
//
//
//
//      txtdata.setText(Util.setDataPorExtenso(new Date(),"ddd, dd mmm yyyy"));
//
//      Button cmdsalvar = view.findViewById(R.id.cmdsalvar);
//      cmdsalvar.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            BancoSQLite db = new BancoSQLite(getContext());
//            String resultado = db.gravatransacoes("INC", 0, "", "ENT",
//                    1,
//                    1,
//                    txtdescricao.getText().toString(),
//                    txtvalor.getText().toString());
//            db.close();
//            Toast.makeText(getContext(),resultado,Toast.LENGTH_SHORT).show();
//         }
//      });

   }

   private void carregatela(){
      Toolbar toolbar =  findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      txtvalor = findViewById(R.id.man_txtvalor);
      txtdescricao = findViewById(R.id.man_txtdescricao);
      txtdata = findViewById(R.id.man_txtdata);
   }
}
