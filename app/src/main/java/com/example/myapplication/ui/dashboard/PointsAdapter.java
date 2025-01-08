package com.example.myapplication.ui.dashboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.database.Point;

import java.util.ArrayList;
import java.util.List;

//任务点页面的数据绑定（控制层）
public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.PointsViewHolder>{

    private ArrayList<Point> pointlist;
    //对外开放的数据修改，用于接收外界的数据
    public void setPointList(ArrayList<Point> pointList) {
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
        Point pointItem = pointlist.get(position);
        holder.Point_id.setText(pointItem.getPoint_name());
        holder.Point_location.setText(pointItem.get_X()+", "+pointItem.get_Y());
        byte[] b = pointItem.getImage_data().get(0);
        Bitmap originalBitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
//        int imageViewWidth = holder.Point_image.getWidth();
//        int imageViewHeight = holder.Point_image.getHeight();
//        float scaleWidth = ((float) imageViewWidth) / originalBitmap.getWidth();
//        float scaleHeight = ((float) imageViewHeight) / originalBitmap.getHeight();
//        float scale = Math.min(scaleWidth, scaleHeight);
        float scale = 0.5F;
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,
                (int) (originalBitmap.getWidth() * scale), (int) (originalBitmap.getHeight() * scale), true);
        holder.Point_image.setImageBitmap(scaledBitmap);
        // 注意：如果originalBitmap不再需要，你应该及时回收它以释放内存
        if (originalBitmap != null && !originalBitmap.isRecycled()) {
            originalBitmap.recycle();
            originalBitmap = null;
        }
    }

}
