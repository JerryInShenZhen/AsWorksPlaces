package com.coagent.lyscs.bookpart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener{

    private Context mContext;

    private int[] btnId = {
            R.id.btn_01, R.id.btn_02,R.id.btn_03,R.id.btn_04,
            R.id.btn_05, R.id.btn_06,R.id.btn_07,R.id.btn_08,
            R.id.btn_09, R.id.btn_10,R.id.btn_11,R.id.btn_12,
            R.id.btn_13, R.id.btn_14,R.id.btn_15,R.id.btn_16
    };

    private PopupMenu popup =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        findView();
    }

    private void findView() {
        for (int i=0; i<btnId.length; i++){
            findViewById(btnId[i]).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_01:
                popupMenuCreat(v);
                break;
            case R.id.btn_02:
                goAntherActivity(ActionViewDemo.class);
                break;
            case R.id.btn_03:
                goAntherActivity(ActionTabDemo.class);
                break;
            case R.id.btn_04:
                enterActionCategory(v);
                break;
            case R.id.btn_05:
                goAntherActivity(Huidiao.class);
                break;
            case R.id.btn_06:
                goAntherActivity(HandlerDemo.class);
                break;
            case R.id.btn_07:
                goAntherActivity(BitmapFactoryDemo.class);
                break;
            case R.id.btn_08:
                goAntherActivity(GeometricViewDemo.class);
                break;
            case R.id.btn_09:
                goAntherActivity(DrawPathDemo.class);
                break;
            case R.id.btn_10:

                break;
            case R.id.btn_11:

                break;
            case R.id.btn_12:

                break;
            case R.id.btn_13:

                break;
            case R.id.btn_14:

                break;
            case R.id.btn_15:

                break;
            case R.id.btn_16:

                break;
        }
    }

    /**
     * 通过action和category启动activity
     */
    private void enterActionCategory(View v) {
        Intent intent = new Intent();
        intent.setAction("com.coagent.lysys.action.CRAZYIT_ACTION");
        intent.addCategory("com.coagent.lysys.category.CRAZYIT_CATEGOTY");
        startActivity(intent);
    }

    /**
     * 对按钮控件产生一个popupMenus
     */
    private void popupMenuCreat(View v) {
        popup = new PopupMenu(mContext, v);
        //将菜单文件加载到popup菜单中
        getMenuInflater().inflate(R.menu.context, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.red_title:
                        popup.dismiss();
                        break;
                    default:
                        Toast.makeText(mContext, "you had click the"+
                        item.getTitle()+"item", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
        popup.show();
    }

    /**
     * 跳转到其它界面
     */
    private void goAntherActivity(Class<? extends Activity> _class){
        Intent intent = new Intent(mContext, _class);
        startActivity(intent);
    }
}
