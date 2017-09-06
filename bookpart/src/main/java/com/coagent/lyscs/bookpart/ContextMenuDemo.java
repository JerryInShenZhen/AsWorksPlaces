package com.coagent.lyscs.bookpart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lyscs on 2017/6/14.
 */
public class ContextMenuDemo extends Activity {

    private final int MENU1 = 0x111;
    private final int MENU2 = 0x112;
    private final int MENU3 = 0x113;

    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt = (TextView) findViewById(R.id.tv_main);
        txt.setText("可通过上下文菜单选择背景颜色");
        //为该控件注册上下文菜单
        registerForContextMenu(txt);
    }

    //创建上下文菜单时触发该方法
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(0, MENU1, 0, "Red");
        menu.add(0, MENU2, 0, "Green");
        menu.add(0, MENU3, 0, "Bule");
        menu.setGroupCheckable(0, true, true);  //设置成单选
        menu.setHeaderIcon(R.mipmap.ic_launcher);
        menu.setHeaderTitle("选择背景颜色");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case MENU1:
                item.setChecked(true);
                txt.setBackgroundColor(Color.RED);
                break;
            case MENU2:
                item.setChecked(true);
                txt.setBackgroundColor(Color.GREEN);
                break;
            case MENU3:
                item.setChecked(true);
                txt.setBackgroundColor(Color.BLUE);
                break;

        }
        return true;
    }
}
