package com.cookandroid.todolist;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
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

public class listAdapter extends BaseAdapter {

    ArrayList<ToDo> list = new ArrayList<ToDo>();

    ListDBHelper listDBHelper;
    SQLiteDatabase sqlDB;

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Context context = viewGroup.getContext();
        listDBHelper = new ListDBHelper(context);

        // 리스트뷰에 아이템이 인플레이드 되어있는지 확인
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, viewGroup, false);
        }

        final CheckBox checkBox = view.findViewById(R.id.chkbox);
        TextView txtTodo = view.findViewById(R.id.txtTodo);
        Button btnDel = view.findViewById(R.id.btnDelete);

        // list 배열에서 객체를 가져옴
        final ToDo listdata = list.get(i);

        // 각 뷰에 적용
        txtTodo.setText(listdata.getTodo());

        if(list.get(i).getChecked() == 1){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);
        }
        final int i1 = i;
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    int year = listdata.getYear();
                    int month = listdata.getMonth();
                    int date = listdata.getDate();
                    String todo = listdata.getTodo();
                    System.out.println(year + "  " + month + "    " + date + "    "+todo);
                   // Toast.makeText(context, listdata.getDate(), Toast.LENGTH_SHORT).show();

                    sqlDB = listDBHelper.getWritableDatabase();
                    String sql = "DELETE FROM listTBL WHERE year="+year+" AND month="+month+" AND date="+date+" AND todo='"+todo+"';";
                    sqlDB.execSQL(sql);
                    sqlDB.close();

                    list.remove(i1);
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
                        if(list.get(i1).getChecked() == 1){
                            list.get(i1).checked = 0;
                        }else{
                            list.get(i1).checked = 1;
                        }

                        list.get(i1).setChecked(1);
                        sqlDB = listDBHelper.getWritableDatabase();

                        String sql = "UPDATE listTBL SET checked=1 WHERE year="+listdata.getYear()+" AND month="+listdata.getMonth()+" AND date="+listdata.getDate()+" AND todo='"+listdata.getTodo()+"';";
                        sqlDB.execSQL(sql);
                        sqlDB.close();

                        notifyDataSetChanged();

                    }else{
                        list.get(i1).setChecked(0);
                        sqlDB = listDBHelper.getWritableDatabase();

                        String sql = "UPDATE listTBL SET checked=0 WHERE year="+listdata.getYear()+" AND month="+listdata.getMonth()+" AND date="+listdata.getDate()+" AND todo='"+listdata.getTodo()+"';";
                        sqlDB.execSQL(sql);
                        sqlDB.close();

                        notifyDataSetChanged();
                    }
                }
            }
        });

        return view;
    }

    public void addItemToList(int year, int month, int date, String todo, int checked){
        ToDo listdata = new ToDo();

        listdata.setYear(year);
        listdata.setMonth(month);
        listdata.setDate(date);
        listdata.setTodo(todo);
        listdata.setChecked(checked);

        // listdata 객체를 list 배열에 추가
        list.add(listdata);
    }

}
