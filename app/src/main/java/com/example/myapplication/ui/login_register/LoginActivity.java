package com.example.myapplication.ui.login_register;


import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private CheckBox rememberMe;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        username = findViewById(R.id.username);
//        password = findViewById(R.id.password);
//        rememberMe = findViewById(R.id.remember_me);
//        loginButton = findViewById(R.id.login_button);
//
//        loginButton.setOnClickListener(v -> {
//            String user = username.getText().toString();
//            String pass = password.getText().toString();
//            boolean remember = rememberMe.isChecked();
//
//            // 这里可以添加登录逻辑
//            Toast.makeText(LoginActivity.this, "用户名: " + user + ", 记住密码: " + remember, Toast.LENGTH_SHORT).show();
//        });
    }
}
