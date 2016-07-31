package su.levenetc.androidplayground.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Eugene Levenetc on 31/07/2016.
 */
public class AnimatedTextView extends TextView {

	private String currentText;

	public AnimatedTextView(Context context) {
		super(context);
	}

	public AnimatedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void changeText(String text) {
		if (currentText == null) {
			currentText = text;
			setText(text);
		} else {
			changeTextInternal(text);
		}
	}

	private void changeTextInternal(String text) {
		currentText = text;
		AnimatorSet set = new AnimatorSet();
		set.setDuration(200);
		ObjectAnimator from = ObjectAnimator.ofFloat(this, View.ALPHA, 1, 0);
		ObjectAnimator to = ObjectAnimator.ofFloat(this, View.ALPHA, 0, 1);

		from.addListener(new Animator.AnimatorListener() {
			@Override public void onAnimationStart(Animator animation) {

			}

			@Override public void onAnimationEnd(Animator animation) {
				setText(text);
			}

			@Override public void onAnimationCancel(Animator animation) {

			}

			@Override public void onAnimationRepeat(Animator animation) {

			}
		});

		set.playSequentially(from, to);
		set.start();
	}


}
