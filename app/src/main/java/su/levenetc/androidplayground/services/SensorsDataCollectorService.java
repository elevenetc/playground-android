package su.levenetc.androidplayground.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import su.levenetc.androidplayground.utils.FloatArraySet;
import su.levenetc.androidplayground.utils.Out;
import su.levenetc.androidplayground.utils.RotationSensorsHandler;

/**
 * Created by Eugene Levenetc on 17/07/2016.
 */
public class SensorsDataCollectorService extends Service implements RotationSensorsHandler.Handler {

	private static final String TAG = SensorsDataCollectorService.class.getSimpleName();

	private RotationSensorsHandler rotationSensorsHandler = new RotationSensorsHandler();
	private static final long COLLECTING_DATA_HANDLER = 3000;
	private FloatArraySet yawnData = new FloatArraySet();
	private FloatArraySet pitchData = new FloatArraySet();
	private FloatArraySet rollData = new FloatArraySet();

	public static void start(Context context) {
		context.startService(new Intent(context, SensorsDataCollectorService.class));
	}

	@Override public void onCreate() {
		super.onCreate();
		rotationSensorsHandler.setDataHandler(this);
		rotationSensorsHandler.onCreate(this);
		rotationSensorsHandler.onResume();
		new Timer().schedule(new TimerTask() {
			@Override public void run() {
				deliverResultsAndStop();
			}
		}, COLLECTING_DATA_HANDLER);
	}

	@Nullable @Override public IBinder onBind(Intent intent) {
		return null;
	}

	@Override public void handleYawn(float value) {
		yawnData.add(value);
	}

	@Override public void handlePitch(float value) {
		pitchData.add(value);
	}

	@Override public void handleRoll(float value) {
		rollData.add(value);
	}

	@Override public void onError(Throwable t) {

	}

	private void deliverResultsAndStop() {

		rotationSensorsHandler.onStop();
		rotationSensorsHandler.onDestroy();

		ParamResult yawnResult = new ParamResult(yawnData);
		ParamResult pitchResult = new ParamResult(pitchData);
		ParamResult rollResult = new ParamResult(rollData);

		Out.pln("yawn", yawnResult.getAverage() + ":" + yawnResult.getSize());
		Out.pln("pitch", pitchResult.getAverage() + ":" + pitchResult.getSize());
		Out.pln("roll", rollResult.getAverage() + ":" + rollResult.getSize());

		stopSelf();
	}

	private static class ParamResult {

		private FloatArraySet set;
		private float total;

		public ParamResult(FloatArraySet set) {
			this.set = set;
			compute();
		}

		private void compute() {
			float[] all = set.getAllItems();
			for (float v : all) total += v;
		}

		private float getAverage() {
			return total / set.size();
		}

		private float getSize() {
			return set.size();
		}
	}
}
