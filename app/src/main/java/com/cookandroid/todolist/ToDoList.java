package com.cookandroid.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;

public class ToDoList extends AppCompatActivity {

    Button btnHome, btnCal, btnAdd, btnLeft, btnRight;
    TextView txtDay;
    LinearLayout linearlist;
    ListView TodoList;

    SharedPreferences preferences;
    ToDo todo;

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
        btnLeft = findViewById(R.id.btnLeft);
        btnRight = findViewById(R.id.btnRight);
        txtDay = findViewById(R.id.txtDay);
        linearlist = findViewById(R.id.linearlist);
        TodoList = findViewById(R.id.TodoList);

        listHelper = new ListDBHelper(this);

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        date = cal.get(Calendar.DATE);

        txtDay.setText(month+"월 "+date+"일");

        todo = new ToDo(year, month, date);

        selectDB();

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
                            String sql = "INSERT INTO listTBL(year, month, date, todo, chk) ";
                            sql+="VALUES("+year+","+month+","+date+",'"+list+"',0);";
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
    } //onCreate

    public void selectDB(){
        /*
        linearlist.removeAllViews();
        sqlDB = listHelper.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT todo, chk FROM " + "listTBL"+" WHERE year = "+year+" AND month ="+month+" AND date ="+date+";", null);

        String todo = "";
        int check = 0;

        while (cursor.moveToNext()){
            todo = cursor.getString(cursor.getColumnIndex("todo"));
            //check = cursor.getInt(cursor.getColumnIndex("checklist"));

            CheckBox chk = new CheckBox(this);
            chk.setText(todo);
            if(check == 1) {
                chk.setChecked(true);
                chk.setTextColor(Color.parseColor("#CACACA"));
            }
            linearlist.addView(chk); // 체크박스 추가
        }
        cursor.close();
        sqlDB.close();
        */

        ListDBHelper listDBHelper = new ListDBHelper(this);
        SQLiteDatabase sqlDB = listDBHelper.getReadableDatabase();

        Cursor cursor = sqlDB.rawQuery("SELECT * FROM " + "listTBL"+" WHERE year = "+year+" AND month ="+month+" AND date ="+date+";", null);
        listAdapter adapter = new listAdapter(this);

        while(cursor.moveToNext()){
            String todo = cursor.getString(3);
            int chk = cursor.getInt(4);
            adapter.addToDoList(year, month, date, todo, chk);
        }

        TodoList.setAdapter(adapter);

    }

    public void setList(){
        ListDBHelper listDBHelper = new ListDBHelper(this);
        SQLiteDatabase sqlDB = listDBHelper.getReadableDatabase();

        Cursor cursor_todolist = sqlDB.rawQuery("SELECT todo FROM listTBL WHERE year = "+year+" AND month ="+month+" AND date ="+date+";", null);
        Cursor cursor_todochk = sqlDB.rawQuery("SELECT chk FROM listTBL WHERE year = "+year+" AND month ="+month+" AND date ="+date+";", null);

    }

}
