package com.mountreachsolution.farmlabourscheduling.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminRegistration extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FarmLabourScheduling.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ADMIN = "admin_data";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";

    public AdminRegistration(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableQuery = "CREATE TABLE " + TABLE_ADMIN + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_NUMBER + " TEXT NOT NULL, " +
                COLUMN_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_ROLE + " TEXT NOT NULL);";
        sqLiteDatabase.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ADMIN);
        onCreate(sqLiteDatabase);
    }

    public long insertAdminData(String name, String number, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_NUMBER, number);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, role);

        long result = db.insert(TABLE_ADMIN, null, values);
        db.close();

        return result;
    }

    public String loginUser(String mobile, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Fix: Use correct table and column names
        String query = "SELECT " + COLUMN_ROLE + ", " + COLUMN_PASSWORD + " FROM " + TABLE_ADMIN + " WHERE " + COLUMN_NUMBER + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{mobile});

        if (cursor != null && cursor.moveToFirst()) {
            // Retrieve the stored password and role from the database
            String storedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            String role = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));

            cursor.close();
            db.close();

            if (storedPassword.equals(password)) {
                // If passwords match, return the role
                return role;
            } else {
                // If the password doesn't match, return a specific message
                return "INCORRECT_PASSWORD";
            }
        }

        if (cursor != null) {
            cursor.close();
        }
        db.close();
        return null;  // Return null if no matching user is found
    }
}
