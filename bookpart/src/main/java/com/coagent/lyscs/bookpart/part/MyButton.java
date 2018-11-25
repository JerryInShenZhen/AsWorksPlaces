package com.coagent.lyscs.bookpart.part;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by lyscs on 2017/9/10.
 */
public class MyButton extends Button {

    private Context mContext;

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        Log.i("--mybutton--", "the onKeyDown in myButton");
        Toast.makeText(mContext, "the keydown in myButton",
                Toast.LENGTH_LONG).show();
        return false;    //事件在此不被拦截
    }

    @Override
    public boolean callOnClick() {
        super.callOnClick();
        Log.i("--mybutton--", "the callOnClick in myButton");
        Toast.makeText(mContext, "the callOnClick in myButton",
                Toast.LENGTH_LONG).show();
        return false;    //事件在此不被拦截
    }
}
