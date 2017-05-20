package com.coagent.lyscs.crazydemoall;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;

import com.coagent.lyscs.crazydemoall.part.HttpThread;

/**
 * Created by lyscs on 2017/5/15.
 */
public class HttpUrlConDemo extends Activity {

    private WebView webView;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = (WebView) findViewById(R.id.webview);
        new HttpThread("http://www.baidu.com", webView, handler).start();
    }
}
