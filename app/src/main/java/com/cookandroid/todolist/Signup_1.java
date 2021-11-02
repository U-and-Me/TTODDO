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

public class Signup_1 extends AppCompatActivity {

    EditText editID, editPWD;
    Button btnCancel, btnNext;

    String Userid = "";
    String Userpwd = "";

    DBHelper MemHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_1);

        editID = findViewById(R.id.editID);
        editPWD = findViewById(R.id.editPwd);
        btnCancel = findViewById(R.id.btnCancel);
        btnNext = findViewById(R.id.btnNext);

        MemHelper = new DBHelper(this);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int chPWD = 0;
                int chID = checkID();

                if(chID == 1) {
                    chPWD = checkPWD();
                }

                if(chID == 1 && chPWD == 1) {

                    Intent intent = new Intent(getApplicationContext(), Signup_2.class);
                    intent.putExtra(Userid, Userpwd);
                    startActivity(intent);
                    editID.setText(""); editPWD.setText("");
                }
            }
        });



    } // onCreat()

    int checkID(){
        Userid = editID.getText().toString();
        if(Userid.length() < 5){
            Toast.makeText(getApplicationContext(), "5자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
            return -1;
        }

        sqlDB = MemHelper.getWritableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from memberTBL where Id;", null);

        if(cursor.moveToNext()){
            String strName = cursor.getString(0);
            if(Userid.equals(strName)){
                Toast.makeText(getApplicationContext(), "중복된 ID 입니다.", Toast.LENGTH_SHORT).show();
                return -1;
            }
        }

        cursor.close();
        sqlDB.close();

        return 1;
    }

    int checkPWD(){
        Userpwd= editPWD.getText().toString();
        if(Userpwd.length() < 8){
            Toast.makeText(getApplicationContext(), "8자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
            return -1;
        }

        return 1;
    }

}