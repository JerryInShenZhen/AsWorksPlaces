package com.coagent.lyscs.bookpart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lyscs on 2017/9/11.
 */
public class HandlerDemo extends Activity {

    private Context mContext = null;
    private int currentStr = 0;
    private String[] strHandler = {"strHandler1111", "strHandler2222",
            "strHandler3333", "strHandler4444",
            "strHandler5555", "strHandler6666",
            "strHandler7777", "strHandler18888"};

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what == 0x123){
                if(mContext != null){
                    Toast.makeText(mContext,
                            strHandler[currentStr++ % strHandler.length],
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(0x123);
            }
        }, 0, 2500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
