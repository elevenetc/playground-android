package su.levenetc.androidplayground.activities;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import su.levenetc.androidplayground.R;
import su.levenetc.androidplayground.fragments.MapFragment;

/**
 * Created by eugene.levenetc on 15/01/2017.
 */
public class MapEditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        findViewById(R.id.btn_zoom_in).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle zoom in
            }
        });

        findViewById(R.id.btn_zoom_out).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle zoom out
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.root_container, new MapFragment())
                    .commit();
        }
    }

static class Surface extends GLSurfaceView{

    public Surface(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
}