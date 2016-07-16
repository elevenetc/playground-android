package su.levenetc.androidplayground.manager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import rx.Observable;
import rx.subjects.PublishSubject;
import su.levenetc.androidplayground.models.SManagerState;
import su.levenetc.androidplayground.utils.LogWriter;
import su.levenetc.androidplayground.utils.ThreadUtils;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class SManager implements SensorEventListener {

	private static final String TAG = SManager.class.getSimpleName();
	private static final boolean CHECK_DEFLECTION = false;

	private static final int SENSOR_TYPE = Sensor.TYPE_ROTATION_VECTOR;
	private Sensor accelerometer;
	private SensorManager sysSensorManager;
	private BManager bManager;
	private LogWriter logWriter;
	private static final float MAX_DEFLECTION = 0.7f;
	private float[] prevValues = new float[3];
	private static final int BATTERY_LOG_TIME = 1000 * 60 * 5;
	private PublishSubject<SManagerState> statePublishSubject = PublishSubject.create();
	private SManagerState state = new SManagerState();
	private final float[] orientationValues = new float[3];
	private final float[] rotationMatrix = new float[9];
	private final float[] rotationVectorFromMatrix = new float[9];

	public void init(Context context) {
		logWriter = new LogWriter(TAG, context);
		bManager = new BManager();
		sysSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sysSensorManager.getDefaultSensor(SENSOR_TYPE);
		bManager.init(context);

		Log.i(TAG, "Previous logs:");
		Log.i(TAG, logWriter.getLogs());
		Log.i(TAG, "End logs.");

		sysSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, 10 * 1000000);

		initBatteryTimer();
	}

	public Observable<SManagerState> getStateObservable() {
		return statePublishSubject;
	}

	@Override public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != SENSOR_TYPE) return;
		boolean update = CHECK_DEFLECTION ? hasDeflection(event.values) : true;

		if (update) {

			SensorManager.getRotationMatrixFromVector(rotationVectorFromMatrix, event.values);
			SensorManager.remapCoordinateSystem(rotationVectorFromMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, rotationMatrix);
			SensorManager.getOrientation(rotationMatrix, orientationValues);
			
			orientationValues[0] = (float) Math.toDegrees(orientationValues[0]);
			orientationValues[1] = (float) Math.toDegrees(orientationValues[1]);
			orientationValues[2] = (float) Math.toDegrees(orientationValues[2]);

			state.x = orientationValues[0];
			state.y = orientationValues[1];
			state.z = orientationValues[2];
			statePublishSubject.onNext(state);
		}
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