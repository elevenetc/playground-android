package su.levenetc.androidplayground;

import org.junit.Test;

import su.levenetc.androidplayground.utils.Utils;

import static junit.framework.Assert.assertEquals;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class UtilsTest {
	@Test public void testFilledString() {
		assertEquals("   ", Utils.getFilledString(3, ' '));
		assertEquals(" ", Utils.getFilledString(1, ' '));
	}

	@Test public void testFixLength() {
		assertEquals("X  ", Utils.fixLength("X", 3, ' '));
		assertEquals("XX", Utils.fixLength("XXX", 2, ' '));
	}
}
