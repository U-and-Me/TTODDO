package com.cookandroid.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class main extends AppCompatActivity {

    TextView txtName, txtDate;
    Button btnList, btnCal, btnLeft, btnRight;

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

    }
}
