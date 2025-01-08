package com.example.myapplication.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

//model层，与数据库交互/模拟数据，处理控制器层的请求，返回结果
public class DashboardViewModel extends ViewModel {
    private String user_name = null;
    private final MutableLiveData<List<BlockItem>> mBlockList;
    //数据获取，计划在页面加载时调用，对本地的数据（缓存）进行读取
    public DashboardViewModel(String user_name,ArrayList<String> projrcts_datas) {
        //获取数据
        mBlockList = new MutableLiveData<>();
        this.user_name = user_name;
        // 模拟数据
        List<BlockItem> blockList = new ArrayList<>();
        for (int i = 0; i < projrcts_datas.size(); i++) {
            String taskId = projrcts_datas.get(i);
            BlockItem blockItem = new BlockItem(taskId);
            blockList.add(blockItem);
        }
        mBlockList.setValue(blockList);
    }
    //对外开发的数据接口，观察者模式
    public LiveData<List<BlockItem>> getBlockList() {
        return mBlockList;
    }
}
