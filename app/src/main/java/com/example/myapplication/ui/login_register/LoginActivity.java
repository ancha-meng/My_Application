package com.example.myapplication.ui.login_register;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private CheckBox rememberMe;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        rememberMe = findViewById(R.id.remember_me);
        loginButton = findViewById(R.id.login_button);

        loginButton.setOnClickListener(v -> {
            String user = username.getText().toString();
            String pass = password.getText().toString();
            boolean remember = rememberMe.isChecked();
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "用户名或密码不能为空", Toast.LENGTH_SHORT).show();
            } else {
                // 这里可以添加登录逻辑
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }
            // 这里可以添加登录逻辑
            Toast.makeText(LoginActivity.this, "用户名: " + user + ", 记住密码: " + remember, Toast.LENGTH_SHORT).show();
        });
    }
    public void goToRegister(View view) {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }
}
