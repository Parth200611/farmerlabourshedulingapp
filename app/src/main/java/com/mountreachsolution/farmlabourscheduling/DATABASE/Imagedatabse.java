package com.mountreachsolution.farmlabourscheduling.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Imagedatabse extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserImageDB";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "userimage";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_IMAGE = "image";

    public Imagedatabse(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NUMBER + " TEXT, " +
                COLUMN_IMAGE + " TEXT)";
        db.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertImagePath(String mobileNumber, String imagePath) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NUMBER, mobileNumber);
        values.put(COLUMN_IMAGE, imagePath);

        // Use REPLACE to update if mobile number already exists
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }
    public String getImagePathByMobileNumber(String mobileNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        String imagePath = null;
        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NUMBER + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_NUMBER + " = ?", new String[]{mobileNumber});
        if (cursor.moveToFirst()) {
            imagePath = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return imagePath;
    }
    public String getImageByNumber(String number) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to fetch image for the provided number
        String selection = COLUMN_NUMBER + " = ?";
        String[] selectionArgs = { number };

        Cursor cursor = db.query(
                TABLE_NAME,     // Table name
                new String[]{COLUMN_IMAGE}, // Columns to return (only image column)
                selection,      // WHERE clause
                selectionArgs,  // Values for WHERE clause
                null,            // Grouping rows (null means no grouping)
                null,            // Filtering rows (null means no filtering)
                null             // Sorting order (null means no sorting)
        );

        // Check if cursor has data and return the image URI (if found)
        if (cursor != null && cursor.moveToFirst()) {
            String imageUri = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE));
            cursor.close(); // Close cursor after use
            return imageUri; // Return the image URI
        } else {
            cursor.close();
            return null; // Return null if no data is found
        }
    }

}