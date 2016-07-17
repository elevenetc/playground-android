package su.levenetc.androidplayground.manager;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import rx.Observable;
import rx.subjects.PublishSubject;
import su.levenetc.androidplayground.models.SManagerState;
import su.levenetc.androidplayground.utils.LogWriter;
import su.levenetc.androidplayground.utils.RotationSensorsHandler;
import su.levenetc.androidplayground.utils.ThreadUtils;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class SManager {

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
	private RotationSensorsHandler sensorsHandler = new RotationSensorsHandler();

	public void onCreate(Context context) {
		logWriter = new LogWriter(TAG, context);
		bManager = new BManager();
		sysSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		accelerometer = sysSensorManager.getDefaultSensor(SENSOR_TYPE);
		bManager.init(context);

		sensorsHandler.onCreate(context, 0);
		sensorsHandler.setDataHandler(new RotationSensorsHandler.Handler() {
			@Override public void handleYawn(float value) {
				state.x = value;
				updateState();
			}

			@Override public void handlePitch(float value) {
				state.y = value;
				updateState();
			}

			@Override public void handleRoll(float value) {
				state.z = value;
				updateState();
			}

			@Override public void onError(Throwable t) {
				t.printStackTrace();
			}

			private void updateState() {
				statePublishSubject.onNext(state);
			}
		});
		sensorsHandler.onResume();

		initBatteryTimer();
	}

	public Observable<SManagerState> getStateObservable() {
		return statePublishSubject;
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