package com.example.opavliuchen.webviewjslogone;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.ConsoleMessage;

public class MainActivity extends AppCompatActivity implements CustomWebViewFragment.OnJSConsoleMessageListener {

    private static final String FIRST_URL = "http://ptech.me/stck";
    private static final String SECOND_URL = "http://ptech.me/trst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment webViewFragment = fm.findFragmentById(R.id.webViewFragment);

        if (webViewFragment == null) {
            webViewFragment = createWebFragment();
            fm.beginTransaction().add(R.id.webViewFragment, webViewFragment).commit();
        }

        Fragment jsLogFragment = fm.findFragmentById(R.id.jsLogFragment);

        if (jsLogFragment == null) {
            jsLogFragment = createLogFragment();
            fm.beginTransaction().add(R.id.jsLogFragment, jsLogFragment).commit();
        }
    }

    private Fragment createLogFragment() {
        return new JSLogFragment();
    }

    private Fragment createWebFragment() {
        return CustomWebViewFragment.newInstance(SECOND_URL);
//        return new CustomWebViewFragment();
    }

    @Override
    public void OnJSConsoleMessage(ConsoleMessage cm) {
        JSLogFragment fragment = (JSLogFragment) getSupportFragmentManager().findFragmentById(R.id.jsLogFragment);
        if (fragment != null) {
            fragment.updateLog(cm);
        }
    }

    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }*/
}
