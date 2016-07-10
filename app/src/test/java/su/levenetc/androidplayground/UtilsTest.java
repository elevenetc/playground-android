package su.levenetc.androidplayground;

import junit.framework.Assert;

import org.junit.Test;

import su.levenetc.androidplayground.utils.Utils;

/**
 * Created by Eugene Levenetc on 10/07/2016.
 */
public class UtilsTest {
	@Test public void testFilledString() {
		Assert.assertEquals("   ", Utils.getFilledString(3, ' '));
		Assert.assertEquals(" ", Utils.getFilledString(1, ' '));
	}
}
