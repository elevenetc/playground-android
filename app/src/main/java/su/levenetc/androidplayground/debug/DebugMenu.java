package su.levenetc.androidplayground.debug;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class DebugMenu {
    public static void add(AppCompatActivity activity) {
        WindowManager wm = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);


        ContentFrameLayout view = activity.findViewById(android.R.id.content);
        view.addView(new MenuContainer(activity), new ContentFrameLayout.LayoutParams(200, 200));
//        if (view == null) {
//
//        }


//        wm.addView(new MenuContainer(activity), new WindowManager.LayoutParams(
//                200, 200,
//                100, 100,
//                TYPE_APPLICATION_OVERLAY,
//                0,
//                PixelFormat.TRANSPARENT));
    }

    static class MenuContainer extends FrameLayout {

        public MenuContainer(@NonNull Context context) {
            super(context);
            init();
        }

        private void init() {
            addView(new DM(getContext()));
            setBackgroundColor(Color.BLUE);
        }
    }

    static class DM extends View {

        public DM(Context context) {
            super(context);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            super.onMeasure(
                    MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY)
            );
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.RED);
        }
    }
}
