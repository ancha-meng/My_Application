package com.example.myapplication.ui.dashboard;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

//任务点页面的数据绑定（控制层）
public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.PointsViewHolder>{

    private List<PointItem> pointlist;
    //对外开放的数据修改，用于接收外界的数据
    public void setPointList(List<PointItem> pointList) {
        this.pointlist = pointList;
    }
    @Override
    public int getItemCount() {
        return pointlist.size();
    }
    //视图要素集合
    public static class PointsViewHolder extends RecyclerView.ViewHolder {
        TextView Point_id;
        TextView Point_location;
        TextView Point_score;
        ImageView Point_image;
        TextView Point_audio;

        public PointsViewHolder(View itemView) {
            super(itemView);
            //通过要素ID索引获取视图对象
            Point_id=itemView.findViewById(R.id.point_id);
            Point_location=itemView.findViewById(R.id.point_location);
            Point_score=itemView.findViewById(R.id.point_score);
            Point_image=itemView.findViewById(R.id.point_image);

        }
    }

    @Override
    public PointsAdapter.PointsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.points_layout, parent, false);
        return new PointsAdapter.PointsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(PointsViewHolder holder, int position) {
        PointItem pointItem = pointlist.get(position);
        holder.Point_id.setText(Integer.toString(pointItem.getId()));
        holder.Point_score.setText(Integer.toString(pointItem.getRating()));
        holder.Point_location.setText
                ('('+Double.toString(pointItem.getLatitude())
                        +','+
                        Double.toString(pointItem.getLongitude())+')');

    }

}
