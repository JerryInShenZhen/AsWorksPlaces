package com.coagent.lyscs.crazydemoall;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lyscs on 2017/3/11.
 * AlertDialog对话框
 */
public class AlertDialogDemo extends Activity {

    TextView show;
    private String[] items = {"city of stars", "la la land", "In Love"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alertdialog);

        show = (TextView) findViewById(R.id.tv_show);
    }

    protected void simpleDialog(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("简单对话框")
                .setIcon(R.drawable.ic_launcher)
                .setMessage("对话框测试内容，哈哈\n第二行内容案例");
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    protected void simpleList(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("简单列表对话框")
                .setIcon(R.drawable.ic_launcher)
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogDemo.this, "你选中了" + items[which] + "这首歌", Toast.LENGTH_SHORT).show();
                        show.setText( "你选中了" + items[which] + "这首歌");
                    }
                });
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    protected void singleChoice(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("单选列表对话框")
                .setIcon(R.drawable.ic_launcher)
                .setSingleChoiceItems(items, 1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AlertDialogDemo.this, "你选中了" + items[which] + "这首歌", Toast.LENGTH_SHORT).show();
                        show.setText( "你选中了" + items[which] + "这首歌");
                    }
                });
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    protected void multChoice(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("多选列表对话框")
                .setIcon(R.drawable.ic_launcher)
                .setMultiChoiceItems(items, new boolean[]{false, true, true}, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {

                    }
                });
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    protected void customList(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("多选列表对话框")
                .setIcon(R.drawable.ic_launcher)
                .setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items), null);
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    protected void customView(View v) {
        TableLayout loginForm = (TableLayout) getLayoutInflater().inflate(R.layout.login, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("自定义View对话框")
                .setIcon(R.drawable.ic_launcher)
                .setView(loginForm);
        setPositiveButton(builder);
        setNegativeButton(builder)
                .create()
                .show();
    }

    private AlertDialog.Builder setPositiveButton(AlertDialog.Builder builder) {
        return builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                show.setText("单击了【确定】按钮");
            }
        });
    }

    private AlertDialog.Builder setNegativeButton(AlertDialog.Builder builder) {
        return builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                show.setText("单击了【取消】按钮");
            }
        });
    }
}
