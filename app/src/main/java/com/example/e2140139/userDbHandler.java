package com.example.e2140139;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class userDbHandler extends SQLiteOpenHelper {

    //USER DATA
    public static final String DATABASE_NAME = "campaigntrack.db";
    public static final String TBL_NAME = "user_data";
    public static final String col_1 = "ID";
    public static final String col_2 = "USERNAME";
    public static final String col_3 = "PASSWORD";

    // SERVEY DATA
    public static final String TBLSURVEY = "survey_data";
    public static final String cols_1 = "ID";
    public static final String cols_2 = "LATITUDE";
    public static final String cols_3 = "LONGITUDE";
    public static final String cols_4 = "PARTY";
    public static final String cols_5 = "DATETIME";
    public static final String cols_6 = "NOOFVOTES";
    public static final String cols_7 = "EXVOTES";
    public static final String cols_8 = "FEEDBACKTYPE";
    public static final String cols_9 = "MOSTSUPPORTPARTY";
    public static final String cols_10 = "ADDDETAILS";


    public userDbHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TBL_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,USERNAME TEXT,PASSWORD TEXT)");
        db.execSQL("CREATE TABLE " + TBLSURVEY + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, LATITUDE TEXT, LONGITUDE TEXT, PARTY TEXT, DATETIME TEXT, NOOFVOTES INTEGER, EXVOTES INTEGER, FEEDBACKTYPE TEXT, MOSTSUPPORTPARTY TEXT, ADDDETAILS TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_NAME);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TBLSURVEY);
        onCreate(db);
    }

    public boolean RegisterUser(String uname, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, uname);
        contentValues.put(col_3, password);
        long result = db.insert(TBL_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean loginUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase(); // Use getReadableDatabase for SELECT queries
        Cursor cursor = db.query(TBL_NAME, null, "USERNAME=? AND PASSWORD=?", new String[]{username, password}, null, null, null);
        boolean isUserFound = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return isUserFound;
    }

    //
    public boolean insertData(String lat, String lon, String party, String date, int nov, int noev, String ft, String msp, String add) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(cols_2, lat);
        contentValues.put(cols_3, lon);
        contentValues.put(cols_4, party);
        contentValues.put(cols_5, date);
        contentValues.put(cols_6, nov);
        contentValues.put(cols_7, noev);
        contentValues.put(cols_8, ft);
        contentValues.put(cols_9, msp);
        contentValues.put(cols_10, add);
        long result = db.insert(TBLSURVEY, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public int countTotalVotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        int sum = 0;

        // Replace 'table_name' and 'column_name' with your actual table and column names.
        String query = "SELECT SUM(EXVOTES) FROM "+TBLSURVEY;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return sum;
    }
    public int countUnpVotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        int sum = 0;

        // Replace 'table_name' and 'column_name' with your actual table and column names.
        String query = "SELECT SUM(EXVOTES) FROM survey_data WHERE PARTY='UNP';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return sum;
    }
    public int countSLPFVotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        int sum = 0;

        // Replace 'table_name' and 'column_name' with your actual table and column names.
        String query = "SELECT SUM(EXVOTES) FROM survey_data WHERE PARTY='SLFP';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return sum;
    }
    public int countJVPFVotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        int sum = 0;

        // Replace 'table_name' and 'column_name' with your actual table and column names.
        String query = "SELECT SUM(EXVOTES) FROM survey_data WHERE PARTY='JVP';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return sum;
    }
    public int countCPSLVotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        int sum = 0;

        // Replace 'table_name' and 'column_name' with your actual table and column names.
        String query = "SELECT SUM(EXVOTES) FROM survey_data WHERE PARTY='CPSL';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return sum;
    }
    public int countSETMVotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        int sum = 0;

        // Replace 'table_name' and 'column_name' with your actual table and column names.
        String query = "SELECT SUM(EXVOTES) FROM survey_data WHERE PARTY='SETM';";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            sum = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return sum;
    }
    @SuppressLint("Range")
    public String leadingParty(){
        SQLiteDatabase db = this.getReadableDatabase();
        String leadingParty = null;
        String queryString = "SELECT PARTY, SUM(EXVOTES) AS TOTAL_VOTES FROM " + TBLSURVEY + " GROUP BY PARTY ORDER BY TOTAL_VOTES DESC LIMIT 1;";
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            leadingParty = cursor.getString(cursor.getColumnIndex("PARTY"));
        }
        cursor.close();
        db.close();
        return leadingParty;
    }
    public Cursor DisplayData(String lat, String lon) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM survey_data WHERE LATITUDE=? AND LONGITUDE=?";
        Cursor result = db.rawQuery(query, new String[]{lat, lon});
        return result;
    }
}
