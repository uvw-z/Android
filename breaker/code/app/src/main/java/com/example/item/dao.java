package com.example.item;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Range;

import java.util.List;

public class dao {
    myHelper myhelper;
    SQLiteDatabase db;
    Cursor c;
    Context context;

    public dao(Context context) {
        this.context = context;
        this.myhelper=new myHelper(context,"db",null,1);
    }

    void select_all(List<swt> info){
        db=myhelper.getReadableDatabase();
        c=db.rawQuery("select * from infoo",null);

        while(c.moveToNext()){
            swt swt=new swt("","","","",0);
            swt.setName(c.getString(0));
            swt.setI(c.getString(1));
            swt.setU(c.getString(2));
            swt.setT(c.getString(3));
            swt.setFlag(c.getInt(4));
            info.add(swt);
        }
    }

    void new_swt(swt swt){
        db=myhelper.getWritableDatabase();
        db.execSQL("insert into infoo values (?,?,?,?,?)",new Object[]{swt.getName(),swt.getI(),swt.getU(),swt.getT(),swt.getFlag()});
    }

    void update_swt(String name,String i,String u,String t){
        db=myhelper.getWritableDatabase();
        db.execSQL("update infoo set i=?,u=?,t=? where name=?",new String[]{i,u,t,name});
    }
    void cb(String n,int flag){
        db=myhelper.getWritableDatabase();
        db.execSQL("update infoo set flag=? where name=?",new Object[]{flag,n});
    }
}
