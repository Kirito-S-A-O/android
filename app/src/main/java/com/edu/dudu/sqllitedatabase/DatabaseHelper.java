package com.edu.dudu.sqllitedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.edu.dudu.Constants;
import com.edu.dudu.RecyclerView.LinearAdapter;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    //创建数据库
    public DatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //当数据库创建是回调
        Log.d(TAG, "创建数据库...");
        //创建字段
        //sql create table table_name (_id integer ,name varchar,password varchar);
        //创建数据库 id 自动加一 黑白子成绩不为空 默认为0
        String sql = "create table " + Constants.TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT,name varchar,password varchar,WhiteWin integer not null default 0,BlackWin integer not null default 0)";
        db.execSQL(sql);

        //添加一些数据
        for(int i=0;i<10;i++){

            String sql1  = "insert into "+ Constants.TABLE_NAME+"(name,password,WhiteWin,BlackWin) values(?,?,?,?)";
           //(数据类型)(最小值+Math.random()*(最大值-最小值+1))
            db.execSQL(sql1,new Object[]{"bot"+i,"admin",(int)(30+Math.random()*(150-20+1)),(int)(30+Math.random()*(150-20+1))});
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级数据库的回调
        Log.d(TAG, "升级数据库...");

        //sql ：alter table table_name add 字段 属性
        String sql;
        switch(oldVersion){
            case 1:
                //
                break;
            case 2:
                //添加WhiteWin字段
                // sql = "alter table "+Constants.TABLE_NAME+" add WhiteWin integer";
                //db.execSQL(sql);
                break;
            case 3:
//                sql = "alter table "+Constants.TABLE_NAME+" add WhiteWin integer";
//                db.execSQL(sql);
//                sql = "alter table "+Constants.TABLE_NAME+" add BlackWin integer";
//                db.execSQL(sql);
                break;
                //添加BlackWin字段
        }


    }
}
