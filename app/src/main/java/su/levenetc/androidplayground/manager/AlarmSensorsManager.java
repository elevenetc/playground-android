package su.levenetc.androidplayground.manager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class AlarmSensorsManager implements SensorEventListener {

	private Sensor accelerometer;
	private SensorManager sysSensorManager;
	private BManager bManager;

	public void init(Context context) {
		bManager = new BManager();
		sysSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sysSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

		sysSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, 10 * 1000000);
		bManager.init(context);
	}

	@Override public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) return;
		float axisX = event.values[0];
		float axisY = event.values[1];
		float axisZ = event.values[2];
		Log.i("alarm-sens", axisX + ":" + axisY + ":" + axisZ + ":battery:" + bManager.getBattery());
	}

	@Override public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}
}