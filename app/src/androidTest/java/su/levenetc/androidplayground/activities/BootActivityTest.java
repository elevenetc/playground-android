package su.levenetc.androidplayground.activities;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import su.levenetc.androidplayground.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class BootActivityTest {

	@Rule
	public ActivityTestRule<BootActivity> mActivityTestRule = new ActivityTestRule<>(BootActivity.class);

	@Test
	public void bootActivityTest() {
		ViewInteraction button = onView(
				allOf(withId(R.id.btn_show_button), withText("Show button"), isDisplayed()));
		button.perform(click());

		ViewInteraction button2 = onView(
				allOf(withId(R.id.btn_open_activity), withText("Open Activity"), isDisplayed()));
		button2.perform(click());

		ViewInteraction editText = onView(
				allOf(withId(R.id.edit_text), isDisplayed()));
		editText.perform(replaceText("abc"), closeSoftKeyboard());

		ViewInteraction button3 = onView(
				allOf(withId(R.id.btn_action), withText("Action"), isDisplayed()));
		button3.perform(click());

	}

}
