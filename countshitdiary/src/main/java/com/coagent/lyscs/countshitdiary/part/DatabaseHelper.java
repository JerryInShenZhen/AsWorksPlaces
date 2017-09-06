package com.coagent.lyscs.countshitdiary.part;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lyscs on 2017/5/26.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String SHIT_DIARY = "shit_diary";
    public static final String XIN_SHIT = "xin_shit";
    public static final String SHIT_DATA = "shit_data";
    public static final String SHIT_KG = "shit_kg";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context) {
        super(context, SHIT_DIARY, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists " + XIN_SHIT + "(" +
                "id integer primary key, " +
                SHIT_DATA + " varchar, " +
                SHIT_KG + " varchar)";
        db.execSQL(sql);
    }

    /**
     * 将一个bean加入到数据可
     * @param bean
     */
    public void insertShit(ShitBean bean){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(SHIT_DATA, bean.shitData);
        cv.put(SHIT_KG, bean.shitKg);
        db.insert(XIN_SHIT, null, cv);
        db.close();
    }

    /**
     * 返回XIN_SHIT表中的全部数据
     */
    public Cursor getAllShitData(){
        SQLiteDatabase db = getWritableDatabase();
        return db.query(XIN_SHIT, null, null,null,null,null, SHIT_DATA + " ASC");
    }

    public void deleteAllShitData(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(XIN_SHIT, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
