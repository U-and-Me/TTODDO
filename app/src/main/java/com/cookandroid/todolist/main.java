package com.cookandroid.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.GregorianCalendar;

public class main extends AppCompatActivity {

    TextView txtName, txtDate;
    Button btnList, btnCal, btnLeft, btnRight;

    SQLiteDatabase sqlDB;

   GregorianCalendar today = new GregorianCalendar();

    String UserId = "";
    String NickName = "";
    int month = 0;
    int date = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        txtDate = findViewById(R.id.txtDate);
        txtName = findViewById(R.id.txtName);
        btnList = findViewById(R.id.btnList);
        btnCal = findViewById(R.id.btnCal);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);

        Intent in = getIntent();
        UserId = in.getStringExtra("Userid");

        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), calender.class);
                startActivity(intent);
            }
        });
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ToDoList.class);
                startActivity(intent);
            }
        });

        // 닉네임 가져오기
        sqlDB = DBCreate.MemHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select Name from memberTBL where Id='"+ UserId +"';", null);
        cursor.moveToFirst();
        NickName = cursor.getString(0);

        txtName.setText(NickName);

        // 날짜 가져오기(월, 일)
        month = today.get(today.MONTH) + 1;
        date = today.get(today.DAY_OF_MONTH);

        if(month < 10){
            if(date < 10)
                txtDate.setText("0"+month+"월 "+"0"+date+"일");
            else
                txtDate.setText("0"+month+"월 "+date+"일");
        }else{
            if(date < 10)
                txtDate.setText(month+"월 "+"0"+date+"일");
            else
                txtDate.setText(month+"월 "+date+"일");
        }

    }
}
