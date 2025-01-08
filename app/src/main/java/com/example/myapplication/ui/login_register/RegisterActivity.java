package com.example.myapplication.ui.login_register;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.AuthManager;
import com.example.myapplication.database.DBOpenHelper;
import com.example.myapplication.database.connect;

import java.sql.SQLException;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private String realCode;
    private DBOpenHelper mDBOpenHelper;
    private Button mBtRegisteractivityRegister;
    private ImageView mIvRegisteractivityBack;
    private EditText mEtRegisteractivityUsername;
    private EditText mEtRegisteractivityPassword1;
    private EditText mEtRegisteractivityPassword2;
    private EditText mEtRegisteractivityPhonecodes;
    private ImageView mIvRegisteractivityShowcode;

    private int conn_on = 0;
    private String is_exist = "0";
    private boolean is_register = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();

        mDBOpenHelper = new DBOpenHelper(this);

        //将验证码用图片的形式显示出来
        mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
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

    private void initView(){
        mBtRegisteractivityRegister = findViewById(R.id.bt_registeractivity_register);
        mIvRegisteractivityBack = findViewById(R.id.iv_registeractivity_back);
        mEtRegisteractivityUsername = findViewById(R.id.et_registeractivity_username);
        mEtRegisteractivityPassword1 = findViewById(R.id.et_registeractivity_password1);
        mEtRegisteractivityPassword2 = findViewById(R.id.et_registeractivity_password2);
        mEtRegisteractivityPhonecodes = findViewById(R.id.et_registeractivity_phoneCodes);
        mIvRegisteractivityShowcode = findViewById(R.id.iv_registeractivity_showCode);

        /**
         * 注册页面能点击的就三个地方
         * top处返回箭头、刷新验证码图片、注册按钮
         */
        mIvRegisteractivityBack.setOnClickListener(this);
        mIvRegisteractivityShowcode.setOnClickListener(this);
        mBtRegisteractivityRegister.setOnClickListener(this);
    }

    public void onClick(View view) {
        final Handler handler2 = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if(is_register)//判断输入密码与取得的密码是否相同
                {
                    showMsg("注册成功");
                }
                else {
                    if(Integer.parseInt(is_exist)!=0){
                        showMsg("该账号已被注册"+is_exist);
                    }
                    else {
                        showMsg("注册失败");
                    }
                }
                is_register=false;
                return false;
            }
        });
        int id =view.getId();
        if (id == R.id.iv_registeractivity_back) {
            Intent intent1 = new Intent(this, LoginActivity.class);
            startActivity(intent1);
            finish();
        }
        if (id == R.id.tv_registeractivity_login)
        {
            Intent intent2 = new Intent(this, LoginActivity.class);
            startActivity(intent2);
            finish();
        }
        if (id == R.id.iv_registeractivity_showCode) {
            mIvRegisteractivityShowcode.setImageBitmap(Code.getInstance().createBitmap());
            realCode = Code.getInstance().getCode().toLowerCase();
        }
        if (id == R.id.bt_registeractivity_register) {
            //获取用户输入的用户名、密码、验证码
            String username = mEtRegisteractivityUsername.getText().toString().trim();
            String password1 = mEtRegisteractivityPassword1.getText().toString().trim();
            String password2 = mEtRegisteractivityPassword2.getText().toString().trim();
            String phoneCode = mEtRegisteractivityPhonecodes.getText().toString().toLowerCase();
            //注册验证
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password1) && !TextUtils.isEmpty(password2) && !TextUtils.isEmpty(phoneCode)) {
                if (phoneCode.equals(realCode)) {
                    if (!(password1 == password2)) {
                        //将用户名和密码加入到数据库中
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message msg = new Message();
                                try {
                                    is_exist = connect.exist_col("user","username",username);
                                    if(Integer.parseInt(is_exist)==0){
                                        connect.insertIntoData(username,password2);
                                        is_register=true;
                                    }
                                    //connect.insertIntoData(username,password2);//调用插入数据库语句
                                    //mDBOpenHelper.add(username, password2);
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                    //showMsg("注册失败");
                                }handler2.sendMessage(msg);//跳转到handler2
                            }
                        }).start();
                        //showMsg("验证通过，注册成功");
                        //Toast.makeText(this, "验证通过，注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "密码输入不一致,请重新输入", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "验证码错误,注册失败", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "未完善信息，注册失败", Toast.LENGTH_SHORT).show();
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


