package com.cookandroid.todolist;

import android.content.Intent;
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

    String Userid = "";
    String Userpwd = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        btnLogin = findViewById(R.id.btnLogin);
        editID = findViewById(R.id.editID);
        editPwd = findViewById(R.id.editPwd);

        btnLogin.setOnClickListener(new View.OnClickListener() {
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
                    Intent intent = new Intent(getApplicationContext(), main.class);
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
        Userpwd = editPwd.getText().toString();
        if(Userpwd.length() != 0) return 1;
        return -1;
    }

}