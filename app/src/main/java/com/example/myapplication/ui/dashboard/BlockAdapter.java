package com.example.myapplication.ui.dashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;
//适配器——表示层，用于直接操作页面
public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockViewHolder> {

    private List<BlockItem> blockList; // 修改变量名以匹配数据类型

    // ViewHolder类
    public static class BlockViewHolder extends RecyclerView.ViewHolder {
        // 通常是视图的值引用
        TextView Task_id;
        TextView Task_time;

        public BlockViewHolder(View itemView) {
            super(itemView);
            // 获取视图中的id，这两个引用将用于每个block的数据赋值
            Task_id = itemView.findViewById(R.id.task_id);
            Task_time = itemView.findViewById(R.id.task_time);
        }
    }

    //更新blocklist数据
    public void setBlockList(List<BlockItem> blockList) {
        this.blockList = blockList;
    }

    // 加载dblock_layout.xml布局文件，完成样式的绑定
    @Override
    public BlockViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dblock_layout, parent, false);
        return new BlockViewHolder(view);
    }

    //数据绑定函数，基于list数据的位置，对blockitem类承载的数据进行传递
    @Override
    public void onBindViewHolder(BlockViewHolder holder, int position) {
        BlockItem blockItem = blockList.get(position);
        holder.Task_id.setText(blockItem.getTask_id());
        holder.Task_time.setText(blockItem.getTask_time().toString());
    }

    @Override
    public int getItemCount() {
        return blockList.size();
    }
}
