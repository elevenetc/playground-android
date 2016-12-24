package su.levenetc.androidplayground.utils;

import android.content.Context;
import android.util.TypedValue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;

import su.levenetc.androidplayground.exceptions.ObjectDeserializationException;
import su.levenetc.androidplayground.exceptions.ObjectSerialisationException;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class Utils {

    public static <T> T toObject(byte[] array) throws ObjectDeserializationException {
        ByteArrayInputStream bis = new ByteArrayInputStream(array);
        ObjectInput in = null;
        try {
            in = new ObjectInputStream(bis);
            return (T) in.readObject();
        } catch (Exception e) {
            throw new ObjectDeserializationException(e);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }


    public static byte[] toByteArray(Object object) throws ObjectSerialisationException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new ObjectSerialisationException(e);
        } finally {
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

    public static float getSp(float sp, Context context) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sp, context.getResources().getDisplayMetrics());
    }

    public static String getFilledString(int length, char c) {
        if (length < 0) throw new IllegalArgumentException("length must be >= 0");
        if (length > 0) {
            char[] array = new char[length];
            Arrays.fill(array, c);
            return new String(array);
        }
        return "";
    }

    public static String fixLength(String string, int max, char extraChar) {
        if (string.length() < max) {
            int diff = max - string.length();
            string += getFilledString(diff, extraChar);
        } else if (string.length() > max) {
            string = string.substring(0, max);
        }
        return string;
    }
}
