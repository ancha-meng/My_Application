package com.example.myapplication.database;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class Register extends AppCompatActivity {

    private DbContect mSQlite;
    private EditText username;
    private EditText userpassword;
    private Button reday;
    private Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        reday = findViewById(R.id.register_button);
        back = findViewById(R.id.register_back);
        username = findViewById(R.id.username);
        userpassword =findViewById( R.id.password);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        reday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = username.getText().toString().trim();
                String password = userpassword.getText().toString().trim();
                if(!TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password)){
                    mSQlite.add(name,password);
                    Intent intent1 = new Intent(Register.this,MainActivity.class);
                    startActivity(intent1);
                    finish();
                    Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                }else {Toast.makeText(Register.this,"信息不完备，注册失败",Toast.LENGTH_SHORT).show();}
            }
        });
        mSQlite = new DbContect(Register.this);
    }
}
