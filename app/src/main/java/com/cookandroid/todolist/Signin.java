package com.cookandroid.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Signin extends AppCompatActivity {

    Button btnLogin, btnJoin;
    EditText editID, editPwd;

    String Userid;
    String Userpwd;

    String strId, strPwd;

    DBHelper MemHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);
        editID = findViewById(R.id.editID);
        editPwd = findViewById(R.id.editPwd);

        MemHelper = new DBHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Userpwd= editPwd.getText().toString();
                Userid = editID.getText().toString();

                if(checkID()==1) {
                    if (checkPWD() == 1) {
                       //Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), main.class);
                        intent.putExtra("Userid" , Userid);
                        startActivity(intent);
                    }
                }

            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Join_1.class);
                startActivity(intent);
            }
        });
    } // onCreat()

    int checkID(){
        if(Userid.length() == 0){
            Toast.makeText(getApplicationContext(), "ID를 입력해주세요", Toast.LENGTH_SHORT).show();
            return 0;
        }

        sqlDB = MemHelper.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT Id FROM " + "memberTBL", null);
        cursor.moveToFirst();
        strId = cursor.getString(cursor.getColumnIndex("Id"));
        if (Userid.equals(strId)) {
            cursor.close();
            sqlDB.close();
            return 1;
        }

        cursor.close();
        sqlDB.close();

        Toast.makeText(getApplicationContext(), "없는 ID입니다.", Toast.LENGTH_SHORT).show();

        return 0;
    }

    int checkPWD(){
        if(Userpwd.length() < 1) {
            Toast.makeText(getApplicationContext(), "PWD를 입력해주세요", Toast.LENGTH_SHORT).show();
            return 0;
        }

        sqlDB = MemHelper.getWritableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT Pwd FROM " + "memberTBL", null);
        cursor.moveToFirst();
        strPwd = cursor.getString(cursor.getColumnIndex("Pwd"));
        if(Userpwd.equals(strPwd)){
            cursor.close();
            sqlDB.close();
            return 1;
        }
        cursor.close();
        sqlDB.close();

        Toast.makeText(getApplicationContext(), "잘못된 PWD 입니다.", Toast.LENGTH_SHORT).show();

        return 0;
    }

}