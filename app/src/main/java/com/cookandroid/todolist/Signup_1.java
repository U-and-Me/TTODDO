package com.cookandroid.todolist;

import android.content.Intent;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_1);

        editID = findViewById(R.id.editID);
        editPWD = findViewById(R.id.editPwd);
        btnCancel = findViewById(R.id.btnCancel);
        btnNext = findViewById(R.id.btnNext);

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
                int chID = checkID();
                int chPWD = checkPWD();

                if(chID == -1){
                    Toast.makeText(getApplicationContext(), "ID를 입력해주세요", Toast.LENGTH_LONG).show();
                }
                if(chPWD == -1){
                    Toast.makeText(getApplicationContext(), "PWD를 입력해주세요", Toast.LENGTH_LONG).show();
                }
                if(chID == 1 && chPWD == 1) {
                    Intent intent = new Intent(getApplicationContext(), Signup_2.class);
                    startActivity(intent);
                }
            }
        });



    } // onCreat()

    int checkID(){
        Userid = editID.getText().toString();
        if(Userid.length() != 0) return 1;
        return -1;
    }

    int checkPWD(){
        Userpwd = editPWD.getText().toString();
        if(Userpwd.length() != 0) return 1;
        return -1;
    }

}
