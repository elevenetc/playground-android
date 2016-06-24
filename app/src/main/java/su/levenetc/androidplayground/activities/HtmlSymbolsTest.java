package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import su.levenetc.androidplayground.R;

/**
 * Created by eugene.levenetc on 24/06/16.
 */
public class HtmlSymbolsTest extends AppCompatActivity {
    private String EURO_SYMBOL = "&#8364;";
    private String E_SYMBOL = "&#235;";
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_symbols);

        textView = (TextView) findViewById(R.id.text_view);

        findViewById(R.id.btn_run).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("test", "testBuiltIn");
                        testBuiltIn(500, genText(500));
                        testBuiltIn(1000, genText(1000));
                        testBuiltIn(2000, genText(2000));
                    }
                }, 2000);
            }
        });
    }

    private String genText(int size) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            double rnd = Math.random();
            if (rnd >= 0.7) {
                sb.append(EURO_SYMBOL);
            } else if (rnd >= 0.3 && rnd < 0.7) {
                sb.append(E_SYMBOL);
            } else {
                sb.append("A");
            }
        }
        return sb.toString();
    }

    private void testBuiltIn(int size, String text) {
        long time;
        Spanned result;
        long[] times = new long[100];
        long max = 0;
        long average = 0;
        for (int i = 0; i < times.length; i++) {

            time = System.currentTimeMillis();
            result = Html.fromHtml(text);
            times[i] = System.currentTimeMillis() - time;

            if (times[i] > max) max = times[i];
        }

        for (int i = 0; i < times.length; i++) average += times[i];

        Log.i("result", "Size:" + size + " max:" + max + "ms " + "average:" + average / times.length + "ms");
    }
}
