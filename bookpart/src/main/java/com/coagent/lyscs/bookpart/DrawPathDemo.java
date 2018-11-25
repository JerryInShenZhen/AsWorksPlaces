package com.coagent.lyscs.bookpart;

import android.app.Activity;
import android.os.Bundle;

import com.coagent.lyscs.bookpart.part.PathView;

public class DrawPathDemo extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_draw_path_demo);
        setContentView(new PathView(this));
    }

}
