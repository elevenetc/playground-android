package su.levenetc.androidplayground.utils;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.MotionEvents;
import android.support.test.espresso.action.Press;
import android.view.View;
import org.hamcrest.Matcher;

import static android.support.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast;


/**
 * Created by eleven on 03/10/2016.
 */
public class ExtraViewActions {
	public static class PressCenterOfView implements ViewAction {

		@Override
		public Matcher<View> getConstraints() {
			return isDisplayingAtLeast(90);
		}

		@Override
		public String getDescription() {
			return "press into the center of view";
		}

		@Override
		public void perform(UiController uiController, View view) {
			float[] precision = Press.FINGER.describePrecision();
			float[] coordinates = GeneralLocation.CENTER.calculateCoordinates(view);
			MotionEvents.DownResultHolder downResult = MotionEvents.sendDown(uiController, coordinates, precision);
			MotionEvents.sendUp(uiController, downResult.down);
		}
	}
}
