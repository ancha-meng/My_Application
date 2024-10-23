package com.example.myapplication.ui.home;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class HomeFragment extends Fragment implements LocationSource,AMapLocationListener,InputName_Dialog.OnInputConfirmedListener {

    private MapView mapView;
    private  String lat;
    private  String lon;
    private AMap aMap;
    private OnLocationChangedListener mListener;
    private AMapLocationClient locationClient;
    private AMapLocationClientOption clientOption;
    private String project_name = "任务名称";
    private Button btn_new;
    private Button btn_name;
    private Button btn_save;
    private Button btn_finish;
    private Button btn_cancel;
    private Button btn_start;
    private TextView inform_point;
    private Button btn_camera;
    private Button btn_audio;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    public static final int TAKE_PHOTO = 1;
    private ImageView cameraPicture;
    private boolean isRecording = false;
    private MediaRecorder mMediaRecorder;
    //计时器部分
    private long startTime;
    private TextView timerTextView;
    private Handler handler = new Handler();
    private RelativeLayout input_view;
    //切换地图
    private Button btn_vcmap;
    private Button btn_rsmap;
    //完成取消评价点
    private Button btn_finish_point;
    private Button btn_cancel_point;

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    public HomeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        AMapLocationClient.updatePrivacyShow(getActivity(),true,true);
        AMapLocationClient.updatePrivacyAgree(getActivity(),true);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initview(savedInstanceState,view);

        //变量初始化
        btn_new = view.findViewById(R.id.new_project);
        btn_name = view.findViewById(R.id.name_project);
        btn_save = view.findViewById(R.id.save_project);
        btn_finish = view.findViewById(R.id.finish_project);
        btn_cancel = view.findViewById(R.id.cancel_project);
        btn_start = view.findViewById(R.id.start_eval);
        inform_point = view.findViewById(R.id.inform_point);
        btn_camera = view.findViewById(R.id.btn_camera);
        btn_audio = view.findViewById(R.id.btn_audio);
        cameraPicture = view.findViewById(R.id.photo_view);
        timerTextView = view.findViewById(R.id.timer);
        input_view = view.findViewById(R.id.input_point);
        //切换地图
        btn_vcmap = view.findViewById(R.id.vc_map);
        btn_rsmap = view.findViewById(R.id.rs_map);
        //完成取消评价点
        btn_finish_point = view.findViewById(R.id.finish_point);
        btn_cancel_point = view.findViewById(R.id.cancel_point);

        //按钮点击事件
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });
        btn_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(view);
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMsg("已保存");
            }
        });
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTipDialog("确认完成？");
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTipDialog("确认取消？");
            }
        });
        //开始评价
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取当前视图的布局参数
                //ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mapView.getLayoutParams();
                // 修改高度百分比
                //params.matchConstraintPercentHeight = 0.3f;
                // 将修改后的布局参数应用到视图上
                //mapView.setLayoutParams(params);
                mapView.setVisibility(View.GONE);
                btn_start.setVisibility(View.GONE);
                btn_name.setVisibility(View.GONE);
                btn_save.setVisibility(View.GONE);
                btn_finish.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
                btn_vcmap.setVisibility(View.GONE);
                btn_rsmap.setVisibility(View.GONE);
                input_view.setVisibility(View.VISIBLE);
                String text = "x:"+lon+" Y:"+lat+";温度:";
                inform_point.setText(text);
            }
        });
        //拍照结果
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // 处理拍照结果
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // 这里处理照片数据
                        Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                        cameraPicture.setImageBitmap(imageBitmap);
                        saveImage(imageBitmap);
                    }
                }
        );
        //相机
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 动态申请权限
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, TAKE_PHOTO);
                    // 启动相机程序
                    openCamera();
                } else {
                    // 启动相机程序
                    openCamera();
                }
            }
        });
        //录音
        btn_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 检查录音权限是否已获取
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED) {
                    // 未获取，请求权限
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECORD_AUDIO}, 1);
                }
                if(isRecording==false){
                    startRecording();
                    isRecording=true;
                    btn_audio.setText("结束录音");
                    start_timer();
                }
                else{
                    stopRecording();
                    isRecording=false;
                    btn_audio.setText("开始录音");
                    stop_timer();
                }
            }
        });
        //切换地图
        //切换标准地图
        btn_vcmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 设置卫星地图模式
            }
        });
        //切换卫星地图
        btn_rsmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 设置卫星地图模式
            }
        });
        //评价点完成
        btn_finish_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTipDialog_point("确认完成？");
            }
        });
        //评价点取消
        btn_cancel_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTipDialog_point("确认取消？");
            }
        });
        return view;
    }

    private void initview( Bundle savedInstanceState,View view){
        mapView= (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        if (aMap==null)
        {
            aMap=mapView.getMap();
        }
        else
        {
            aMap.clear();
            aMap=mapView.getMap();
        }
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }
    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener=listener;
        if(locationClient==null){
            try {
                locationClient = new AMapLocationClient(getActivity());
            } catch (Exception e) {
                e.printStackTrace();
            }
            clientOption=new AMapLocationClientOption();
            locationClient.setLocationListener(this);
            clientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//高精度定位
            clientOption.setOnceLocationLatest(true);//设置单次精确定位
            locationClient.setLocationOption(clientOption);
            locationClient.startLocation();
        }

    }
    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener=null;
        if(locationClient!=null){
            locationClient.stopLocation();
            locationClient.onDestroy();
        }
        locationClient=null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null&&aMapLocation != null) {
            if (aMapLocation != null
                    &&aMapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                lat = String.format("%.2f", aMapLocation.getLatitude());
                lon = String.format("%.2f", aMapLocation.getLongitude());
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode()+ ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
            }
        }
    }
    /**
     * 必须重写以下方法
     */
    @Override
    public void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(locationClient!=null){
            locationClient.onDestroy();
        }
    }
    /**
     * Toast提示
     * @param msg 提示内容
     */
    private void showMsg(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }

    public void showDialog(View view) {
        InputName_Dialog inputname_dialog = new InputName_Dialog();
        inputname_dialog.setOnInputConfirmedListener(this);
        inputname_dialog.show(getChildFragmentManager(), "inputDialog");
    }
    @Override
    public void onInputConfirmed(String inputText) {
        // 在这里处理接收到的输入文字
        project_name = inputText;
        btn_name.setText(project_name);
        btn_new.setVisibility(View.GONE);
        btn_name.setVisibility(View.VISIBLE);
        btn_save.setVisibility(View.VISIBLE);
        btn_finish.setVisibility(View.VISIBLE);
        btn_cancel.setVisibility(View.VISIBLE);
        btn_start.setVisibility(View.VISIBLE);
    }
    private void showTipDialog(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage(text);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                btn_new.setVisibility(View.VISIBLE);
                btn_name.setVisibility(View.GONE);
                btn_save.setVisibility(View.GONE);
                btn_finish.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
                btn_start.setVisibility(View.GONE);
                dialogInterface.dismiss();
                project_name="任务名称";
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    private void showTipDialog_point(String text){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage(text);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                input_view.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.VISIBLE);
                btn_name.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.VISIBLE);
                btn_finish.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                btn_vcmap.setVisibility(View.VISIBLE);
                btn_rsmap.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
    private void openCamera() {
        if (activityResultLauncher != null) {
            // launch的输入参数是泛型，对应ActivityResultLauncher<Intent>
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 确保有相机应用可用
            if (takePictureIntent.resolveActivity(requireActivity().getPackageManager())!= null) {
                activityResultLauncher.launch(takePictureIntent);
            }
        }
    }
    private void saveImage(Bitmap bitmap) {
        //在API29及之后是不需要申请的，默认是允许的
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "image.jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            // 发送刷新相册的广播
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            getActivity().sendBroadcast(intent);
        } catch (IOException e) {
            e.printStackTrace();
            showMsg("保存失败");
        }
    }

    public void startRecording() {
        //在API29及之后是不需要申请的，默认是允许的
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        showMsg("开始录音，再次点击结束录音");
        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null)
            mMediaRecorder = new MediaRecorder();
        try {
            /* ②setAudioSource/setVedioSource */
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);// 设置麦克风
            /*
             * ②设置输出文件的格式：THREE_GPP/MPEG-4/RAW_AMR/Default THREE_GPP(3gp格式
             * ，H263视频/ARM音频编码)、MPEG-4、RAW_AMR(只支持音频且音频编码要求为AMR_NB)
             */
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            /* ②设置音频文件的编码：AAC/AMR_NB/AMR_MB/Default 声音的（波形）的采样 */
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            /* ③准备 */
            mMediaRecorder.setOutputFile(getOutputPath());
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
        } catch (IOException e) {
            Log.e("RecordingError", "Error starting recording: " + e.getMessage());
        }
    }

    public void stopRecording() {
        try {
            showMsg("结束录音");
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        } catch (RuntimeException e) {
            Log.e("RecordingError", "Error stopping recording: " + e.getMessage());
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            File file = new File(getOutputPath());
            if (file.exists())
                file.delete();
        }
    }
    private String getOutputPath(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RECORDINGS), "recordinging.mp3");
            return file.getAbsolutePath();
        }
        else{
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "recordinging.mp3");
            return file.getAbsolutePath();
        }
    }
    public void start_timer() {
        startTime = SystemClock.elapsedRealtime();
        updateTimer();
    }

    private void updateTimer() {
        long elapsedTime = SystemClock.elapsedRealtime() - startTime;
        int minutes = (int) (elapsedTime / 60000);
        int seconds = (int) ((elapsedTime % 60000) / 1000);

        String timeText = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(timeText);

        handler.postDelayed(this::updateTimer, 1000);
    }

    public void stop_timer() {
        handler.removeCallbacksAndMessages(null);
    }
}