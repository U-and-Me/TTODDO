package com.cookandroid.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRouter;
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
                Userpwd= editPWD.getText().toString();
                Userid = editID.getText().toString();

                if(checkID()==1) {
                    if (checkPWD() == 1) {
                        Intent intent = new Intent(getApplicationContext(), Signup_2.class);
                        intent.putExtra("Userid", Userid);
                        intent.putExtra("Userpwd", Userpwd);
                        startActivity(intent);
                        editID.setText("");
                        editPWD.setText("");
                    }
                }
            }
        });



    } // onCreat()

    int checkID(){

        //Toast.makeText(getApplicationContext(), Userid, Toast.LENGTH_SHORT).show();
        if(Userid.length() < 3){
            Toast.makeText(getApplicationContext(), "3자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
            return -1;
        }
        sqlDB = MemHelper.getReadableDatabase();
        Cursor cursor ;
        cursor=sqlDB.rawQuery("select * from memberTBL where Id='"+ Userid +"';", null);
        while(cursor.moveToNext()){
            String strId = cursor.getString(0);
            if(Userid.equals(strId)){
                Toast.makeText(getApplicationContext(), "중복된 ID 입니다.", Toast.LENGTH_SHORT).show();
                return 0;
            }
        }
        cursor.close();
        sqlDB.close();

        return 1;
    }

    int checkPWD(){

        if(Userpwd.length() < 4){
            Toast.makeText(getApplicationContext(), "4자 이상 입력해주세요", Toast.LENGTH_SHORT).show();
            return 0;
        }

        return 1;
    }

}