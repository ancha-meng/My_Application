package com.example.myapplication.ui.login_register;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private EditText avatarPath;
    private EditText gender;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);
//
//        username = findViewById(R.id.username);
//        email = findViewById(R.id.email);
//        password = findViewById(R.id.password);
//        confirmPassword = findViewById(R.id.confirm_password);
//        avatarPath = findViewById(R.id.avatar_path);
//        gender = findViewById(R.id.gender);
//        registerButton = findViewById(R.id.register_button);
//
//        registerButton.setOnClickListener(v -> {
//            String user = username.getText().toString();
//            String mail = email.getText().toString();
//            String pass = password.getText().toString();
//            String confirmPass = confirmPassword.getText().toString();
//            String avatar = avatarPath.getText().toString();
//            String gen = gender.getText().toString();
//
//            if (!pass.equals(confirmPass)) {
//                Toast.makeText(RegisterActivity.this, "密码不匹配", Toast.LENGTH_SHORT).show();
//            } else {
//                // 这里可以添加注册逻辑
//                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}
