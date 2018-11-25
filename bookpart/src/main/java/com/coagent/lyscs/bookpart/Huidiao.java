package com.coagent.lyscs.bookpart;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.coagent.lyscs.bookpart.part.MyButton;

public class Huidiao extends Activity {

    private MyButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huidiao);

        showToast("on create---");

        button = (MyButton) findViewById(R.id.btn_mybutton);
        button.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(event.getAction() == KeyEvent.ACTION_DOWN){
                    Log.i("--Linster--", "the keydown on lister");
                    Toast.makeText(Huidiao.this, "the keydown on lister",
                            Toast.LENGTH_LONG).show();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        Log.i("--Linster--", "the keydown on activity");
        Toast.makeText(Huidiao.this, "the keydown on activity",
                Toast.LENGTH_LONG).show();
        return false;
    }

    private void showToast(String str){
        Toast.makeText(Huidiao.this, str,
                Toast.LENGTH_LONG).show();
    }
}
