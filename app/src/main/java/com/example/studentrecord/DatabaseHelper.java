package com.example.studentrecord;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

public class DatabaseHelper extends SQLiteOpenHelper {

    public final static String DATABASE_NAME = "MyStudent.db";
    public final static String TABLE_NAME = "MyStudent_table";
    public final static String COLMN_1 = "ID";
    public final static String COLMN_2 = "NAME";
    public final static String COLMN_3 = "EMAIL";
    public final static String COLMN_4 = "COURSE_COUNT";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " NAME TEXT," +
                " EMAIL TEXT," +
                " COURSE_COUNT INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String name, String email, String courseCount){
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLMN_2,name);
        contentValues.put(COLMN_3,email);
        contentValues.put(COLMN_4,courseCount);

        // Insert the new row, returning the primary key value of the new row
        long result = db.insert(TABLE_NAME,null,contentValues);

        if(result == -1){
            return false;
        } else{
            return true;
        }

    }

    public boolean updateData(String id,String name, String email, String courseCount){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLMN_1,id);
        contentValues.put(COLMN_2,name);
        contentValues.put(COLMN_3,email);
        contentValues.put(COLMN_4,courseCount);

        db.update(TABLE_NAME,contentValues,"ID=?",new String[]{id});
        return true;

    }

    public Cursor getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM "+ TABLE_NAME + " WHERE ID='"+id+"'";
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.delete(TABLE_NAME,"ID=?",new String[]{id});

    }

    public Cursor getAllData(){

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_NAME,null);
        return cursor;
    }

}
