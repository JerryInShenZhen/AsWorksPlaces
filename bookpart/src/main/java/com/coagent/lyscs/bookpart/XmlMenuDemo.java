package com.coagent.lyscs.bookpart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lyscs on 2017/7/8.
 */
public class XmlMenuDemo extends Activity {

    private TextView tvMain;
    private android.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findView();
        registerForContextMenu(tvMain); //为这个VIEW注册上下文菜单
    }

    private void findView() {
        tvMain = (TextView) findViewById(R.id.tv_main);
        tvMain.setText("XML文件定义菜单测试用显示");
        tvMain.setTextSize(30);

        actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);  //设置应用程序图标可显示
        actionBar.setHomeButtonEnabled(true);   //将应用程序图标设置为可点击的按钮
        actionBar.setDisplayHomeAsUpEnabled(true);  //将应用程序图标设置为可点击的按钮，并添加向左箭头
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.context, menu); //装填xml到对应菜单
        menu.setHeaderIcon(R.mipmap.ic_launcher);
        menu.setHeaderTitle("请选择背景颜色");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        item.setChecked(true);
        switch (item.getItemId()){
            case R.id.red_title:
                tvMain.setBackgroundColor(Color.RED);
                break;
            case R.id.green_title:
                tvMain.setBackgroundColor(Color.GREEN);
                break;
            case R.id.blue_title:
                tvMain.setBackgroundColor(Color.BLUE);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu_01, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.isCheckable()){
            item.setChecked(true);
        }
        switch (item.getItemId()){
            case R.id.font_10:
                tvMain.setTextSize(10 * 2);
                break;
            case R.id.font_12:
                tvMain.setTextSize(12 * 2);
                break;
            case R.id.font_14:
                tvMain.setTextSize(14 * 2);
                break;
            case R.id.font_16:
                tvMain.setTextSize(16 * 2);
                break;
            case R.id.font_18:
                tvMain.setTextSize(18 * 2);
                break;
            case R.id.red_font:
                tvMain.setTextColor(Color.RED);
                break;
            case R.id.green_font:
                tvMain.setTextColor(Color.GREEN);
                break;
            case R.id.blue_font:
                tvMain.setTextColor(Color.BLUE);
                break;
            case R.id.plain_item:
                tvMain.setText("点击了普通菜单选项");
                break;
            case android.R.id.home:
                goAntherActivity(ContextMenuDemo.class);
                break;
        }
        return true;
    }

    /**
     * 跳转到其它界面
     */
    private void goAntherActivity(Class<? extends Activity> _class){
        Intent intent = new Intent(this, _class);
        startActivity(intent);
    }
}
