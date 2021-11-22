package com.cookandroid.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Date;

public class calender extends AppCompatActivity {

    TextView txtYear, txtMonth, txtDate, txtDay;
    Button btnList, btnHome;
    CalendarView calendarView;

    Calendar cal = Calendar.getInstance();

    int year, month, date, day;
    String[] days = {"일", "월", "화", "수", "목", "금", "토"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calender);

        btnList = findViewById(R.id.btnList);
        btnHome = findViewById(R.id.btnHome);
        txtYear = (TextView)findViewById(R.id.txtYear);
        txtMonth = (TextView)findViewById(R.id.txtMonth);
        txtDate = (TextView)findViewById(R.id.txtDate);
        txtDay = (TextView)findViewById(R.id.txtDay);
        calendarView = findViewById(R.id.calendarView);

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        date = cal.get(Calendar.DATE);
        day = cal.get(Calendar.DAY_OF_WEEK);

        txtYear.setText(year+".");
        txtMonth.setText(Integer.toString(month));
        txtDate.setText(date+".");
        txtDay.setText(days[day-1]);

        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ToDoList.class);
                startActivity(intent);
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
                cal.set(iyear, imonth, idayOfMonth);
                int iday = cal.get(Calendar.DAY_OF_WEEK);

                txtYear.setText(iyear+".");
                txtMonth.setText(Integer.toString(imonth+1));
                txtDate.setText(idayOfMonth+".");
                txtDay.setText(days[iday-1]);
            }
        });

    }
}
