package com.example.opavliuchen.webviewjslogone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.widget.TextView;

/**
 * Created by o.pavliuchen on 20.09.2015.
 */
public class JSLogFragment extends Fragment {

    TextView mLogTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.js_log_fragment, container, false);
        mLogTextView = (TextView) view.findViewById(R.id.jsLogTV);
        return view;
    }

    public void updateLog(ConsoleMessage cm) {
        mLogTextView.append("\n" + cm.message());
    }
}
