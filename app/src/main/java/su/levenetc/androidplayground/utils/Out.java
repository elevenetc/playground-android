package su.levenetc.androidplayground.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eugene Levenetc on 24/03/2016.
 */
public class Out {

    private static final Date date = new Date(0);
    private static final DateFormat formatter = new SimpleDateFormat("HH:mm:ss:SSS");

    public static void lifeOnCreate(String objName) {
        Log.i(objName, "onCreate");
    }

    public static void lifeOnDestroy(String objName) {
        Log.i(objName, "onDestroy");
    }

    public static void lifeOnStartCommand(String tag) {
        Log.i(tag, "onStartCommand");
    }

    public static void pln() {
        pln("\n");
    }

    public static void pln(String prefix, Object msg) {
        if (prefix != null) Log.i("", prefix + ":" + msg);
        else Log.i("", msg.toString());
    }

    public static void pln(Object msg) {
        Log.i("", String.valueOf(msg));
        System.out.println(msg);
    }

    public static void time(long ms) {
        time(null, ms);
    }

    public static void time(String prefix, long ms) {
        date.setTime(ms);
        pln(prefix, formatter.format(date));
    }
}
