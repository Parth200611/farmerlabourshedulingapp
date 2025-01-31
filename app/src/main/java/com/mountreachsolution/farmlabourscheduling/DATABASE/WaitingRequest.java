package com.mountreachsolution.farmlabourscheduling.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.SQLClientInfoException;

public class WaitingRequest extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "waitingwork.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "waitingwork";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MOBILE_NO = "mobileno";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_WORK = "work";
    private static final String COLUMN_WAGES = "wages";
    private static final String COLUMN_START_TIME = "starttime";
    private static final String COLUMN_END_TIME = "endtime";
    private static final String COLUMN_WORK_DATE = "workdate";
    private static final String COLUMN_CROP = "crop";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_LABOUR = "labour";
    private static final String COLUMN_LABOUR_NAME = "labourname";
    private static final String COLUMN_LABOUR_NUMBER = "labournumber";
    private static final String COLUMN_LABOUR_ADDRESS = "labouraddress";
    private static final String COLUMN_LABOUR_SKILL = "labourskill";
    private static final String COLUMN_LABOUR_AADHAR = "labouradhar";
    private static final String COLUMN_LABOUR_AGE = "labourage";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " TEXT, "
            + COLUMN_MOBILE_NO + " TEXT, "
            + COLUMN_ADDRESS + " TEXT, "
            + COLUMN_WORK + " TEXT, "
            + COLUMN_WAGES + " TEXT, "
            + COLUMN_START_TIME + " TEXT, "
            + COLUMN_END_TIME + " TEXT, "
            + COLUMN_WORK_DATE + " TEXT, "
            + COLUMN_CROP + " TEXT, "
            + COLUMN_IMAGE + " TEXT, "
            + COLUMN_LABOUR + " TEXT, "
            + COLUMN_LABOUR_NAME + " TEXT, "
            + COLUMN_LABOUR_NUMBER + " TEXT, "
            + COLUMN_LABOUR_ADDRESS + " TEXT, "
            + COLUMN_LABOUR_SKILL + " TEXT, "
            + COLUMN_LABOUR_AADHAR + " TEXT, "
            + COLUMN_LABOUR_AGE + " TEXT);";

    public WaitingRequest(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);  // Execute the SQL statement to create the table
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);  // Recreate the table if it already exists (on upgrade)
    }
    public long insertData(String name, String mobileno, String address, String work, String wages, String starttime, String endtime, String workdate,
                           String crop, String image, String labour, String labourname, String labournumber, String labouraddress, String labourskill,
                           String labouradhar, String labourage) {
        SQLiteDatabase db = this.getWritableDatabase();  // Get writable database
        ContentValues contentValues = new ContentValues();  // ContentValues to hold the data

        // Put the values into the ContentValues object
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_MOBILE_NO, mobileno);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_WORK, work);
        contentValues.put(COLUMN_WAGES, wages);
        contentValues.put(COLUMN_START_TIME, starttime);
        contentValues.put(COLUMN_END_TIME, endtime);
        contentValues.put(COLUMN_WORK_DATE, workdate);
        contentValues.put(COLUMN_CROP, crop);
        contentValues.put(COLUMN_IMAGE, image);
        contentValues.put(COLUMN_LABOUR, labour);
        contentValues.put(COLUMN_LABOUR_NAME, labourname);
        contentValues.put(COLUMN_LABOUR_NUMBER, labournumber);
        contentValues.put(COLUMN_LABOUR_ADDRESS, labouraddress);
        contentValues.put(COLUMN_LABOUR_SKILL, labourskill);
        contentValues.put(COLUMN_LABOUR_AADHAR, labouradhar);
        contentValues.put(COLUMN_LABOUR_AGE, labourage);

        // Insert data and return the row ID of the inserted data
        return db.insert(TABLE_NAME, null, contentValues);
    }
}
