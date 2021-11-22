package com.cookandroid.todolist;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Join_2 extends AppCompatActivity {

    EditText editNick;
    Button btnOk;

    String NickName;

    //DBCreate MemHelper = new DBCreate(this);
    SQLiteDatabase sqlDB;
    String id, pwd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_2);

        editNick = findViewById(R.id.editNick);
        btnOk = findViewById(R.id.btnOk);

        //MemHelper = new DBHelper(this);

        Intent in = getIntent();
        id = in.getStringExtra("Userid");
        pwd = in.getStringExtra("Userpwd");

       // Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), pwd, Toast.LENGTH_SHORT).show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    NickName = editNick.getText().toString();

                    sqlDB = DBCreate.MemHelper.getWritableDatabase();
                    String sql = "insert into memberTBL(Id, Pwd, Name) ";
                    sql += "values('" + id + "','" + pwd + "','" + NickName + "');";

                    sqlDB.execSQL(sql);
                    sqlDB.close();

                    Toast.makeText(getApplicationContext(), "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "가입에 실패했습니다.", Toast.LENGTH_SHORT).show();
                }

                Intent intent = new Intent(getApplicationContext(), Signin.class);
                startActivity(intent);
            }
        });

    } //onCreat

}
