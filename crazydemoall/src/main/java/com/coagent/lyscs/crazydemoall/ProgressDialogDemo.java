package com.coagent.lyscs.crazydemoall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

/**
 * Created by lyscs on 2017/3/11.
 */
public class ProgressDialogDemo extends Activity {

    final static int MAX_PROGRESS = 100;
    private int[] data = new int[50];      //用一个数组填充模拟耗时
    int progressStatus = 0;
    int hasData = 0;
    ProgressDialog p1, p2;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x123) {       //判断消息来源
                p2.setProgress(progressStatus);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progressdialog);

    }

    public void showSpinner(View v) {
        ProgressDialog.show(this, "任务进行中....", "任务执行中，请等待...", false, true);
    }

    public void showIndeterminate(View v) {
        p1 = new ProgressDialog(ProgressDialogDemo.this);
        p1.setTitle("任务正在执行中");
        p1.setMessage("任务执行中，请等待...");
        p1.setCancelable(true);     //设置对话框能用取消按钮关闭
        p1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);       //设置对话框风格
        p1.setIndeterminate(true);  //设置进度条不显示进度
        p1.show();
    }

    public void showProgress(View v) {
        progressStatus = 0;
        hasData = 0;

        p2 = new ProgressDialog(ProgressDialogDemo.this);
        p2.setMax(MAX_PROGRESS);
        p2.setTitle("任务执行百分比");
        p2.setMessage("耗时任务完成百分");
        p2.setCancelable(false);     //设置对话框不能用取消按钮关闭
        p2.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);       //设置对话框风格
        p2.setIndeterminate(false);  //设置进度条显示进度
        p2.show();

        new Thread(){
            @Override
            public void run() {
                while (progressStatus < MAX_PROGRESS){
                    progressStatus = MAX_PROGRESS*doWork()/data.length;
                    handler.sendEmptyMessage(0x123);
                }
                if (progressStatus > MAX_PROGRESS) {
                    p2.dismiss();
                }
            }
        }.start();
    }

    public int doWork(){
        data[hasData++] = (int) (Math.random()*100);
        try {
            Thread.sleep(1000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        return hasData;
    }

}
