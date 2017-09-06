package com.coagent.lyscs.countshitdiary;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        delayEnterHome();
    }

    /**
     * 延迟3秒进入主界面
     */
    private void delayEnterHome(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enterHome();
            }
        }, 3000);
    }

    /**
     * 进入主界面
     */
    private void enterHome(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            //触摸后直接进入主界面
            handler.removeCallbacksAndMessages(null);
            enterHome();
        }
        return super.onTouchEvent(event);
    }
}
