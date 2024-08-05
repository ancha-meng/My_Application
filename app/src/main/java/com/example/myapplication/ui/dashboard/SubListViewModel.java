package com.example.myapplication.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class SubListViewModel extends ViewModel {


    private final MutableLiveData<List<PointItem>> mPointList;
    public SubListViewModel(){
        mPointList=new MutableLiveData<>();
        // 模拟数据
        List<PointItem> pointItems = new ArrayList<>();
        pointItems.add(new PointItem(1, 34.052235, -118.243683, 5, "Sunny", null, null, null, 0));
        pointItems.add(new PointItem(2, 40.712776, -74.005974, 4, "Cloudy", null, null, null, 0));
        pointItems.add(new PointItem(3, 51.507351, -0.127758, 3, "Rainy", null, null, null, 0));
        pointItems.add(new PointItem(4, 35.689487, 139.691711, 5, "Clear", null, null, null, 0));
        pointItems.add(new PointItem(5, 48.856613, 2.352222, 4, "Windy", null, null, null, 0));

        mPointList.setValue(pointItems);
    }

    public LiveData<List<PointItem>> getPointList() {
        return mPointList;
    }
}
