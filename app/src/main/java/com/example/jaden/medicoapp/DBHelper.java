package com.example.jaden.medicoapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "myDb";
    public static final String PAT_TABLENAME = "patient_schedule";
    public static final String ITEMID = "id";
    public static final String PAT_DATE = "date";
    public static final String PAT_SLOT = "slot";

    /*-------------doctor make plan table---------------------------*/
    public static final String DOC_TABLENAME = "doctor_schedule";
    public static final String DOCID = "doc_id";
    public static final String DOC_DATE = "doc_date";
    public static final String DOC_SLOT1 = "doc_slot1";
    public static final String DOC_SLOT2 = "doc_slot2";
    public static final String DOC_SLOT3 = "doc_slot3";
    public static final String DOC_SLOT4 = "doc_slot4";
    public static final String DOC_SLOT5 = "doc_slot5";
    public static final String DOC_SLOT6 = "doc_slot6";
    public static final String DOC_SLOT7 = "doc_slot7";
    public static final String DOC_SLOT8 = "doc_slot8";
    public static final String DOC_SLOT9 = "doc_slot9";
    public static final String DOC_SLOT10 = "doc_slot10";
    public static final String DOC_SLOT11 = "doc_slot11";
    public static final String DOC_SLOT12 = "doc_slot12";

    public static final int VERSION = 1;

    private Context mContext = null;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createPatTable = " CREATE TABLE " + PAT_TABLENAME + "("
                + ITEMID + " INTEGER PRIMARY KEY, " + PAT_DATE + " TEXT, " + PAT_SLOT +  " TEXT)";
        sqLiteDatabase.execSQL(createPatTable);

        /*-------------doctor make plan table---------------------------*/
        String createDocTable = " CREATE TABLE " + DOC_TABLENAME + "("
                + DOCID + " INTEGER PRIMARY KEY, "
                + DOC_DATE + " TEXT, "
                + DOC_SLOT1 + " TEXT, "
                + DOC_SLOT2 + " TEXT, "
                + DOC_SLOT3 + " TEXT, "
                + DOC_SLOT4 + " TEXT, "
                + DOC_SLOT5 + " TEXT, "
                + DOC_SLOT6 + " TEXT, "
                + DOC_SLOT7 + " TEXT, "
                + DOC_SLOT8 + " TEXT, "
                + DOC_SLOT9 + " TEXT, "
                + DOC_SLOT10 + " TEXT, "
                + DOC_SLOT11 + " TEXT, "
                + DOC_SLOT12 +  " TEXT)";
        sqLiteDatabase.execSQL(createDocTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + PAT_TABLENAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DOC_TABLENAME);
        onCreate(sqLiteDatabase);
    }
}
