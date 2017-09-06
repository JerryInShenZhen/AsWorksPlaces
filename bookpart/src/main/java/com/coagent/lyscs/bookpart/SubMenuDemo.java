package com.coagent.lyscs.bookpart;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by lyscs on 2017/5/21.
 */
public class SubMenuDemo extends Activity {

    //定义 字体大小 菜单的选项标识
    private static final int FONT_10 = 0x111;
    private static final int FONT_12 = 0x112;
    private static final int FONT_14 = 0x113;
    private static final int FONT_16 = 0x114;
    private static final int FONT_18 = 0x115;
    //定义 普通菜单项 的标识
    private static final int PLAIN_ITEM = 0x11b;
    //定义 字体颜色 的标识
    private static final int FONT_RED = 0x116;
    private static final int FONT_BLUE = 0x117;
    private static final int FONT_GREEN = 0x118;

    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);

        editText = (EditText) findViewById(R.id.et_submenu_input);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //向menu中添加“字体大小”的子菜单
        SubMenu fontMenu = menu.addSubMenu("字体大小");
        fontMenu.setIcon(R.mipmap.ic_launcher);
        fontMenu.setHeaderIcon(R.mipmap.ic_launcher);
        fontMenu.setHeaderTitle("选择字体大小");
        fontMenu.add(0, FONT_10, 0, "10号字体");
        fontMenu.add(0, FONT_12, 0, "12号字体");
        fontMenu.add(0, FONT_14, 0, "14号字体");
        fontMenu.add(0, FONT_16, 0, "16号字体");
        fontMenu.add(0, FONT_18, 0, "18号字体");

        //向menu中添加普通菜单
        menu.add(0, PLAIN_ITEM, 0, "普通菜单");

        //向menu中添加“字体颜色”的子菜单
        SubMenu colorMenu = menu.addSubMenu("字体颜色");
        colorMenu.setIcon(R.mipmap.ic_launcher);
        colorMenu.setHeaderIcon(R.mipmap.ic_launcher);
        colorMenu.setHeaderTitle("选择文字颜色");
        colorMenu.add(0, FONT_RED, 0, "红色");
        colorMenu.add(0, FONT_BLUE, 0, "蓝色");
        colorMenu.add(0, FONT_GREEN, 0, "绿色");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case FONT_10:
                editText.setTextSize(10 * 2);
                break;
            case FONT_12:
                editText.setTextSize(12 * 2);
                break;
            case FONT_14:
                editText.setTextSize(14 * 2);
                break;
            case FONT_16:
                editText.setTextSize(16 * 2);
                break;
            case FONT_18:
                editText.setTextSize(18 * 2);
                break;
            case FONT_RED:
                editText.setTextColor(Color.RED);
                break;
            case FONT_BLUE:
                editText.setTextColor(Color.BLUE);
                break;
            case FONT_GREEN:
                editText.setTextColor(Color.GREEN);
                break;
            case PLAIN_ITEM:
                Toast.makeText(this, "你点击了普通菜单", Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
