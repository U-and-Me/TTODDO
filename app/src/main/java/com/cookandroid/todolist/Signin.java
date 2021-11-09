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

    Button btnLogin;
    EditText editID, editPwd;

    String Userid;
    String Userpwd;

    DBHelper MemHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        btnLogin = findViewById(R.id.btnLogin);
        editID = findViewById(R.id.editID);
        editPwd = findViewById(R.id.editPwd);

        // DB 생성 또는 기존 DB 불러오기


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Userpwd= editPwd.getText().toString();
                Userid = editID.getText().toString();

                //if(checkID()==1) {

                    if (checkPWD() == 1) {
                       //Toast.makeText(getApplicationContext(), "로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), main.class);
                        startActivity(intent);
                    }
               // }

            }
        });
    } // onCreat()

    int checkID(){
        if(Userid.length() == 0){
            Toast.makeText(getApplicationContext(), "ID를 입력해주세요", Toast.LENGTH_SHORT).show();
            return 0;
        }

        sqlDB = MemHelper.getReadableDatabase();
        Cursor cursor ;
        cursor=sqlDB.rawQuery("select Id from memberTBL where Id='"+ Userid +"';", null);
        String strId = cursor.getString(0);
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

        sqlDB = MemHelper.getReadableDatabase();
        Cursor cursor ;
        cursor=sqlDB.rawQuery("select Pwd from memberTBL where Id='"+ Userid +"';", null);
        String strPwd = cursor.getString(0);
        if(Userpwd.equals(strPwd)){
            cursor.close();
            sqlDB.close();
            return 1;
        }
        cursor.close();
        sqlDB.close();

        return 0;
    }

}