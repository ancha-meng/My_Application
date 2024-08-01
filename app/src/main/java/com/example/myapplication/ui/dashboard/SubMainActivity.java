package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import com.example.myapplication.R;

//此页面为跳转的子页面，负责显示任务的点信息，并控制点信息显示的两个子页面
public class SubMainActivity extends AppCompatActivity {

    private Button buttonList;
    private Button buttonMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_main_layout);

        // 初始化互斥按钮
        buttonList = findViewById(R.id.button_list);
        buttonMap = findViewById(R.id.button_map);

        // 设置按钮点击事件
        buttonList.setOnClickListener(v -> showListFragment());
        buttonMap.setOnClickListener(v -> showMapFragment());

        // 默认显示列表页面
        showListFragment();
    }

    private void showListFragment() {
        // 切换按钮状态
        buttonList.setBackgroundColor(getResources().getColor(R.color.teal_200));
        buttonMap.setBackgroundColor(getResources().getColor(R.color.teal_700));

        // 显示列表Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SubListFragment());
        transaction.commit();
    }

    private void showMapFragment() {
        // 切换按钮状态
        buttonList.setBackgroundColor(getResources().getColor(R.color.teal_700));
        buttonMap.setBackgroundColor(getResources().getColor(R.color.teal_200));

        // 显示地图Fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new SubMapFragment());
        transaction.commit();
    }
}
