package com.mountreachsolution.farmlabourscheduling.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Postwork extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FarmWorkDB";
    private static final int DATABASE_VERSION = 2;

    private static final String TABLE_NAME = "postwork";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_WORKING_SHORT = "working_short";
    private static final String COLUMN_CROP_NAME = "crop_name";
    private static final String COLUMN_WAGES = "wages";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_START_TIME = "start_time";
    private static final String COLUMN_END_TIME = "end_time";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_LABOUR_REQUIRED = "labour_required";

    public Postwork(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_NUMBER + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_WORKING_SHORT + " TEXT, " +
                COLUMN_CROP_NAME + " TEXT, " +
                COLUMN_WAGES + " TEXT, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_START_TIME + " TEXT, " +
                COLUMN_END_TIME + " TEXT, " +
                COLUMN_DATE + " TEXT, " +
                COLUMN_LABOUR_REQUIRED + " TEXT)";

        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 2) {

            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_LABOUR_REQUIRED + " TEXT");
        }
    }

    public void insertPostwork(String name, String number, String address, String workingShort,
                               String cropName, String wages, String image, String startTime,
                               String endTime, String date, String labourRequired) {

        SQLiteDatabase db = this.getWritableDatabase();


        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME, name);
        contentValues.put(COLUMN_NUMBER, number);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_WORKING_SHORT, workingShort);
        contentValues.put(COLUMN_CROP_NAME, cropName);
        contentValues.put(COLUMN_WAGES, wages);
        contentValues.put(COLUMN_IMAGE, image);
        contentValues.put(COLUMN_START_TIME, startTime);
        contentValues.put(COLUMN_END_TIME, endTime);
        contentValues.put(COLUMN_DATE, date);
        contentValues.put(COLUMN_LABOUR_REQUIRED, labourRequired);


        db.insert(TABLE_NAME, null, contentValues);


        db.close();
    }
    public Cursor getAllWorkPostings() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public Cursor getWorkPostingById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }
    public String getImageById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_IMAGE + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID + " = ?", new String[]{id});

        if (cursor != null && cursor.moveToFirst()) {
            String imagePath = cursor.getString(0);
            cursor.close();
            return imagePath;
        }

        return null;
    }
    public Cursor getWorkPostingByMobile(String mobileNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NUMBER + " = ?", new String[]{mobileNumber});
    }
    public void deleteWorkPostingById(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
        db.close();
    }
    public void updateWagesAndLabour(String id, String newWages, String newLabourRequired) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_WAGES, newWages);
        contentValues.put(COLUMN_LABOUR_REQUIRED, newLabourRequired);

        db.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{id});
        db.close();
    }
    public void clearData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.execSQL("VACUUM"); // Reset auto-increment counter
        db.close();
    }



}
