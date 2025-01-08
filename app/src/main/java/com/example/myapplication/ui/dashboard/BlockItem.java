package com.example.myapplication.ui.dashboard;

import android.widget.TextView;
import java.util.Date;


//——————————————————————此类负责定义专有的数据块-1类：存储单个任务的相关信息，便于统一管理————————————————————
public class BlockItem {
    private String Task_id;
    private String Task_time;

    // 构造函数
    public BlockItem(String Task_id) {
        this.Task_id = Task_id;
    }

    // get方法
    public String getTask_id() {
        return Task_id;
    }

    public String getTask_time() {
        return null;
    }

    // set方法
    public void setTask_id(String Task_id) {
        this.Task_id = Task_id;
    }

    public void setTask_time(String Task_time) {
        this.Task_time = Task_time;
    }
}
