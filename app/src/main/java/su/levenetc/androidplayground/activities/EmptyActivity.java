package su.levenetc.androidplayground.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

/**
 * Created by eugene.levenetc on 05/02/2018.
 */

public class EmptyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View view = new View(this);
//        view.setBackgroundColor(Color.RED);
        setContentView(new LayeredButton(this));
    }

    static class LayeredButton extends RelativeLayout {

        public LayeredButton(Context context) {
            super(context);
            Button buttonA = new Button(context);
            Button buttonB = new Button(context);
            Button buttonC = new Button(context);

            buttonA.setText("a");
            buttonB.setText("b");
            buttonC.setText("c");

            addView(buttonC);
            addView(buttonB);
            addView(buttonA);

            rotateFrontChild();

            setVisibility(View.VISIBLE);
        }

        void rotateFrontChild(){
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    getChildAt(0).bringToFront();
                    rotateFrontChild();
                }
            }, 1000);
        }

    }
}
