package com.mountreachsolution.farmlabourscheduling.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Workrequestdatabse extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "farmlabour.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "rwquestwork";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FARMER_NAME = "farmername";
    private static final String COLUMN_FARMER_MOBILE = "farmermobileno";
    private static final String COLUMN_FARMER_ADDRESS = "farmeraddress";
    private static final String COLUMN_WORK_NAME = "workname";
    private static final String COLUMN_WAGES = "wages";
    private static final String COLUMN_START_TIME = "starttime";
    private static final String COLUMN_END_TIME = "endtime";
    private static final String COLUMN_WORK_DATE = "workdate";
    private static final String COLUMN_CROP = "crop";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_LABOUR = "labour";
    private static final String COLUMN_LABOUR_NAME = "labourname";
    private static final String COLUMN_LABOUR_MOBILE = "labourmobileno";
    private static final String COLUMN_LABOUR_ADDRESS = "labouraddress";
    private static final String COLUMN_LABOUR_SKILL = "labourskill";
    private static final String COLUMN_LABOUR_AGE = "labourage";
    private static final String COLUMN_LABOUR_AADHAR = "labouradhrno";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_FARMER_NAME + " TEXT, "
            + COLUMN_FARMER_MOBILE + " TEXT, "
            + COLUMN_FARMER_ADDRESS + " TEXT, "
            + COLUMN_WORK_NAME + " TEXT, "
            + COLUMN_WAGES + " TEXT, "
            + COLUMN_START_TIME + " TEXT, "
            + COLUMN_END_TIME + " TEXT, "
            + COLUMN_WORK_DATE + " TEXT, "
            + COLUMN_CROP + " TEXT, "
            + COLUMN_IMAGE + " TEXT, "
            + COLUMN_LABOUR + " TEXT, "
            + COLUMN_LABOUR_NAME + " TEXT, "
            + COLUMN_LABOUR_MOBILE + " TEXT, "
            + COLUMN_LABOUR_ADDRESS + " TEXT, "
            + COLUMN_LABOUR_SKILL + " TEXT, "
            + COLUMN_LABOUR_AGE + " TEXT, "
            + COLUMN_LABOUR_AADHAR + " TEXT);";

    public Workrequestdatabse(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public long insertData(String farmerName, String farmerMobile, String farmerAddress, String workName, String wages, String startTime, String endTime, String workDate, String crop, String image, String labour, String labourName, String labourMobile, String labourAddress, String labourSkill,String labourage, String labourAadhar) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FARMER_NAME, farmerName);
        values.put(COLUMN_FARMER_MOBILE, farmerMobile);
        values.put(COLUMN_FARMER_ADDRESS, farmerAddress);
        values.put(COLUMN_WORK_NAME, workName);
        values.put(COLUMN_WAGES, wages);
        values.put(COLUMN_START_TIME, startTime);
        values.put(COLUMN_END_TIME, endTime);
        values.put(COLUMN_WORK_DATE, workDate);
        values.put(COLUMN_CROP, crop);
        values.put(COLUMN_IMAGE, image);
        values.put(COLUMN_LABOUR, labour);
        values.put(COLUMN_LABOUR_NAME, labourName);
        values.put(COLUMN_LABOUR_MOBILE, labourMobile);
        values.put(COLUMN_LABOUR_ADDRESS, labourAddress);
        values.put(COLUMN_LABOUR_SKILL, labourSkill);
        values.put(COLUMN_LABOUR_AGE, labourage);
        values.put(COLUMN_LABOUR_AADHAR, labourAadhar);

        return db.insert(TABLE_NAME, null, values);
    }
}
