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

        textView = findViewById(R.id.text_view);

        final ReplaceMethod custom = new ReplaceMethod() {
            @Override
            public void test(String text, long[] times, int index) {
                long time;
                String result;
                time = System.currentTimeMillis();
                text = text.replace(EURO_SYMBOL, "€");
                text = text.replace(E_SYMBOL, "ë");
                result = text;
                times[index] = System.currentTimeMillis() - time;
            }
        };

        final ReplaceMethod builtin = new ReplaceMethod() {
            @Override
            public void test(String text, long[] times, int index) {
                long time;
                Spanned result;
                time = System.currentTimeMillis();
                result = Html.fromHtml(text);
                times[index] = System.currentTimeMillis() - time;
            }
        };

        findViewById(R.id.btn_run).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("builtin", "builtin");
                        test(20, genText(20), builtin);
                        test(500, genText(500), builtin);
                        test(1000, genText(1000), builtin);
                        test(2000, genText(2000), builtin);

                        Log.i("cusom", "custom");
                        test(20, genText(20), custom);
                        test(500, genText(500), custom);
                        test(1000, genText(1000), custom);
                        test(2000, genText(2000), custom);
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

    private void test(int size, String text, ReplaceMethod method) {
        long time;
        Spanned result;
        long[] times = new long[100];
        long max = 0;
        long average = 0;
        for (int i = 0; i < times.length; i++) {
            method.test(text, times, i);
            if (times[i] > max) max = times[i];
        }

        for (int i = 0; i < times.length; i++) average += times[i];

        Log.i("result", "Size:" + size + " max:" + max + "ms " + "average:" + average / times.length + "ms");
    }

    private interface ReplaceMethod {
        void test(String text, long[] times, int index);
    }
}
