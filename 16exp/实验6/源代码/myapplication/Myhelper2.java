package com.example.myapplication;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.myapplication.MainActivity;

public class Myhelper2 extends SQLiteOpenHelper {
    //由于父类没有无参构造参数，所以子类必须指定调用弗雷德哪个有参的构造函数
    public Myhelper2(Context context) {
        super(context, "product.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("MyHelper","OnCreate");
        db.execSQL("create table account(_id integer primary key autoincrement,name varchar(20),balance integer)");//id主键，商品名称列，金额列
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("MyHelper","OnUpgrade");
    }
}
