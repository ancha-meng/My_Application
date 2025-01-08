package com.example.myapplication.ui.login_register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.AuthManager;
import com.example.myapplication.database.DBOpenHelper;
import com.example.myapplication.database.User;
import com.example.myapplication.database.connect;
import com.example.myapplication.ui.SharedViewModel;
import com.example.myapplication.ui.notifications.NotificationsFragment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String realCode;
    private DBOpenHelper mDBOpenHelper;
    private TextView mTvLoginactivityRegister;
    private EditText mEtLoginactivityUsername;
    private EditText mEtLoginactivityPassword;
    private Button mBtLoginactivityLogin;
    private EditText mEtloginactivityPhonecodes;
    private ImageView mIvloginactivityShowcode;

    public static String password_receive;//用于接收数据库查询的返回数据
    private int conn_on = 0;
    private boolean match = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);

        //将验证码用图片的形式显示出来
        mIvloginactivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
        realCode = Code.getInstance().getCode().toLowerCase();

        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                conn_on=connect.getConn_on();
                //if(conn_on==1){showMsg("连接成功:"+conn_on);}
                if(conn_on==2){showMsg("连接失败:"+conn_on);}
                if(conn_on==0){showMsg("未连接:"+conn_on);}
                return false;
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    connect.getConnection("db_map");//执行连接测试
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(msg);//跳转到handler
            }
        }).start();
    }

    private void initView() {
        // 初始化控件
        mBtLoginactivityLogin = findViewById(R.id.bt_loginactivity_login);
        mTvLoginactivityRegister = findViewById(R.id.tv_loginactivity_register);
        mEtLoginactivityUsername = findViewById(R.id.et_loginactivity_username);
        mEtLoginactivityPassword = findViewById(R.id.et_loginactivity_password);
        mEtloginactivityPhonecodes = findViewById(R.id.et_loginactivity_phoneCodes);
        mIvloginactivityShowcode = findViewById(R.id.iv_loginactivity_showCode);

        // 设置点击事件监听器
        mBtLoginactivityLogin.setOnClickListener(this);
        mIvloginactivityShowcode.setOnClickListener(this);

    }

    public void onClick(View view) {
        final Handler handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if(password_receive.equals(mEtLoginactivityPassword.getText().toString()))//判断输入密码与取得的密码是否相同
                {
                    showMsg("登陆成功");
                    match = true;
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("name",mEtLoginactivityUsername.getText().toString());
                    AuthManager.getInstance().setLoggedIn(true);
                    AuthManager.getInstance().setUsername(mEtLoginactivityUsername.getText().toString());
                    startActivity(intent);
                    finish();//销毁此Activity
                }
                else {
                    showMsg("用户名或密码不正确，请重新输入");
                    match = true;
                }
                return false;
            }
        });
        int id = view.getId();
        if (id == R.id.iv_mainactivity_back)
        {
            Intent intent1 = new Intent(this, MainActivity.class);
            startActivity(intent1);
            finish();
        }
        if (id == R.id.tv_loginactivity_register)
        {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        }
        if (id == R.id.iv_loginactivity_showCode)
        {

            mIvloginactivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
            realCode = Code.getInstance().getCode().toLowerCase();
        }
        if (id == R.id.bt_loginactivity_login)
        {
            String name = mEtLoginactivityUsername.getText().toString().trim();
            String password = mEtLoginactivityPassword.getText().toString().trim();
            String phoneCode = mEtloginactivityPhonecodes.getText().toString().toLowerCase();

            if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(phoneCode)) {
                if (phoneCode.equals(realCode)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = new Message();
                            try {
                                password_receive=connect.querycol(mEtLoginactivityUsername.getText().toString());//调用查询语句，获得账号对应的密码
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }handler2.sendMessage(msg);//跳转到handler2
                        }
                    }).start();
//                    ArrayList<User> data = mDBOpenHelper.getAllData();
//                    for (int i = 0; i < data.size(); i++) {
//                        User user = data.get(i);
//                        if (name.equals(user.getName()) && password.equals(user.getPassword())/*&&phoneCode.equals(realCode)*/) {
//                            match = true;
//                            break;
//                        } else {
//                            match = false;
//                        }
//                    }
//                    if (match) {
//                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(this, MainActivity.class);
//                        intent.putExtra("name",name);
//                        AuthManager.getInstance().setLoggedIn(true);
//                        AuthManager.getInstance().setUsername(name);
//                        startActivity(intent);
//                        finish();//销毁此Activity
//                    } else {
//                        Toast.makeText(this, "用户名或密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "请输入你的用户名或密码", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onBackPressed() {
        // 不调用 super.onBackPressed()，这样就不会触发默认的返回行为
        // 你可以在这里添加一些额外的逻辑，比如显示一个Toast消息
    }
}

