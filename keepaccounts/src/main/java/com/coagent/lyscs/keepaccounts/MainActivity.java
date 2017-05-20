package com.coagent.lyscs.keepaccounts;

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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<CostBean> mCostBeenList;
    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mCostBeenList = new ArrayList<CostBean>();
        mDatabaseHelper = new DatabaseHelper(this);
        ListView costList = (ListView) findViewById(R.id.lv_main);
        initListData();
        adapter = new CostListAdapter(this, mCostBeenList);
        costList.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewDialog = inflater.inflate(R.layout.new_cost_data, null);
                final EditText cost_title = (EditText) viewDialog.findViewById(R.id.et_cost_title);
                final EditText cost_money = (EditText) viewDialog.findViewById(R.id.et_cost_money);
                final DatePicker cost_date = (DatePicker) viewDialog.findViewById(R.id.dp_cost_date);
                builder.setView(viewDialog);
                builder.setTitle("新开销");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CostBean costBean = new CostBean();
                        costBean.costTitle = cost_title.getText().toString();
                        costBean.costMoney = cost_money.getText().toString();
                        costBean.costDate = cost_date.getYear() + "-" + (cost_date.getMonth() + 1) +
                                "-" + cost_date.getDayOfMonth();
                        mDatabaseHelper.insertCost(costBean);
                        mCostBeenList.add(costBean);
                        adapter.notifyDataSetChanged();  //刷新listview的数据
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();    //对话框内容弄好还要show出来
            }
        });
    }

    private void initListData() {
        CostBean bean = new CostBean();
        /*bean.costTitle = "eat";
        bean.costDate = "2017-05-12, 10:11";
        bean.costMoney = "20.2";
        for (int i=0; i<10; i++){
            mCostBeenList.add(bean);
        }*/

        /*mDatabaseHelper.deleteAllCostData();    //先清除之前的再插入测试的
        for (int i = 0; i < 15; i++) {
            bean.costTitle = "eat" + i;
            bean.costDate = "2017-05-12, 10:11";
            bean.costMoney = "20.2";
            mDatabaseHelper.insertCost(bean);
        }*/

        Cursor cursor = mDatabaseHelper.getAllCostData();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CostBean costBean = new CostBean();
                costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeenList.add(costBean);
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_chart) {
            Intent intent = new Intent(MainActivity.this, ChartsActivity.class);
            intent.putExtra("costList", (Serializable) mCostBeenList);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
