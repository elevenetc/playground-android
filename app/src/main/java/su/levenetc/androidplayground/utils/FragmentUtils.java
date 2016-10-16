package su.levenetc.androidplayground.utils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by eugene.levenetc on 05/10/16.
 */
public class FragmentUtils {
    public static <T> T create(Class<T> clazz) {
        try {
            return clazz.cast(clazz.getDeclaredConstructor().newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }
}
