package su.levenetc.androidplayground.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.annotation.Nullable;
import android.view.Surface;

import java.text.NumberFormat;


/**
 * Created by Eugene Levenetc on 16/07/2016.
 */
public class RotationSensorsHandler implements SensorEventListener {

	private SensorManager sensorManager;
	private final float[] gData = new float[3];// Gravity or accelerometer
	private final float[] mData = new float[3];// Magnetometer
	private final float[] orientation = new float[3];
	private final float[] rotationMatrix = new float[9];
	private final float[] copyRotationMatrix = new float[9];
	private final float[] iMat = new float[9];
	private final NumberFormat numberFormat = NumberFormat.getInstance();
	private boolean haveGravity;
	private boolean haveAccelerometer;
	private boolean haveMagnetometer;
	private @Nullable Handler handler;
	private int displayRotation;
	private float maxYaw;
	private float minYaw;
	private boolean hasRotationVector;

	public void onCreate(Context context, int displayRotation) {
		this.displayRotation = displayRotation;
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		numberFormat.setMaximumFractionDigits(2);
		hasRotationVector = SystemUtils.hasSensor(Sensor.TYPE_ROTATION_VECTOR, context);
	}

	public void onResume() {
		final int sensorDelay = SensorManager.SENSOR_DELAY_FASTEST;

		if (hasRotationVector) {
			sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), sensorDelay);
		} else {
			Sensor gSensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
			Sensor aSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
			Sensor mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
			sensorManager.registerListener(this, gSensor, sensorDelay);
			sensorManager.registerListener(this, aSensor, sensorDelay);
			sensorManager.registerListener(this, mSensor, sensorDelay);
		}
	}

	public void onStop() {
		sensorManager.unregisterListener(this);
	}

	public void onDestroy() {
		sensorManager = null;
	}

	public void onSensorChanged(SensorEvent event) {

		switch (event.sensor.getType()) {
			case Sensor.TYPE_ROTATION_VECTOR:
				gData[0] = event.values[0];
				gData[1] = event.values[1];
				gData[2] = event.values[2];
				break;
			case Sensor.TYPE_GRAVITY:
				gData[0] = event.values[0];
				gData[1] = event.values[1];
				gData[2] = event.values[2];
				haveGravity = true;
				break;
			case Sensor.TYPE_ACCELEROMETER:
				if (haveGravity) break;
				gData[0] = event.values[0];
				gData[1] = event.values[1];
				gData[2] = event.values[2];
				haveAccelerometer = true;
				break;
			case Sensor.TYPE_MAGNETIC_FIELD:
				mData[0] = event.values[0];
				mData[1] = event.values[1];
				mData[2] = event.values[2];
				haveMagnetometer = true;
				break;
			default:
				return;
		}

		final float DEG = (float) (360f / (2f * Math.PI));

		if (hasRotationVector) {

			getRotationMatrixFromVector(rotationMatrix, event.values);
			SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_X, SensorManager.AXIS_Z, copyRotationMatrix);
			SensorManager.getOrientation(copyRotationMatrix, orientation);

			orientation[0] = (float) Math.toDegrees(orientation[0]);//yawn
			orientation[1] = (float) Math.toDegrees(orientation[1]);//pitch
			orientation[2] = (float) Math.toDegrees(orientation[2]);//roll

			if (handler != null) {
				handler.handleYawn(orientation[0]);
				handler.handlePitch(orientation[1]);
				handler.handleRoll(orientation[2]);
			}

		} else {
			if ((haveGravity || haveAccelerometer) && haveMagnetometer) {
				SensorManager.getRotationMatrix(rotationMatrix, iMat, gData, mData);

				if (displayRotation == Surface.ROTATION_270)
					SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_MINUS_Y, SensorManager.AXIS_X, copyRotationMatrix);
				else
					SensorManager.remapCoordinateSystem(rotationMatrix, SensorManager.AXIS_Y, SensorManager.AXIS_MINUS_X, copyRotationMatrix);

				SensorManager.getOrientation(copyRotationMatrix, orientation);

				float yaw = orientation[0] * DEG + 180;
				float roll = (float) Math.toDegrees(orientation[1]);
				float pitch = orientation[2] * DEG;

				if (yaw < minYaw) minYaw = yaw;
				if (yaw > maxYaw) maxYaw = yaw;

				if (handler != null) {
					handler.handleYawn(yaw);
					handler.handleRoll(roll);
					handler.handlePitch(pitch);
				}
			} else {
				if (handler != null)
					handler.onError(new RuntimeException("Sensors are not available"));
			}
		}
	}

	@Override public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	public void setDataHandler(Handler handler) {
		this.handler = handler;
	}

	/**
	 * Copy from {@link android.hardware.SensorManager#getRotationMatrixFromVector}
	 * On some Samsung devices with 4.3 method throws exception
	 * https://groups.google.com/forum/#!topic/android-developers/U3N9eL5BcJk
	 * https://github.com/nvanbenschoten/motion/issues/16
	 */
	private static void getRotationMatrixFromVector(float[] R, float[] rotationVector) {

		float q0;
		float q1 = rotationVector[0];
		float q2 = rotationVector[1];
		float q3 = rotationVector[2];

		if (rotationVector.length >= 4) {
			q0 = rotationVector[3];
		} else {
			q0 = 1 - q1 * q1 - q2 * q2 - q3 * q3;
			q0 = (q0 > 0) ? (float) Math.sqrt(q0) : 0;
		}

		float sq_q1 = 2 * q1 * q1;
		float sq_q2 = 2 * q2 * q2;
		float sq_q3 = 2 * q3 * q3;
		float q1_q2 = 2 * q1 * q2;
		float q3_q0 = 2 * q3 * q0;
		float q1_q3 = 2 * q1 * q3;
		float q2_q0 = 2 * q2 * q0;
		float q2_q3 = 2 * q2 * q3;
		float q1_q0 = 2 * q1 * q0;

		if (R.length == 9) {
			R[0] = 1 - sq_q2 - sq_q3;
			R[1] = q1_q2 - q3_q0;
			R[2] = q1_q3 + q2_q0;

			R[3] = q1_q2 + q3_q0;
			R[4] = 1 - sq_q1 - sq_q3;
			R[5] = q2_q3 - q1_q0;

			R[6] = q1_q3 - q2_q0;
			R[7] = q2_q3 + q1_q0;
			R[8] = 1 - sq_q1 - sq_q2;
		} else if (R.length == 16) {
			R[0] = 1 - sq_q2 - sq_q3;
			R[1] = q1_q2 - q3_q0;
			R[2] = q1_q3 + q2_q0;
			R[3] = 0.0f;

			R[4] = q1_q2 + q3_q0;
			R[5] = 1 - sq_q1 - sq_q3;
			R[6] = q2_q3 - q1_q0;
			R[7] = 0.0f;

			R[8] = q1_q3 - q2_q0;
			R[9] = q2_q3 + q1_q0;
			R[10] = 1 - sq_q1 - sq_q2;
			R[11] = 0.0f;

			R[12] = R[13] = R[14] = 0.0f;
			R[15] = 1.0f;
		}
	}

	public static interface Handler {
		void handleYawn(float value);

		void handlePitch(float value);

		void handleRoll(float value);

		void onError(Throwable t);
	}
}