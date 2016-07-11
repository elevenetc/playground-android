package su.levenetc.androidplayground.manager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import su.levenetc.androidplayground.utils.LogWriter;
import su.levenetc.androidplayground.utils.ThreadUtils;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class SManager implements SensorEventListener {

	private static final String TAG = SManager.class.getSimpleName();

	private Sensor accelerometer;
	private SensorManager sysSensorManager;
	private BManager bManager;
	private LogWriter logWriter;
	private static final float MAX_DEFLECTION = 0.7f;
	private float[] prevValues = new float[3];
	private static final int BATTERY_LOG_TIME = 1000 * 60 * 5;

	public void init(Context context) {
		logWriter = new LogWriter(TAG, context);
		bManager = new BManager();
		sysSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sysSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		bManager.init(context);

		Log.i(TAG, "Previous logs:");
		Log.i(TAG, logWriter.getLogs());
		Log.i(TAG, "End logs.");

		sysSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, 10 * 1000000);

		initBatteryTimer();
	}

	@Override public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;

		if (hasDeflection(event.values)) logWriter.log(TAG, "deflection");
	}

	@Override public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	private void initBatteryTimer() {
		new Thread(new Runnable() {
			@Override public void run() {
				while (true) {
					float level = bManager.getBattery();
					logWriter.log(TAG, "battery level: " + level);
					ThreadUtils.sleep(BATTERY_LOG_TIME);
				}

			}
		}).start();
	}

	private boolean hasDeflection(float[] fresh) {
		if (prevValues == null) {
			prevValues = fresh;
			return false;
		}

		boolean result = false;

		for (int i = 0; i < fresh.length; i++) {
			float value = fresh[i];
			float diff = value - prevValues[i];
			if (Math.abs(diff) > MAX_DEFLECTION) {
				result = true;
				break;
			}
		}
		prevValues[0] = fresh[0];
		prevValues[1] = fresh[1];
		prevValues[2] = fresh[2];
		return result;
	}
}