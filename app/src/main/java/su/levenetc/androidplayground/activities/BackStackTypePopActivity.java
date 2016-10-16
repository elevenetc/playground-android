package su.levenetc.androidplayground.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import rx.functions.Action0;
import su.levenetc.androidplayground.R;

import static su.levenetc.androidplayground.fragments.TextFragment.create;

/**
 * Created by eugene.levenetc on 05/10/16.
 */
public class BackStackTypePopActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        if (savedInstanceState == null) {
            final FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().add(R.id.root_container, create("A", new Action0() {
                @Override
                public void call() {
                    fm.beginTransaction().replace(R.id.root_container, create("B", new Action0() {
                        @Override
                        public void call() {
                            fm.beginTransaction().replace(R.id.root_container, create("C", null, new Action0() {
                                @Override
                                public void call() {
                                    backTo();
                                }
                            }));
                        }
                    }, null)).commit();
                }
            }, null)).commit();
        }
    }

    private void backTo(Class<? extends Fragment> clazz) {

    }
}