package com.cookandroid.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class calender extends AppCompatActivity {

    TextView txtYear, txtMonth, txtDate, txtDay;
    Button btnHome, btnAdd;
    CalendarView calendarView;
    ListView listView;
    EditText editlist;

    Calendar cal = Calendar.getInstance();

    ListDBHelper listHelper;
    SQLiteDatabase sqlDB;

    int year, month, date, day;
    String[] days = {"일", "월", "화", "수", "목", "금", "토"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);

        btnAdd = findViewById(R.id.btnAdd);
        btnHome = findViewById(R.id.btnHome);
        txtYear = (TextView)findViewById(R.id.txtYear);
        txtMonth = (TextView)findViewById(R.id.txtMonth);
        txtDate = (TextView)findViewById(R.id.txtDate);
        txtDay = (TextView)findViewById(R.id.txtDay);
        calendarView = findViewById(R.id.calendarView);
        listView = findViewById(R.id.listview);

        listHelper = new ListDBHelper(this);

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        date = cal.get(Calendar.DATE);
        day = cal.get(Calendar.DAY_OF_WEEK);


        txtYear.setText(year+".");
        txtMonth.setText(Integer.toString(month));
        txtDate.setText(date+".");
        txtDay.setText(days[day-1]);

        showList();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = View.inflate(getApplicationContext(), R.layout.add, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(calender.this);
                dlg.setTitle("할 일 추가");
                dlg.setView(v);

                editlist = v.findViewById(R.id.edtAdd);

                dlg.setPositiveButton("추가", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{

                            sqlDB =  listHelper.getWritableDatabase();
                            String list = editlist.getText().toString();
                            String sql = "INSERT INTO listTBL(year, month, date, todo, checked) ";
                            sql+="VALUES("+year+","+month+","+date+",'"+list+"',0);";
                            sqlDB.execSQL(sql);

                            showList();

                            sqlDB.close();
                            Toast.makeText(getApplicationContext(), "추가 성공", Toast.LENGTH_SHORT).show();

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
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), main.class);
                startActivity(intent);
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int iyear, int imonth, int idayOfMonth) {
                year = iyear;
                month = imonth+1;
                date = idayOfMonth;

                cal.set(iyear, imonth, idayOfMonth);
                int iday = cal.get(Calendar.DAY_OF_WEEK);

                txtYear.setText(iyear+".");
                txtMonth.setText(Integer.toString(month));
                txtDate.setText(idayOfMonth+".");
                txtDay.setText(days[iday-1]);

                showList();

            }
        });

    } //onCreate

    void showList(){
        ListDBHelper listDBHelper = new ListDBHelper(this);
        SQLiteDatabase sqlDB = listDBHelper.getReadableDatabase();

        Cursor cursor = sqlDB.rawQuery("SELECT * FROM listTBL WHERE year="+year+" AND month="+month+" AND date="+date, null);

        listAdapter adapter = new listAdapter();

        while(cursor.moveToNext()){
            adapter.addItemToList(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4));
        }

        listView.setAdapter(adapter);

    }

}
