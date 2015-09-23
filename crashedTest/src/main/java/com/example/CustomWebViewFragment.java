package com.example.opavliuchen.webviewjslogone;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by o.pavliuchen on 20.09.2015.
 */
public class CustomWebViewFragment extends Fragment {

    private static final String WEB_VIEW_CONTENT_URL = "com.example.opavliuchen.webviewjslogone.content_url";
//    private static final String CAP = "https://www.google.com.ua/";
    private String mUrl;
    private WebView mWebView;
    private OnJSConsoleMessageListener mCallback;

    public static Fragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putSerializable(WEB_VIEW_CONTENT_URL, url);
        CustomWebViewFragment fragment = new CustomWebViewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mUrl = (String) getArguments().getSerializable(WEB_VIEW_CONTENT_URL);
        assert(mUrl != null);
//        if (mUrl == null) mUrl == CAP;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.web_view_fragment, container, false);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }*/
        mWebView = (WebView) view.findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            public boolean onConsoleMessage(ConsoleMessage cm) {
                mCallback.OnJSConsoleMessage(cm);
                Log.d("MyApplication", cm.message() + " -- From line "
                        + cm.lineNumber() + " of "
                        + cm.sourceId());
                return true;
            }
        });
        mWebView.loadUrl(mUrl);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnJSConsoleMessageListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnJSConsoleMessageListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mCallback = null;
    }

    public interface OnJSConsoleMessageListener {
        void OnJSConsoleMessage(ConsoleMessage cm);
    }
}
