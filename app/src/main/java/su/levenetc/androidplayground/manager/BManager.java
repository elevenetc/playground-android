package su.levenetc.androidplayground.manager;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class BManager {

	private Intent batteryStatus;

	public void init(Context context) {
		IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		batteryStatus = context.registerReceiver(null, filter);
	}

	public float getBattery() {
		int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
		int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
		return level / (float) scale;
	}
}
