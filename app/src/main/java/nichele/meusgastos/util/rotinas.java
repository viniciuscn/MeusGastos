package nichele.meusgastos.util;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import nichele.meusgastos.Classes.Categoria;
import nichele.meusgastos.Classes.Conta;
import nichele.meusgastos.Classes.Transacao;
import nichele.meusgastos.R;
import nichele.meusgastos.backup.AlarmReceiver;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by vinicius on 16/06/2016.
 */
public class rotinas {

	public static String cambkp =  Environment.getExternalStorageDirectory() + "/Meus Gastos";
	public static String cfg = "mg.cfg";
	public static String cfg_keyfirstopen = "firstopen";
	public static String cfg_keybkpativo = "bkp_ativo";
	public static String cfg_keyhorabkp = "hora_bkp";

	public static final String tag = "inspetor";
	public static Locale regiao = Locale.getDefault();
	public static Transacao transacao;
	public static Conta conta;
	public static Categoria categoria;


	public static void logcat(Object msg){
		Log.v(tag, String.valueOf(msg));
	}

	public static String limpacampovalor(String valor){
		//String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(regiao).getCurrency().getSymbol());
		String replaceable = String.format("[%s,\\s]", NumberFormat.getCurrencyInstance(regiao).getCurrency().getSymbol());
		String valorlimpo = valor.replaceAll(replaceable, "");
		rotinas.logcat(valorlimpo);
		return valorlimpo;
	}

	public static float format(String valor){
		return Float.parseFloat(limpacampovalor(valor));
//		logcat("valor recebido " + valor);
//		String vlralt = valor.replace(",",".");
//		logcat("valor alterado " + vlralt);
//		return Float.parseFloat(vlralt);
	}

	public static String formatvalor_edicao(String valor){
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(Float.parseFloat(valor));
		//return String.format(Float.parseFloat(limpacampovalor(valor)));
		//return String.format("%.2f", Float.parseFloat(valor.replace(".","").replace(",",".")));
		//return String.format("%.2f", Float.parseFloat(valor));

	}

	public static String formatavalorBR(Object valor) {
		DecimalFormat df = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
		//DecimalFormat df = new DecimalFormat("#.00");
		return df.format(Float.parseFloat(valor.toString()));
	}

	public static void setColorCampoValor(Context context, TextView campoValor) {
		if (format(campoValor.getText().toString()) < 0)
			campoValor.setTextColor( context.getResources().getColor(R.color.vermelho) );
		else
			campoValor.setTextColor( context.getResources().getColor(R.color.verde) );

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

	public static void startAlertAtParticularTime(Context context) {
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);

		Intent intent = new Intent(context, AlarmReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 34, intent, 0);


		boolean rodar_bkp = context.getSharedPreferences(rotinas.cfg, Context.MODE_PRIVATE).getBoolean(rotinas.cfg_keybkpativo, false);
		if (rodar_bkp == true) {
			String shora = context.getSharedPreferences(rotinas.cfg, Context.MODE_PRIVATE).getString(rotinas.cfg_keyhorabkp, "");
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(shora.substring(0, 2)));
			calendar.set(Calendar.MINUTE, Integer.parseInt(shora.substring(3, 5)));
			//alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);
			alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
		}else{
			try{
				alarmManager.cancel(pendingIntent);
			}catch (Exception e){
				logcat(e.getMessage());
			}
		}
	}
}
