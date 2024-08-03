package com.example.myapplication.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

//model层，与数据库交互/模拟数据，处理控制器层的请求，返回结果
public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<List<BlockItem>> mBlockList;
    //数据获取，计划在页面加载时调用，对本地的数据（缓存）进行读取
    public DashboardViewModel() {
        //获取数据
        mBlockList = new MutableLiveData<>();

        // 模拟数据
        List<BlockItem> blockList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String taskId = "Task " + (i + 1);
            Date taskTime = new Date(); // 这里可以根据你的需求设置具体的时间
            BlockItem blockItem = new BlockItem(taskId, taskTime);
            blockList.add(blockItem);
        }
        mBlockList.setValue(blockList);
    }
    //对外开发的数据接口，观察者模式
    public LiveData<List<BlockItem>> getBlockList() {
        return mBlockList;
    }


}
