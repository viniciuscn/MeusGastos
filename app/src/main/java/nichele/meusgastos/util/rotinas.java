package nichele.meusgastos.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by vinicius on 16/06/2016.
 */
public class rotinas {

    public static final String tag = "inspetor";

    public float format(String valor){
        return Float.parseFloat(valor.replaceAll(",","."));
    }

    public String formatavalorBR(Float valor) {
        DecimalFormat df = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        //DecimalFormat df = new DecimalFormat("#.00");
        return df.format(valor);
    }

    public void EscondeTeclado(Activity c) {
        c.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    public static void alertCurto(Context contexto, String mensagem) {
        //duracao 0 = short; 1 long;
        Toast.makeText(contexto, mensagem, Toast.LENGTH_SHORT).show();
    }

    public static void alertLongo(Context contexto, String mensagem) {
        //duracao 0 = short; 1 long;
        Toast.makeText(contexto, mensagem, Toast.LENGTH_LONG).show();
    }

    public static void animateTextView(float initialValue, float finalValue, final TextView textview) {
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
