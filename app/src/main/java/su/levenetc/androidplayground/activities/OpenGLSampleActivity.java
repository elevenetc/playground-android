package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.opengl.GLView;
import su.levenetc.androidplayground.views.GLViewConfig;

/**
 * Created by eugene.levenetc on 25/12/2016.
 */
public class OpenGLSampleActivity extends AppCompatActivity {

	private GLView glView;
	private TextView textFps;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_opengl_sample);
		glView = findViewById(R.id.view_gl);
		textFps = findViewById(R.id.text_fps);
		GLViewConfig glViewConfig = findViewById(R.id.view_gl_config);

		glViewConfig.setUpXHandler(value -> {
			glView.getRenderer().setUpX(value);
			glView.requestRender();
		});
		glViewConfig.setUpYHandler(value -> {
			glView.getRenderer().setUpY(value);
			glView.requestRender();
		});
		glViewConfig.setUpZHandler(value -> {
			glView.getRenderer().setUpZ(value);
			glView.requestRender();
		});

		//

		glViewConfig.setCamXHandler(value -> {
			glView.getRenderer().setCamX(value);
			glView.requestRender();
		});
		glViewConfig.setCamYHandler(value -> {
			glView.getRenderer().setCamY(value);
			glView.requestRender();
		});

		glView.getRenderer().getFpsCounter().setHandler(fps -> {
			textFps.post(() -> updateFps(fps));
		});
	}

	private void updateFps(float fps) {
		textFps.setText(String.valueOf(fps));
	}

	@Override
	protected void onPause() {
		super.onPause();
		glView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		glView.onResume();
	}
}
