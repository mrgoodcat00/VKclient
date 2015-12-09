package com.goodcat.vkclient.application.activity;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import com.goodcat.vkclient.application.R;
import com.goodcat.vkclient.application.session.Session;

import java.util.regex.Pattern;

public class LoginActivity extends Activity{

    private static final String AUTH_URI = "https://oauth.vk.com/authorize?";

    private static final String PARAM_CLIENT_ID = "client_id=5122523";

    private static final String SCOPE = "scope=notify,friends,photos,audio,video,docs,notes,pages,offers,questions,wall,group,messages,offline";

    private static final String REDIRECT_URI = "redirect_uri=http://oauth.vk.com/blank.html";

    private static final String DISPLAY = "display=touch";

    private static final String RESPONSE_TYPE = "response_type=token";

    private static final String VERSION = "v=5.37";

    private static final String REQUEST_STR = AUTH_URI + PARAM_CLIENT_ID + "&"
            + SCOPE + "&" + REDIRECT_URI + "&" + DISPLAY + "&" + RESPONSE_TYPE + "&" + VERSION;

    private static final String TAG = LoginActivity.class.getSimpleName();

    private WebView loginWebView;

    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (!Session.isValidSession(this)) {

            if(Session.internetConnection(this)) {
                setContentView(R.layout.activity_login);
            } else {
                setContentView(R.layout.no_internet_connection);
                ImageButton ib = (ImageButton) findViewById(R.id.noInternet);
                TextView tv = (TextView) findViewById(R.id.reLoad);

                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                ib.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }

            loginWebView = (WebView) findViewById(R.id.webView);
            WebSettings webSettings = loginWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setSupportZoom(false);
            webSettings.setSaveFormData(true);
            loginWebView.setWebViewClient(new VKclient());
            loginWebView.requestFocus(View.FOCUS_DOWN);

            loginWebView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.d(TAG, "" + event.toString());
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                        case MotionEvent.ACTION_UP:
                            if (!v.hasFocus()) {
                                v.requestFocus();
                            }
                            break;
                    }
                    return false;
                }
            });
            loginWebView.loadUrl(REQUEST_STR);
        } else {
            setContentView(R.layout.activity_splash);
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }
            }, 500);
        }
    }



    private class VKclient extends WebViewClient {
        private final Pattern pattern = Pattern.compile("https?://(api|oauth)\\.vk\\.com/blank\\.html.*");
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            if(pattern.matcher(url).matches()){
                if (url.indexOf("#") > 0) {
                    Session.saveParams(url, LoginActivity.this);
                    Intent startHome = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(startHome);
                } else {
                    Session.readError(url);
                }
            }
        }
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
