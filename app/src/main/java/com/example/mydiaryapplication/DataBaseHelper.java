package com.example.mydiaryapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(@Nullable Context context) {
        super(context, "writing.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE WRITING_TABLE (ID INTEGER PRIMARY KEY AUTOINCREMENT, WRITING_TEXT TEXT, WRITING_DATE DATE)";

        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean add(Writing writing){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("WRITING_TEXT", writing.text);
        cv.put("WRITING_DATE",writing.date.toString());

        long insert = db.insert("WRITING_TABLE",null,cv);
        if(insert == -1){
            return false;
        }
        else{
            return  true;
        }
    }

    public List<Writing> getAllWritings(){
        List<Writing> returnList = new ArrayList<>();

        String queryString = "SELECT * FROM WRITING_TABLE";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String text = cursor.getString(1);
                String dateString = cursor.getString(2);
                LocalDate date = LocalDate.parse(dateString);
                Writing writing = new Writing(text, date, id);
                returnList.add(writing);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return returnList;
    }

    public boolean deleteWriting(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("WRITING_TABLE", "ID = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }
}
