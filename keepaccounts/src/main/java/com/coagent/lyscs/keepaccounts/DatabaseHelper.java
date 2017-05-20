package com.coagent.lyscs.keepaccounts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lyscs on 2017/5/12.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String JERRY_COST = "jerry_cost";
    public static final String COST_TITLE = "cost_title";
    public static final String COST_DATE = "cost_date";
    public static final String COST_MONEY = "cost_money";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, "daily", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + JERRY_COST + "(" +
                "id integer primary key, " +
                COST_TITLE + " varchar, " +
                COST_DATE + " varchar, " +
                COST_MONEY + " varchar)";
        db.execSQL(sql);
    }

    /**
     * 将日常单笔开销数据存到数据库
     * @param bean
     */
    public void insertCost(CostBean bean){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COST_TITLE, bean.costTitle);
        cv.put(COST_DATE, bean.costDate);
        cv.put(COST_MONEY, bean.costMoney);
        database.insert(JERRY_COST, null, cv);
    }

    /**
     * 返回数据表中的全部数据
     */
    public Cursor getAllCostData(){
        SQLiteDatabase database = getWritableDatabase();
        return database.query(JERRY_COST,null, null, null,null,null, COST_DATE + " ASC");
    }

    /**
     * 清空全部数据
     */
    public void deleteAllCostData(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(JERRY_COST, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
