package su.levenetc.androidplayground.utils;

import java.nio.ByteBuffer;

public class ByteUtils {
	public static byte[] intToByte(int value) {
		byte[] result = new byte[4];
		result[0] = (byte) (value & 0xFF);
		result[1] = (byte) ((value & 0xFF00) >> 8);
		result[2] = (byte) ((value & 0xFF0000) >> 16);
		result[3] = (byte) ((value & 0xFF000000) >> 24);
		return result;
	}

	public static int byteToInt(byte[] array) {
		int result = 0;
		result = result | (0xFF & array[3]);
		result = result << 8;
		result = result | (0xFF & array[2]);
		result = result << 8;
		result = result | (0xFF & array[1]);
		result = result << 8;
		result = result | (0xFF & array[0]);
		return result;
	}


	public static byte[] floatToByte(float value) {
		return ByteBuffer.allocate(4).putFloat(value).array();
	}

	public static float byteToFloat(byte[] array) {
		return ByteBuffer.wrap(array).getFloat();
	}
}
