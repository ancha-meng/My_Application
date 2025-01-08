package com.example.myapplication.ui.dashboard;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.database.AuthManager;
import com.example.myapplication.database.DBOpenHelper;
import com.example.myapplication.database.Point;
import com.example.myapplication.database.connect;

import java.sql.SQLException;
import java.util.ArrayList;

// 此页面为跳转的子页面，负责显示任务的点信息，并控制点信息显示的两个子页面
public class SubMainActivity extends AppCompatActivity {

    private Button buttonList;
    private Button buttonMap;
    private Button buttonUpload;
    private TextView Point_title;
    private Boolean stat;
    private String taskId;
    private String user_name;
    private boolean is_uploaded = false;

    private DBOpenHelper mDBOpenHelper;
    private ArrayList<Point> value_points = new ArrayList<>();
    private ArrayList<Point> feel_points = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_main_layout);

        mDBOpenHelper = new DBOpenHelper(this);
        // 接收参数
        Intent intent = getIntent();
        taskId = intent.getStringExtra("TASK_ID");
        stat = intent.getBooleanExtra("STAT", false); // 没有传值时，默认值为 false
        user_name = intent.getStringExtra("user_name");
        //project_time = intent.getStringExtra("project_time");

        // 初始化互斥按钮
        buttonList = findViewById(R.id.button_list);
        buttonMap = findViewById(R.id.button_map);
        buttonUpload = findViewById(R.id.button_upload);
        Point_title=findViewById(R.id.point_title);
        Point_title.setText("任务："+taskId);

        // 设置按钮点击事件
        buttonList.setOnClickListener(v -> showListFragment());
        buttonMap.setOnClickListener(v -> showMapFragment());

        // 设置上传按钮点击事件
//        buttonUpload.setOnClickListener(v -> {
//            if (!stat) {//未提交时可以进行按钮交互
//                // 执行上传操作
//                Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
//                // 更改 stat 状态
//                stat = true;
//                // 刷新页面
//                refreshPage(taskId, stat);
//            }
//            refreshPage(taskId, stat);
//        });

//        // 根据 stat 信息控制上传按钮
//        if (!stat) {
//            buttonUpload.setEnabled(true);
//            buttonUpload.setBackgroundColor(getResources().getColor(R.color.orange));
//        } else {
//            buttonUpload.setEnabled(false);
//            buttonUpload.setBackgroundColor(getResources().getColor(R.color.light_gray));
//        }

        // 默认显示列表页面
        showListFragment();
    }

    private void showListFragment() {
        // 切换按钮状态
        buttonList.setBackgroundColor(getResources().getColor(R.color.teal_200));
        buttonMap.setBackgroundColor(getResources().getColor(R.color.light_gray));

        // 显示列表Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SubListFragment(getApplication(),user_name,taskId));
        transaction.commit();
    }

    private void showMapFragment() {
//        buttonList.setBackgroundColor(getResources().getColor(R.color.light_gray));
//        buttonMap.setBackgroundColor(getResources().getColor(R.color.teal_200));
//
//        // 显示地图Fragment
//        SubMapFragment subMapFragment = new SubMapFragment();
//        subMapFragment.setStat(stat); // 传递 stat 参数
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragment_container, subMapFragment);
//        transaction.commit();
        showMsg("功能待开发");
    }

    private void refreshPage(String taskId, boolean stat) {
        // 刷新当前页面，并传递更新后的参数
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("TASK_ID", taskId);
        intent.putExtra("STAT", stat);
        AuthManager.getInstance().setLoggedIn(true);
        AuthManager.getInstance().setUsername(user_name);
        startActivity(intent);
        finish();
    }
    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
    public void onClick(View view) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this)
                .setTitle("提示").setMessage("确定上传？")
                .setPositiveButton("确定", (dialog, which) -> {
                    upload();
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
    public void upload() {
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if(is_uploaded){
                    showMsg("上传成功！");
                }
                else {
                    showMsg("上传失败！");
                }
                is_uploaded=false;
                return false;
            }
        });
        value_points = mDBOpenHelper.getValue_points(user_name,taskId);
        feel_points = mDBOpenHelper.getFeel_points(user_name,taskId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    connect.insertProject(user_name,taskId);
                    for(int i = 0;i<value_points.size();i++) {
                        connect.insertValue_point(user_name, taskId, value_points.get(i));
                    }
                    for(int i = 0;i<feel_points.size();i++) {
                        connect.insertFeel_point(user_name, taskId, feel_points.get(i));
                        for(int x = 0;x< feel_points.get(i).getImage_name().size();x++){
                            connect.insertImage(user_name, taskId, feel_points.get(i).getPoint_name(),
                                    feel_points.get(i).getImage_name().get(x),
                                    (Integer) feel_points.get(i).getImage_size().get(x),
                                    feel_points.get(i).getImage_data().get(x));
                        }
                        for(int y = 0;y< feel_points.get(i).getAudio_name().size();y++){
                            connect.insertAudio(user_name, taskId, feel_points.get(i).getPoint_name(),
                                    feel_points.get(i).getAudio_name().get(y),
                                    feel_points.get(i).getAudio_data().get(y));
                        }
                    }
                    is_uploaded=true;
                } catch (SQLException e) {
                    e.printStackTrace();
                }handler.sendMessage(msg);//跳转到handler2
            }
        }).start();
        //showMsg("上传成功！");
    }
}
