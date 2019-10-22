package nichele.meusgastos.backup;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.widget.Toast;

import nichele.meusgastos.util.rotinas;

public class AlarmReceiver extends BroadcastReceiver {
//	@Override
//	public void onReceive(Context context, Intent intent) {
//		// For our recurring task, we'll just display a message
//		Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
//		rotinas.logcat("backup");
//	}

	@Override
	public void onReceive(Context context, Intent intent) {
		//Toast.makeText(context, "Time Up... Now Vibrating !!!", Toast.LENGTH_LONG).show();
		rotinas.logcat("backup");
		Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		vibrator.vibrate(1000);
	}

}
