package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.opengl.GLView;
import su.levenetc.androidplayground.views.GLViewConfig;

/**
 * Created by eugene.levenetc on 25/12/2016.
 */
public class OpenGLSampleActivity extends AppCompatActivity {

    private GLView glView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl_sample);
        glView = (GLView) findViewById(R.id.view_gl);
        GLViewConfig glViewConfig = (GLViewConfig) findViewById(R.id.view_gl_config);

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
