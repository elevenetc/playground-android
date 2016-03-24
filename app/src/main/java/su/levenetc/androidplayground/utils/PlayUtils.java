package su.levenetc.androidplayground.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;

/**
 * Created by Eugene Levenetc on 22/03/2016.
 */
public class PlayUtils {

	private static HashMap<String, Long> times = new HashMap<>();

	public static void startTime(String key) {
		times.put(key, System.nanoTime());
	}

	public static long endTime(String key) {
		if (times.containsKey(key)) return (System.nanoTime() - times.get(key)) / 1000000;
		else return 0;
	}

	public static Object getPrivateStaticFieldValueSilently(Class instance, String fieldName) {
		try {
			return getPrivateStaticFieldValue(instance, fieldName);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Object getPrivateStaticFieldValue(Class clazz, String fieldName) throws NoSuchFieldException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);
		return field.get(clazz);
	}

	public static void setFinalInstanceField(Object instance, String fieldName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
		Field field = instance.getClass().getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(instance, newValue);
	}

	static void setStaticFinalField(Class clazz, String fieldName, Object newValue) throws Exception {
		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true);

		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

		field.set(null, newValue);
	}
}
