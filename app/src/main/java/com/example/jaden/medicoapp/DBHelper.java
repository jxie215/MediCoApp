package com.example.jaden.medicoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "myDb";
    public static final String TABLENAME = "dschedule";
    public static final String ITEMID = "id";
    public static final String DATE = "date";
    public static final String SLOT = "slot";
    public static final int VERSION = 1;

    private Context mContext = null;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = " CREATE TABLE " + TABLENAME + "("
                + ITEMID + " INTEGER PRIMARY KEY, " + DATE + " TEXT, " + SLOT +  " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLENAME);
        onCreate(sqLiteDatabase);
    }
}
