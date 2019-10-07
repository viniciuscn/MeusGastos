package nichele.meusgastos;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.SparseArray;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;


public class Util {


   private static final Util ourInstance = new Util();

   public static Util getInstance() {
      return ourInstance;
   }

   public Util() {

   }


   public String formatadata(String campo, String formato) {
      if (formato.toLowerCase().equals("yyyy-mm-dd"))
         campo = campo.substring(6, 10) + "-" + campo.substring(3, 5) + "-" + campo.substring(0, 2);
      if (formato.toLowerCase().equals("dd/mm/yyyy"))
         campo = campo.substring(8, 10) + "/" + campo.substring(5, 7) + "/" + campo.substring(0, 4);
      return campo;
   }


   public void EscondeTeclado(Activity c) {
      c.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
   }

   public String formatavalorBR(Float valor) {
      DecimalFormat df = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
      //DecimalFormat df = new DecimalFormat("#.00");
      return df.format(valor);
   }

   public void alertCurto(Context contexto, String mensagem) {
      //duracao 0 = short; 1 long;
      Toast.makeText(contexto, mensagem, Toast.LENGTH_SHORT).show();
   }

   public void alertLongo(Context contexto, String mensagem) {
      //duracao 0 = short; 1 long;
      Toast.makeText(contexto, mensagem, Toast.LENGTH_LONG).show();
   }

   public void animateTextView(float initialValue, float finalValue, final TextView  textview) {
      ValueAnimator valueAnimator = ValueAnimator.ofFloat(initialValue, finalValue);
      valueAnimator.setDuration(1500);
      valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
         @Override
         public void onAnimationUpdate(ValueAnimator valueAnimator) {
            textview.setText(String.format("%.2f",valueAnimator.getAnimatedValue()));
         }
      });
      valueAnimator.start();
   }


}

