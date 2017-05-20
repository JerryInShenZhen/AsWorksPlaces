package com.coagent.lyscs.crazydemoall.part;

import android.os.Handler;
import android.webkit.WebView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lyscs on 2017/5/15.
 */
public class HttpThread extends Thread {

    private String url;
    private WebView webView;
    private Handler handler;

    public HttpThread(String url, WebView webView, Handler handler) {
        this.url = url;
        this.webView = webView;
        this.handler = handler;
    }

    @Override
    public void run() {
        try {
            URL httpUrl = new URL(url);
            try {
                HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
                conn.setConnectTimeout(5000);       //超时时间
                conn.setRequestMethod("GET");       //GET方式请求
                final StringBuffer sb = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                while ((str = reader.readLine())!=null){    //读出返回数据
                    sb.append(str);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        webView.loadData(sb.toString(), "text/html;charset=utf-8", null);
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
