package com.example.myapplication.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapplication.R;

// 此页面为跳转的子页面，负责显示任务的点信息，并控制点信息显示的两个子页面
public class SubMainActivity extends AppCompatActivity {

    private Button buttonList;
    private Button buttonMap;
    private Button buttonUpload;
    private TextView Point_title;
    private Boolean stat;
    private String taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_main_layout);

        // 接收参数
        Intent intent = getIntent();
        taskId = intent.getStringExtra("TASK_ID");
        stat = intent.getBooleanExtra("STAT", false); // 没有传值时，默认值为 false

        // 初始化互斥按钮
        buttonList = findViewById(R.id.button_list);
        buttonMap = findViewById(R.id.button_map);
        buttonUpload = findViewById(R.id.button_upload);
        Point_title=findViewById(R.id.point_title);
        Point_title.setText("任务"+taskId);

        // 设置按钮点击事件
        buttonList.setOnClickListener(v -> showListFragment());
        buttonMap.setOnClickListener(v -> showMapFragment());

        // 设置上传按钮点击事件
        buttonUpload.setOnClickListener(v -> {
            if (!stat) {//未提交时可以进行按钮交互
                // 执行上传操作
                Toast.makeText(this, "上传成功", Toast.LENGTH_SHORT).show();
                // 更改 stat 状态
                stat = true;
                // 刷新页面
                refreshPage(taskId, stat);
            }
        });

        // 根据 stat 信息控制上传按钮
        if (!stat) {
            buttonUpload.setEnabled(true);
            buttonUpload.setBackgroundColor(getResources().getColor(R.color.orange));
        } else {
            buttonUpload.setEnabled(false);
            buttonUpload.setBackgroundColor(getResources().getColor(R.color.light_gray));
        }

        // 默认显示列表页面
        showListFragment();
    }

    private void showListFragment() {
        // 切换按钮状态
        buttonList.setBackgroundColor(getResources().getColor(R.color.teal_200));
        buttonMap.setBackgroundColor(getResources().getColor(R.color.light_gray));

        // 显示列表Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SubListFragment());
        transaction.commit();
    }

    private void showMapFragment() {
        buttonList.setBackgroundColor(getResources().getColor(R.color.light_gray));
        buttonMap.setBackgroundColor(getResources().getColor(R.color.teal_200));

        // 显示地图Fragment
        SubMapFragment subMapFragment = new SubMapFragment();
        subMapFragment.setStat(stat); // 传递 stat 参数
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, subMapFragment);
        transaction.commit();
    }

    private void refreshPage(String taskId, boolean stat) {
        // 刷新当前页面，并传递更新后的参数
        Intent intent = new Intent(this, SubMainActivity.class);
        intent.putExtra("TASK_ID", taskId);
        intent.putExtra("STAT", stat);
        finish();
        startActivity(intent);
    }
}
