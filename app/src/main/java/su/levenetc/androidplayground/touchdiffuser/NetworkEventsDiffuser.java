package su.levenetc.androidplayground.touchdiffuser;

import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import su.levenetc.androidplayground.utils.ByteUtils;
import su.levenetc.androidplayground.utils.NetUtils;
import su.levenetc.androidplayground.utils.ThreadUtils;
import su.levenetc.androidplayground.utils.ViewUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class NetworkEventsDiffuser {

	private static final String TAG = NetworkEventsDiffuser.class.getSimpleName();

	static DatagramSocket senderSocket;
	static DatagramSocket receiverSocket;
	static StringBuilder stringBuilder = new StringBuilder();

	private static int SERVER_PORT = 6666;
	private static MotionEventsReceiver receiver;
	private static BlockingQueue<DiffuseEvent> eventsQueue = new LinkedBlockingQueue<>();

	static {
		initReceiverSocket();
		initSenderSocket();
	}

	public static void setMotionReceiver(MotionEventsReceiver receiver) {
		NetworkEventsDiffuser.receiver = receiver;
	}

	private static void initSenderSocket() {
		try {
			senderSocket = new DatagramSocket();
			senderSocket.setBroadcast(true);

			new Thread(() -> {
				while (true) {
					try {
						DiffuseEvent diffuseEvent = eventsQueue.take();

						MotionEvent event = diffuseEvent.event;
						View view = diffuseEvent.view;

						try {
							byte[] bytes = eventToByteArray(event, view);
							DatagramPacket packet = new DatagramPacket(bytes, 0, bytes.length, InetAddress.getByName("255.255.255.255"), SERVER_PORT);
							Log.d(TAG, "new event sending");
							senderSocket.send(packet);
						} catch (Exception e) {
							Log.d(TAG, "diffuse error");
							e.printStackTrace();
						}

					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();

		} catch (SocketException e) {
			Log.d(TAG, "initSenderSocket error");
			e.printStackTrace();
		}
	}

	private static void initReceiverSocket() {
		try {
			receiverSocket = new DatagramSocket(SERVER_PORT);

			new Thread(() -> {
				while (true) {
					try {
						byte[] buffer = new byte[1024];
						DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
						receiverSocket.receive(packet);

						if (NetUtils.isFromCurrentMachine(packet)) continue;


						if (receiver != null) receiver.handle(parseReceivedPacketData(packet.getData()));

					} catch (IOException e) {
						Log.d(TAG, "reading received packet error");
						e.printStackTrace();
						ThreadUtils.sleep(1000);
					}
				}
			}).start();


		} catch (SocketException e) {
			Log.d(TAG, "initReceiverSocket error");
			e.printStackTrace();
		}
	}


	public static void diffuse(MotionEvent event, View view) {

		eventsQueue.add(new DiffuseEvent(event, view));
	}

	static class DiffuseEvent {
		MotionEvent event;
		View view;

		public DiffuseEvent(MotionEvent event, View view) {
			this.event = event;
			this.view = view;
		}
	}

	static ArrivedMotionEvent parseReceivedPacketData(byte[] array) {
		byte[] xBytes = Arrays.copyOfRange(array, 0, 4);
		byte[] yBytes = Arrays.copyOfRange(array, 4, 8);
		byte[] actionBytes = Arrays.copyOfRange(array, 8, 12);
		byte[] viewIdLength = Arrays.copyOfRange(array, 12, 16);
		int vLength = ByteUtils.byteToInt(viewIdLength);
		byte[] viewIdBytes = Arrays.copyOfRange(array, 16, 16 + vLength);

		float x = ByteUtils.byteToFloat(xBytes);
		float y = ByteUtils.byteToFloat(yBytes);
		int action = ByteUtils.byteToInt(actionBytes);
		long currentTime = SystemClock.uptimeMillis();
		String viewId = new String(viewIdBytes);

		MotionEvent result = MotionEvent.obtain(currentTime, currentTime, action, x, y, 0);
		result.setSource(666);
		return new ArrivedMotionEvent(viewId, result);
	}

	static byte[] eventToByteArray(MotionEvent event, View view) {
		float x = event.getX();
		float y = event.getY();
		int action = event.getAction();
		byte[] viewId = ViewUtils.getId(view).getBytes();

		byte[] result = new byte[4 * 4 + viewId.length];//3: x(float), y(float), action(int), viewId length(int), viewId(string)

		System.arraycopy(ByteUtils.floatToByte(x), 0, result, 0, 4);
		System.arraycopy(ByteUtils.floatToByte(y), 0, result, 4, 4);
		System.arraycopy(ByteUtils.intToByte(action), 0, result, 8, 4);
		System.arraycopy(ByteUtils.intToByte(viewId.length), 0, result, 12, 4);
		System.arraycopy(viewId, 0, result, 16, viewId.length);

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

	public interface MotionEventsReceiver {
		void handle(ArrivedMotionEvent event);
	}

	public static class ArrivedMotionEvent{
		public String id;
		public MotionEvent event;

		public ArrivedMotionEvent(String id, MotionEvent event) {
			this.id = id;
			this.event = event;
		}
	}
}
