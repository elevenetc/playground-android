package su.levenetc.androidplayground.utils;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Eugene Levenetc on 24/03/2016.
 */
public class DoubleSizeClickListener implements View.OnClickListener {
	@Override public void onClick(View v) {
		ViewGroup.LayoutParams lp = v.getLayoutParams();
		if (lp.height < 0) lp.height = 100;
		else lp.height *= 2;
		v.setLayoutParams(lp);
	}
}
