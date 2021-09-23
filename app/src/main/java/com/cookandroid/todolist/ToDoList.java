package com.cookandroid.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ToDoList extends AppCompatActivity {

    Button btnHome, btnCal, btnAdd, btnDel, btnUpdate, btnLeft, btnRight;
    EditText editDay;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        btnHome = findViewById(R.id.btnHome);
        btnCal = findViewById(R.id.btnCal);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
            }
        });
        btnCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), calender.class);
                startActivity(intent);
            }
        });

    }
}
