package com.cookandroid.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ListDBHelper extends SQLiteOpenHelper {

    public ListDBHelper(@Nullable Context context) {
        super(context, "listDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE listTBL(year INTEGER, month INTEGER, date INTEGER, todo VARCHAR(100), chk INTEGER);";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS listTBL");
        onCreate(sqLiteDatabase);
    }
}
