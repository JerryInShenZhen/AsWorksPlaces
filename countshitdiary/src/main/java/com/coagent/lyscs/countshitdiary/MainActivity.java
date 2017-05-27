package com.coagent.lyscs.countshitdiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.coagent.lyscs.countshitdiary.part.DatabaseHelper;
import com.coagent.lyscs.countshitdiary.part.ShitBean;
import com.coagent.lyscs.countshitdiary.part.ShitListAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ShitBean> mShitBeanList;
    private DatabaseHelper mDatabaseHelper;
    private ShitListAdapter adapter;
    private ListView shitListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mShitBeanList = new ArrayList<ShitBean>();
        mDatabaseHelper = new DatabaseHelper(this);
        shitListView = (ListView) findViewById(R.id.lv_main);
        initListData();
        adapter = new ShitListAdapter(this, mShitBeanList);
        shitListView.setAdapter(adapter);


        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewDialog = inflater.inflate(R.layout.new_shit_data, null);

                final EditText shitKg = (EditText) viewDialog.findViewById(R.id.et_shit_kg);
                final DatePicker shitDate = (DatePicker) viewDialog.findViewById(R.id.dp_shit_date);
                builder.setView(viewDialog);
                builder.setTitle("新产出");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ShitBean bean = new ShitBean();
                        bean.shitKg = shitKg.getText().toString();
                        bean.shitData = shitDate.getYear() + "-" +
                                (shitDate.getMonth() + 1) + "-" + shitDate.getDayOfMonth();
                        mDatabaseHelper.insertShit(bean);
                        mShitBeanList.add(bean);
                        adapter.notifyDataSetChanged(); //刷新listView显示
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
            }
        });


    }

    /**
     * 获取数据库中已存入数据
     */
    private void initListData() {
        Cursor cursor = mDatabaseHelper.getAllShitData();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                ShitBean bean = new ShitBean();
                bean.shitData = cursor.getString(cursor.getColumnIndex("shit_data"));
                bean.shitKg = cursor.getString(cursor.getColumnIndex("shit_kg"));
                mShitBeanList.add(bean);
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_chart){
            Intent intent = new Intent(MainActivity.this, ChartsActivity.class);
            intent.putExtra("shitData", (Serializable) mShitBeanList);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
