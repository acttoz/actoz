package kr.co.moon.speedalim;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
			// Device 부팅시 일로 들어오므로 이곳에서 원하는 작업을 구현한다.
			AlarmManager mManager = (AlarmManager) context
					.getSystemService(Context.ALARM_SERVICE);
			Intent intent1 = new Intent(context, AlarmReceiver.class);

			PendingIntent sender = PendingIntent.getBroadcast(context, 0,
					intent1, 0);
			mManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
					SystemClock.elapsedRealtime(), 1000 * 60 * 120, sender);
		}
	}

}
