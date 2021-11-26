package com.cookandroid.todolist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class listAdapter extends BaseAdapter {

    ListDBHelper listDBHelper;
    SQLiteDatabase sqlDB;

    ArrayList<ToDo> todoList = new ArrayList<ToDo>();

    ToDoList mtodoList;

    listAdapter(ToDoList activity){
        mtodoList = activity;
    }


    @Override
    public int getCount() {
        return todoList.size();
    }

    @Override
    public Object getItem(int i) {
        return todoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();
        listDBHelper = new ListDBHelper(context);

        // 리스트에 아이템이 없는지 확인
        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item,viewGroup, false);
        }

        TextView txtList = view.findViewById(R.id.txtTodo);
        final CheckBox checkBox = view.findViewById(R.id.chkbox);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        final ToDo todoItem = todoList.get(i);
        txtList.setText(todoItem.getTodo());

        if(todoList.get(i).getChecked() == 1){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    String todo = todoItem.getTodo();

                    sqlDB = listDBHelper.getWritableDatabase();
                    String sql = "DELETE FROM listTBL WHERE year="+todoItem.getYear()+" AND month="+todoItem.getMonth()+" AND date="+todoItem.getDate()+" AND todo='"+todo+"';";
                    sqlDB.execSQL(sql);
                    sqlDB.close();

                    todoList.remove(i);
                    notifyDataSetChanged();

                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(context, "삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.getId() == R.id.chkbox){
                    if(checkBox.isChecked()){
                        boolean ischeck = !checkBox.isChecked();
                        if(ischeck) todoList.get(i).checked = 1;
                        else todoList.get(i).checked = 0;
                        todoList.get(i).setChecked(1);

                        sqlDB = listDBHelper.getWritableDatabase();
                        String sql = "UPDATE listTBL SET chk = 1 WHERE year="+todoItem.getYear()+" AND month="+todoItem.getMonth()+" AND date="+todoItem.getDate()+" AND todo='"+todoItem.getTodo()+"';";
                        sqlDB.execSQL(sql);
                        sqlDB.close();

                        notifyDataSetChanged();
                    }else{
                        todoList.get(i).checked = 0;
                        sqlDB = listDBHelper.getWritableDatabase();
                        String sql = "UPDATE listTBL SET chk = 1 WHERE year="+todoItem.getYear()+" AND month="+todoItem.getMonth()+" AND date="+todoItem.getDate()+" AND todo='"+todoItem.getTodo()+"';";
                        sqlDB.execSQL(sql);
                        sqlDB.close();

                        notifyDataSetChanged();
                    }
                }
            }
        });

        return view;
    }

    public void addToDoList(int year, int month, int date, String todo, int check){
        ToDo tododata = new ToDo();
        tododata.setYear(year);
        tododata.setMonth(month);
        tododata.setDate(date);
        tododata.setChecked(0);
        tododata.setTodo(todo);

        todoList.add(tododata);

    }
}
