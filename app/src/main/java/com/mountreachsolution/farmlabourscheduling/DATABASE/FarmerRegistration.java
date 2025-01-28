package com.mountreachsolution.farmlabourscheduling.DATABASE;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;



public class FarmerRegistration extends SQLiteOpenHelper {
    private static final String DATABASENAME = "UseerREfgisterdatabse";
    private static final int DATABASEVERSION = 1;

    private static final String TABLE_USER = "UserREgister";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_MOBILE = "mobileno";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_ADHAR = "adharno";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_ROLE = "role";

    public FarmerRegistration(@Nullable Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_MOBILE + " TEXT UNIQUE, " +  // Added UNIQUE constraint
                COLUMN_AGE + " TEXT , " +
                COLUMN_ADDRESS + " TEXT, " +
                COLUMN_ADHAR + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_ROLE + " TEXT" +
                ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);

    }

    public void UserRegister(String name, String mobileno, String address, String age, String adharno, String password, String role) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_MOBILE, mobileno);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_AGE, age);
        values.put(COLUMN_ADHAR, adharno);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_ROLE, role);
        db.insert(TABLE_USER, null, values);
    }
   public String loginUser(String mobile, String password) {
    SQLiteDatabase db = this.getReadableDatabase();
    String query = "SELECT " + COLUMN_ROLE + ", " + COLUMN_PASSWORD + " FROM " + TABLE_USER + " WHERE " + COLUMN_MOBILE + " = ?";
    Cursor cursor = db.rawQuery(query, new String[]{mobile});

    if (cursor != null && cursor.moveToFirst()) {
        String storedPassword = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
        String role = cursor.getString(cursor.getColumnIndex(COLUMN_ROLE));

        cursor.close();
        db.close();

        if (storedPassword.equals(password)) {
            return role; // Return role if password matches
        } else {
            return null; // Return null if password is incorrect
        }
    }

    if (cursor != null) {
        cursor.close();
    }
    db.close();
    return null; // Return null if user doesn't exist
}


    // Check if a user exists by mobile number
    public boolean isUserExists(String mobile) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_USER + " WHERE " + COLUMN_MOBILE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{mobile});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();
        return exists;
    }
    public Cursor getUserDataByMobile(String mobile) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_MOBILE + " = ?";
        return db.rawQuery(query, new String[]{mobile});
    } public Cursor getUsersByRole(String role) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_ROLE + " = ?";
        return db.rawQuery(query, new String[]{role});
    }
    public Cursor getSelectedWorkPostingsByRole(String role) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_MOBILE + ", " + COLUMN_AGE + ", " + COLUMN_ADDRESS +
                " FROM " + TABLE_USER + " WHERE " + COLUMN_ROLE + " = ?";
        return db.rawQuery(query, new String[]{role});
    }
    public Cursor getUserById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + ", " + COLUMN_MOBILE + ", " + COLUMN_AGE + ", " + COLUMN_ADDRESS +
                " FROM " + TABLE_USER + " WHERE " + COLUMN_ID + " = ?";
        return db.rawQuery(query, new String[]{String.valueOf(id)});
    }




}
