package com.coagent.lyscs.crazydemoall;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

/**
 * Created by lyscs on 2017/3/11.
 */
public class PopupWindowDemo extends Activity {

    Button button;
    Button button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialog);
        View root = this.getLayoutInflater().inflate(R.layout.activity_main, null);
        final PopupWindow popup = new PopupWindow(root, 560, 720);

        button1 = (Button) findViewById(R.id.btn1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.showAtLocation(findViewById(R.id.btn1), Gravity.CENTER, 20, 20);
            }
        });

        button = (Button) root.findViewById(R.id.btn_main);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
    }
}
