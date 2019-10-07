package nichele.meusgastos.FiltraPeriodo;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;
import java.util.GregorianCalendar;

import nichele.meusgastos.R;

public class FiltraPeriodo extends Fragment {

   TextView lblmesextenso, lbldatinicial, lbldatfinal;
   public ImageButton cmdant, cmdnext;
   GregorianCalendar gc = new GregorianCalendar();


   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.filtra_periodo, container, false);

      cmdant = view.findViewById(R.id.cmdant);
      lblmesextenso = view.findViewById(R.id.lblmes_extenso);
      cmdnext = view.findViewById(R.id.cmdnext);

      lbldatinicial = view.findViewById(R.id.lbldatinicial);
      lbldatfinal = view.findViewById(R.id.lbldatfinal);

      initialize(getContext());
      return view;
   }



   private void initialize(final Context context){



      lblmesextenso.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            gc = new GregorianCalendar();
            mostradatas();
            //Toast.makeText(context,new Date().toString(),Toast.LENGTH_SHORT).show();
         }
      });

      mostradatas();



      cmdant.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            gc.set(GregorianCalendar.YEAR, Integer.valueOf(lbldatinicial.getText().subSequence(0,4).toString()));
            gc.set(GregorianCalendar.MONTH, Integer.valueOf(lbldatinicial.getText().subSequence(5,7).toString())-1);
            gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(lbldatinicial.getText().subSequence(8,10).toString()));
            gc.setTime( Util.DateAdd( Util.DateInterval.mes,-1, gc.getTime()) );
            mostradatas();

         }
      });


      cmdnext.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

            gc.set(GregorianCalendar.YEAR, Integer.valueOf(lbldatinicial.getText().subSequence(0,4).toString()));
            gc.set(GregorianCalendar.MONTH, Integer.valueOf(lbldatinicial.getText().subSequence(5,7).toString())-1);
            gc.set(GregorianCalendar.DAY_OF_MONTH, Integer.valueOf(lbldatinicial.getText().subSequence(8,10).toString()));
            gc.setTime( Util.DateAdd( Util.DateInterval.mes,1, gc.getTime()) );
            mostradatas();

         }
      });
   }

   private void mostradatas(){
      lbldatinicial.setText( gc.get(Calendar.YEAR) + "/" + String.format("%02d", new Integer(gc.get(Calendar.MONTH)+1 )) + "/01" );
      lbldatfinal.setText( gc.get(Calendar.YEAR) +"/" + String.format("%02d", new Integer(gc.get(Calendar.MONTH)+1 )) +"/" + String.format("%02d", new Integer(gc.getActualMaximum(Calendar.DAY_OF_MONTH))) );
      lblmesextenso.setText(Util.setDataPorExtenso(gc.getTime(),"MMMM, yyyy"));
   }


}
