package su.levenetc.androidplayground;

import android.content.Context;
import android.support.v4.content.LocalBroadcastManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


/**
 * Created by Eugene Levenetc on 15/05/2016.
 */
@RunWith(MockitoJUnitRunner.class) public class CommonTests {

	@Mock Context context;

	@Test(expected = NullPointerException.class) public void testBroadcastManagerCreation() {
		LocalBroadcastManager.getInstance(context);
	}
}
