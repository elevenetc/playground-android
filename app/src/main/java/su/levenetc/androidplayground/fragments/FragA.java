package su.levenetc.androidplayground.fragments;

import rx.functions.Action0;

/**
 * Created by eugene.levenetc on 05/10/16.
 */
public class FragA extends TextFragment {
    public static FragA create(Action0 nextHandler) {
        FragA result = new FragA();
        result.id = "A";
        result.nextHandler = nextHandler;
        return result;
    }
}