package nichele.meusgastos.backup;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.provider.SyncStateContract;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import nichele.meusgastos.MainActivity;
import nichele.meusgastos.R;
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

		//if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
			new CriaBackup(context).executeLocal("");

			//Toast.makeText(context, "Time Up... Now Vibrating !!!", Toast.LENGTH_LONG).show();
			rotinas.logcat("backup");
			Notification(context, "Meus Gastos", "Backup realizado com sucesso.");
			Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(1000);
		//}


	}

	public void Notification(Context context, String titulo, String texto) {
		NotificationManager notificationmanager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		int notificationId = 1;
		String channelId = "channel-01";
		String channelName = "Channel Name";
		int importance = NotificationManager.IMPORTANCE_HIGH;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
			NotificationChannel mChannel = new NotificationChannel(channelId, channelName, importance);
			notificationmanager.createNotificationChannel(mChannel);
		}

//		Intent intent = new Intent(context, NotificationView.class);
		Intent intent = new Intent(context, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

		PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent,				PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId)
				.setSmallIcon(R.mipmap.calendar_2)
				.setContentTitle(titulo)
				.setContentText(texto)
				.setContentIntent(pIntent)
				;

		notificationmanager.notify(0, builder.build());

	}
}
