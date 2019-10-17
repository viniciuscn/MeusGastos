package nichele.meusgastos.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.Classes.Conta;
import nichele.meusgastos.Classes.Transacao;

/**
 * Created by vinicius on 16/06/2016.
 */
public class rotinas {

    public static final String tag = "inspetor";
    public static Locale locale = Locale.getDefault();
    public static Transacao transacao;
    public static Conta conta;
    public static Categoria categoria;

    public static void logcat(String msg){
        Log.v(tag,msg);
    }

    public static String limpacampovalor(String valor){
        //String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(locale).getCurrency().getSymbol());
        String replaceable = String.format("[%s\\s]", NumberFormat.getCurrencyInstance(locale).getCurrency().getSymbol());
        String valorlimpo = valor.replaceAll(replaceable, "");
        rotinas.logcat(valorlimpo);
        return valorlimpo;
    }

    public static float format(String valor){
        return Float.parseFloat(limpacampovalor(valor));
    }

    public static String formatavalorBR(Object valor) {
        DecimalFormat df = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
        //DecimalFormat df = new DecimalFormat("#.00");
        return df.format(Float.parseFloat(valor.toString()));
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

    public static void msgboxOk(Context context, String mensagem) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( context );
        alertDialogBuilder.setMessage(mensagem);
        alertDialogBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        //alertDialog.show();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
