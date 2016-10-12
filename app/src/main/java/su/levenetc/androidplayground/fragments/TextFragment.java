package su.levenetc.androidplayground.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action0;
import su.levenetc.androidplayground.R;

import static java.lang.String.format;

/**
 * Created by eugene.levenetc on 05/10/16.
 */
public class TextFragment extends Fragment {


    public static TextFragment create(String id, Action0 nextHandler, Action0 backHandler) {
        TextFragment result = new TextFragment();
        result.nextHandler = nextHandler;
        result.backHandler = backHandler;
        result.id = id;
        return result;
    }

    protected String id;
    protected Action0 nextHandler;
    protected Action0 backHandler;

    @BindView(R.id.text_id)
    TextView textId;

    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.btn_back)
    Button btnBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View result = inflater.inflate(R.layout.fragment_text_and_next, container, false);
        ButterKnife.bind(this, result);
        return result;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textId.setText(format("Id: %s", id));
        if (nextHandler == null) btnNext.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_next)
    public void handleNext() {
        nextHandler.call();
    }

    @OnClick(R.id.btn_back)
    public void handleBack() {
        backHandler.call();
    }
}