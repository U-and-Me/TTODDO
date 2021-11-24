package com.cookandroid.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class ToDoList extends AppCompatActivity {

    Button btnHome, btnCal, btnAdd, btnDel, btnUpdate, btnLeft, btnRight;
    TextView txtDay;
    LinearLayout linearlist;
    CheckBox chklist;


    Calendar cal = Calendar.getInstance();

    ListDBHelper listHelper;
    SQLiteDatabase sqlDB;

    int year, month, date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todolist);

        btnHome = findViewById(R.id.btnHome);
        btnCal = findViewById(R.id.btnCal);
        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDel = findViewById(R.id.btnDel);
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        txtDay = findViewById(R.id.txtDay);
        linearlist = findViewById(R.id.linearlist);

        listHelper = new ListDBHelper(this);

        selectDB();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        date = cal.get(Calendar.DATE);

        txtDay.setText(month+"월 "+date+"일");

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

                final EditText edtAddtodo = v.findViewById(R.id.edtAdd);

                dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            sqlDB =  listHelper.getWritableDatabase();
                            String list = edtAddtodo.getText().toString();
                            String sql = "INSERT INTO listTBL(year, month, date, todo) ";
                            sql+="VALUES("+year+","+month+","+date+",'"+list+"');";
                            sqlDB.execSQL(sql);

                            sqlDB.close();
                            Toast.makeText(getApplicationContext(), "추가 성공", Toast.LENGTH_SHORT).show();
                            selectDB();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "추가 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = View.inflate(getApplicationContext(), R.layout.update, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ToDoList.this);
                dlg.setTitle("할 일 수정");
                dlg.setView(v);

                final EditText edtUpdatetodo = v.findViewById(R.id.edtUpdate);

                dlg.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            sqlDB = listHelper.getWritableDatabase();
                            String list = edtUpdatetodo.getText().toString();
                            String sql = "UPDATE listTBL SET todo= '"+list+"';";
                            sqlDB.execSQL(sql);

                            sqlDB.close();
                            Toast.makeText(getApplicationContext(), "수정 성공", Toast.LENGTH_SHORT).show();
                            selectDB();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "수정 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = View.inflate(getApplicationContext(), R.layout.delete, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ToDoList.this);
                dlg.setTitle("할 일 삭제");
                dlg.setView(v);

                dlg.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            sqlDB = listHelper.getWritableDatabase();
                            sqlDB.execSQL("DELETE FROM listTBL;");



                            sqlDB.close();
                            Toast.makeText(getApplicationContext(), "삭제 성공", Toast.LENGTH_SHORT).show();
                            selectDB();
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "삭제 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dlg.setNegativeButton("취소", null);
                dlg.show();

            }
        });
/*
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date--;
                if(date < 1) {
                    date = cal.getActualMaximum(month - 2);
                    month--;
                }
                txtDay.setText(month+"월 "+date+"일");
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date++;
                if(date > cal.getActualMaximum(month-1)){
                    date = 1;
                }
                txtDay.setText(month+"월 "+date+"일");
            }
        });
*/
    } //onCreate

    public void selectDB(){
        sqlDB = listHelper.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT todo FROM " + "listTBL"+" WHERE year = "+year+" AND month ="+month+" AND date ="+date+";", null);
        String todo = "";

        while (cursor.moveToNext()){
            todo = cursor.getString(cursor.getColumnIndex("todo"));
            CheckBox chk = new CheckBox(this);
            chk.setText(todo);
            linearlist.addView(chk); // 체크박스 추가
        }
        cursor.close();
        sqlDB.close();
    }
}
