package com.mountreachsolution.farmlabourscheduling.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Postwork extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FarmWorkDB"; // Database name
    private static final int DATABASE_VERSION = 2; // Updated database version

    private static final String TABLE_NAME = "postwork"; // Table name
    private static final String COLUMN_ID = "id"; // Column for auto-incremented ID
    private static final String COLUMN_NAME = "name"; // Column for worker name
    private static final String COLUMN_NUMBER = "number"; // Column for phone number
    private static final String COLUMN_ADDRESS = "address"; // Column for address
    private static final String COLUMN_WORKING_SHORT = "working_short"; // Column for work in short
    private static final String COLUMN_CROP_NAME = "crop_name"; // Column for crop name
    private static final String COLUMN_WAGES = "wages"; // Column for wages
    private static final String COLUMN_IMAGE = "image"; // Column for image URI or path
    private static final String COLUMN_START_TIME = "start_time"; // Column for start time
    private static final String COLUMN_END_TIME = "end_time"; // Column for end time
    private static final String COLUMN_DATE = "date"; // Column for date
    private static final String COLUMN_LABOUR_REQUIRED = "labour_required"; // New column for labour required

    public Postwork(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL query to create the postwork table with the new column for labour_required
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
                COLUMN_LABOUR_REQUIRED + " TEXT)"; // Adding new column here

        // Execute the SQL query
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle upgrading the database and adding new columns if necessary
        if (oldVersion < 2) {
            // Alter table to add the new column if the version is less than 2
            db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COLUMN_LABOUR_REQUIRED + " TEXT");
        }
    }

    public void insertPostwork(String name, String number, String address, String workingShort,
                               String cropName, String wages, String image, String startTime,
                               String endTime, String date, String labourRequired) {
        // Get writable database
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a ContentValues object to insert data
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
        contentValues.put(COLUMN_LABOUR_REQUIRED, labourRequired); // Adding the new field

        // Insert the data into the table
        db.insert(TABLE_NAME, null, contentValues);

        // Close the database connection
        db.close();
    }
}
