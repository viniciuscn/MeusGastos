package nichele.meusgastos.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import java.math.BigDecimal;

import nichele.meusgastos.R;
import nichele.meusgastos.util.TipoDado;
import nichele.meusgastos.calculadora.DialogCalc;

public class fraTransacoes_Manutencao extends Fragment  {

   TextView txtvalor;
   TextView txtdescricao;

TextView txtdata;

   public fraTransacoes_Manutencao(TipoDado plistar) {
      // Required empty public constructor
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      View view =inflater.inflate(R.layout.fragment_transacoes_manutencao, container, false);
//      Toolbar toolbar =  getActivity().findViewById(R.id.toolbar);
//      toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_close_white));
//      toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            //getActivity().onBackPressed();
//            //getActivity().getFragmentManager().beginTransaction().remove().commit();
//            getActivity().getFragmentManager().popBackStack();
//         }
//      });
//
//      FloatingActionMenu fabmenu = getActivity().findViewById(R.id.fabmenu);
//      fabmenu.setVisibility(View.GONE);
//
//      txtvalor = view.findViewById(R.id.tra_valor);
//      txtvalor.setOnClickListener(new View.OnClickListener() {
//         @Override
//         public void onClick(View v) {
//            abrecalc();
//         }
//      });
//      txtdescricao = view.findViewById(R.id.tra_txtdescricao);
//
//      txtdata = view.findViewById(R.id.lbldata);
//      txtdata.setText(datautil.formatadata(new Date(),"ddd, dd mmm yyyy"));
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
      return view;
   }


   @Nullable
   private BigDecimal value = null;

   public void abrecalc(){
//   final Dialog dialog = new Dialog(DialogCalc.this);
//      dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//      dialog.setContentView(R.layout.dialog_calc);
//      dialog.show();

      Intent intent = new Intent(getContext(), DialogCalc.class);
      startActivityForResult(intent, 0);

//      final CalcDialog calcDialog = new CalcDialog();
//      calcDialog.getSettings().setInitialValue(value);
//      calcDialog.show(getFragmentManager(), "calc_dialog");

   }

   @Override
   public void onStart() {
      super.onStart();

   }

   @Override
   public void onStop() {
      super.onStop();

   }



   //handles


}
