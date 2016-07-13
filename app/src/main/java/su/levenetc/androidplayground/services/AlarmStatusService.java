package su.levenetc.androidplayground.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import su.levenetc.androidplayground.di.DIHelper;
import su.levenetc.androidplayground.manager.SManager;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class AlarmStatusService extends Service {

	@Inject SManager sManager;

	@Override public void onCreate() {
		super.onCreate();
		DIHelper.getAlarmAppComponent().inject(this);
		acquireLock();
	}

	@Nullable @Override public IBinder onBind(Intent intent) {
		return null;
	}

	private void acquireLock() {
		PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
		PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getSimpleName() + "Lock");
		wakeLock.acquire();
	}

}
