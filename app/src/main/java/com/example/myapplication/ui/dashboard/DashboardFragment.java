package com.example.myapplication.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;
import java.util.ArrayList;

//视图层，交互来自模型的数据，负责页面的展示，以及管理页面操作逻辑，生命周期
public class DashboardFragment extends Fragment implements BlockAdapter.OnItemClickListener{

    // 定义成员变量
    private FragmentDashboardBinding binding; // 用于处理布局文件的数据绑定
    private RecyclerView recyclerView; // 显示列表的RecyclerView
    private BlockAdapter blockAdapter; // 管理RecyclerView的适配器

    // 生命周期
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // 实例化DashboardViewModel
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerView;
        blockAdapter = new BlockAdapter();
        blockAdapter.setOnItemClickListener(this);
        recyclerView.setAdapter(blockAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 观察ViewModel中的数据，获取model中读取的数据库数据，通过adapter同步数据
        dashboardViewModel.getBlockList().observe(getViewLifecycleOwner(), blockList -> {
            blockAdapter.setBlockList(blockList);
            // 通知适配器数据已经改变，让它刷新视图
            blockAdapter.notifyDataSetChanged();
        });

        return root;
    }

    @Override
    public void onItemClick(String taskId) {
        // 启动新的Activity
        Intent intent = new Intent(getContext(), SubMainActivity.class);
        intent.putExtra("TASK_ID", taskId);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

