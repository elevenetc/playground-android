package su.levenetc.androidplayground.utils;

import android.util.Log;

/**
 * Created by Eugene Levenetc on 24/03/2016.
 */
public class Out {

	public static void pln(String prefix, Object msg) {
		Log.i("", prefix + ":" + msg);
	}

	public static void pln(Object msg) {
		Log.i("", String.valueOf(msg));
	}
}
