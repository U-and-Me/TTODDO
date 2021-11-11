package com.cookandroid.todolist;

import android.content.Context;

public class DBCreate {
    static DBHelper MemHelper;
    DBCreate(Context context){
        MemHelper = new DBHelper(context);
    }
}
