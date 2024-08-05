package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapException;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.LatLngBounds;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.myapplication.R;

// 此页面为展示点信息的子碎片，以地图的形式显示
public class SubMapFragment extends Fragment {

    private MapView mapView;
    private AMap aMap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_map_layout, container, false);

        // 绑定地图视图
        mapView = view.findViewById(R.id.sub_map);
        mapView.onCreate(savedInstanceState); // 此方法必须重写

        // 初始化地图
        initMap();

        return view;
    }

    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }

        // 设置定位样式
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.radiusFillColor(0x00000000); // 设置圆形缓冲区为透明
        myLocationStyle.strokeColor(0x00000000); // 设置圆形缓冲区边框为透明
        aMap.setMyLocationStyle(myLocationStyle);

        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(20)); // 设置更大比例尺的显示级别
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    // 接收最小外包矩形的参数，调整地图显示级别
    public void moveToBoundingBox(LatLng southwest, LatLng northeast) {
        try {
            if (aMap != null) {
                LatLngBounds bounds = new LatLngBounds(southwest, northeast);
                aMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 0));
            }
        } catch (AMapException e) {
            e.printStackTrace();
            // 处理异常，例如显示错误信息
        }
    }
}
