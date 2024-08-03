package com.example.myapplication.ui.dashboard;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;
//控制器中间层，完成列表碎片与布局样式的绑定，以及数据的存储管理
public class BlockAdapter extends RecyclerView.Adapter<BlockAdapter.BlockViewHolder> {

    private List<BlockItem> blockList;
    private OnItemClickListener onItemClickListener;

    //回调，处理每个信息块的点击事件
    public interface OnItemClickListener {
        void onItemClick(String taskId);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


    public static class BlockViewHolder extends RecyclerView.ViewHolder {
        TextView Task_id;
        TextView Task_time;
        TextView Point_num;

        public BlockViewHolder(View itemView) {
            super(itemView);
            Task_id = itemView.findViewById(R.id.task_id);
            Task_time = itemView.findViewById(R.id.task_time);
            Point_num=itemView.findViewById(R.id.points_num);
        }
    }

    //对外开放的数据修改，用于接收外界的数据
    public void setBlockList(List<BlockItem> blockList) {
        this.blockList = blockList;
    }

    //完成样式的绑定
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
        holder.Point_num.setText(blockItem.getPoint_num());
        // 设置激活点击回调
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                Log.e("sss1","dianji1");
                onItemClickListener.onItemClick(blockItem.getTask_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return blockList.size();
    }
}
