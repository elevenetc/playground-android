package su.levenetc.androidplayground.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import su.levenetc.androidplayground.R;

/**
 * Created by Eugene Levenetc on 12/07/2016.
 */
public class RotationSensorView extends LinearLayout {

	private DeflectionView deflectionX;
	private DeflectionView deflectionY;
	private DeflectionView deflectionZ;

	public RotationSensorView(Context context) {
		super(context);
		init();
	}

	public RotationSensorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		setOrientation(VERTICAL);
		LayoutInflater.from(getContext()).inflate(R.layout.view_rotation_sensor, this, true);
		deflectionX = findViewById(R.id.deflection_x);
		deflectionY = findViewById(R.id.deflection_y);
		deflectionZ = findViewById(R.id.deflection_z);
		setWillNotDraw(false);
	}

	public void setValues(float x, float y, float z) {
		deflectionX.setValue(x);
		deflectionY.setValue(y);
		deflectionZ.setValue(z);

		deflectionX.invalidate();
		deflectionY.invalidate();
		deflectionZ.invalidate();
	}
}
