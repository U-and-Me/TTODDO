package com.cookandroid.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ToDoList extends AppCompatActivity {

    Button btnHome, btnCal, btnAdd, btnDel, btnUpdate, btnLeft, btnRight;
    EditText editDay;

    Calendar cal = Calendar.getInstance();

    int year;
    int month;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        btnHome = findViewById(R.id.btnHome);
        btnCal = findViewById(R.id.btnCal);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        editDay = findViewById(R.id.editDay);

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);

        editDay.setText(year+"."+month);

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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = View.inflate(getApplicationContext(), R.layout.add, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ToDoList.this);

                dlg.setTitle("할 일 추가");
                dlg.setView(v);
                dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{

                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "추가실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();

            }
        });

    }
}
