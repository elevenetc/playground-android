package su.levenetc.androidplayground.touchdiffuser;

import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import su.levenetc.androidplayground.utils.ByteUtils;
import su.levenetc.androidplayground.utils.ThreadUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;

public class NetworkEventsDiffuser {

	private static final String TAG = NetworkEventsDiffuser.class.getSimpleName();

	static DatagramSocket clientSocket;
	static DatagramSocket serverSocket;
	static StringBuilder stringBuilder = new StringBuilder();

	private static int SERVER_PORT = 6666;

	static {
		initServerSocket();
		initClientSocket();
	}

	private static void initClientSocket() {
		try {
			clientSocket = new DatagramSocket();
			clientSocket.setBroadcast(true);
		} catch (SocketException e) {
			Log.d(TAG, "initClientSocket error");
			e.printStackTrace();
		}
	}

	private static void initServerSocket() {
		try {
			serverSocket = new DatagramSocket(SERVER_PORT);

			new Thread(() -> {
				while (true) {
					try {
						byte[] buffer = new byte[1024];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
						serverSocket.receive(packet);

						MotionEvent motionEvent = byteArrayToMotionEvent(packet.getData());
						Log.d(TAG, "new motion even received:" + motionEvent.getAction());

					} catch (IOException e) {
						Log.d(TAG, "reading received packet error");
						e.printStackTrace();
						ThreadUtils.sleep(1000);
					}
				}
			}).start();


		} catch (SocketException e) {
			Log.d(TAG, "initServerSocket error");
			e.printStackTrace();
		}
	}

	public static void diffuse(MotionEvent event, View view) {

		try {
			byte[] bytes = eventToByteArray(event, view);
			DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, InetAddress.getByName("255.255.255.255"), SERVER_PORT);
			Log.d(TAG, "new event sending");
			clientSocket.send(packet);
		} catch (Exception e) {
			Log.d(TAG, "diffuse error");
			e.printStackTrace();
		}
	}

	static MotionEvent byteArrayToMotionEvent(byte[] array) {
		byte[] xBytes = Arrays.copyOfRange(array, 0, 4);
		byte[] yBytes = Arrays.copyOfRange(array, 4, 8);
		byte[] actionBytes = Arrays.copyOfRange(array, 8, 16);

		float x = ByteUtils.byteToFloat(xBytes);
		float y = ByteUtils.byteToFloat(yBytes);
		int action = ByteUtils.byteToInt(actionBytes);
		long currentTime = SystemClock.uptimeMillis();

		return MotionEvent.obtain(currentTime, currentTime, action, x, y, 0);
	}

	static byte[] eventToByteArray(MotionEvent event, View view) {
		float x = event.getX();
		float y = event.getY();
		int action = event.getAction();

		byte[] result = new byte[4 * 3];//3: x, y, action

		System.arraycopy(ByteUtils.floatToByte(x), 0, result, 0, 4);
		System.arraycopy(ByteUtils.floatToByte(y), 0, result, 4, 4);
		System.arraycopy(ByteUtils.intToByte(action), 0, result, 8, 4);

		return result;
	}

	static void appendValue(String name, Object value) {
		stringBuilder.append(name);
		stringBuilder.append(":");
		stringBuilder.append(String.valueOf(value));
	}

	static void appendDivider() {
		stringBuilder.append("|");
	}
}
