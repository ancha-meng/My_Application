package com.example.myapplication.ui.dashboard;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.myapplication.database.DBOpenHelper;
import com.example.myapplication.database.Point;

import java.util.ArrayList;
import java.util.List;

public class SubListViewModel extends ViewModel {
    private String user_name;
    private String project_name;
    private DBOpenHelper mDBOpenHelper;
    private Application application;

    private final MutableLiveData<ArrayList<Point>> mPointList;
    public SubListViewModel(@NonNull Application application, String user_name, String project_name){
        this.user_name = user_name;
        this.project_name = project_name;
        this.application = application;
        mDBOpenHelper = new DBOpenHelper(application);
        mPointList=new MutableLiveData<>();
        ArrayList<Point> pointItems = new ArrayList<>();
        pointItems = mDBOpenHelper.getFeel_points(user_name,project_name);
        //showMsg(String.valueOf(pointItems.size()));
        // 模拟数据
//        pointItems.add(new PointItem(1, 0, 0, 5, "Sunny", null, null, null, 0));
//        pointItems.add(new PointItem(2, 40.712776, -74.005974, 4, "Cloudy", null, null, null, 0));
//        pointItems.add(new PointItem(3, 51.507351, -0.127758, 3, "Rainy", null, null, null, 0));
//        pointItems.add(new PointItem(4, 35.689487, 139.691711, 5, "Clear", null, null, null, 0));
//        pointItems.add(new PointItem(5, 48.856613, 2.352222, 4, "Windy", null, null, null, 0));
        mPointList.setValue(pointItems);
    }

    public LiveData<ArrayList<Point>> getPointList() {
        return mPointList;
    }
    private void showMsg(String msg){
        Toast.makeText(application,msg,Toast.LENGTH_SHORT).show();
    }
}
