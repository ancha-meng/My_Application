package com.example.myapplication.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.AuthManager;
import com.example.myapplication.database.DBOpenHelper;
import com.example.myapplication.database.connect;
import com.example.myapplication.databinding.FragmentDashboardBinding;

import java.sql.SQLException;
import java.util.ArrayList;

//视图层，交互来自模型的数据，负责页面的展示，以及管理页面操作逻辑，生命周期
public class DashboardFragment extends Fragment implements BlockAdapter.OnItemClickListener,BlockAdapter.uploadClickListener{

    // 定义成员变量
    private FragmentDashboardBinding binding; // 用于处理布局文件的数据绑定
    private RecyclerView recyclerView; // 显示列表的RecyclerView
    private BlockAdapter blockAdapter; // 管理RecyclerView的适配器

    //database
    private DBOpenHelper mDBOpenHelper;
    private String user_name = null;
    private  ArrayList<String> project_names = new ArrayList<>();

    // 生命周期
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;

        mDBOpenHelper = new DBOpenHelper(getActivity());

//        // 获取传递过来的Bundle
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String someData = bundle.getString("name");
//            user_name = someData;
//            //showMsg(someData);
//            // 使用传递过来的数据
//        }else{
//            showMsg("请先登录！");
//            //return root;
//        }
        if(AuthManager.getInstance().isLoggedIn()) {
            user_name = AuthManager.getInstance().getUsername();

            project_names = mDBOpenHelper.getProject_data(user_name);
            // 实例化DashboardViewModel
            DashboardViewModel dashboardViewModel = new DashboardViewModel(user_name, project_names);
//        DashboardViewModel dashboardViewModel =
//                new ViewModelProvider(this).get(DashboardViewModel.class);

            blockAdapter = new BlockAdapter();
            blockAdapter.setOnItemClickListener(this);
            blockAdapter.setuploadClickListener(this);
            recyclerView.setAdapter(blockAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            // 观察ViewModel中的数据，获取model中读取的数据库数据，通过adapter同步数据
            dashboardViewModel.getBlockList().observe(getViewLifecycleOwner(), blockList -> {
                blockAdapter.setBlockList(blockList);
                // 通知适配器数据已经改变，让它刷新视图
                blockAdapter.notifyDataSetChanged();
            });
        }
        else{
            showMsg("请先登录！");
        }

        return root;
    }

    @Override
    public void onItemClick(String taskId, Boolean stat, String project_time) {
        // 启动新的Activity
        Intent intent = new Intent(getContext(), SubMainActivity.class);
        //传递点索引与状态信息
        intent.putExtra("TASK_ID", taskId);
        intent.putExtra("STAT", stat);
        intent.putExtra("user_name",user_name);
        //intent.putExtra("project_time",project_time);

        startActivity(intent);
        //getActivity().finish();
    }

    @Override
    public void uploadClick(String taskId, String project_time){ }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void showMsg(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}

