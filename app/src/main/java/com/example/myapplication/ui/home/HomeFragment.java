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
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
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
import com.example.myapplication.database.AuthManager;
import com.example.myapplication.database.DBOpenHelper;
import com.example.myapplication.database.Point;
import com.example.myapplication.database.User;
import com.example.myapplication.database.connect;
import com.example.myapplication.databinding.FragmentHomeBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

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
    private Button btn_start2;
    private TextView inform_point;
    private TextView inform_point2;
    private Button btn_camera;
    private Button btn_audio;
    private Button btn_play;
    private Button btn_cleanimages;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    public static final int TAKE_PHOTO = 1;
    private ImageView cameraPicture1;
    private ImageView cameraPicture2;
    private ImageView cameraPicture3;
    private ImageView cameraPicture4;
    private boolean isRecording = false;
    private MediaRecorder mMediaRecorder;
    private MediaPlayer mPlayer = null;
    private Uri photoURI=null;
    private String imageFileName = null;
    private String audioFileName = null;
    //计时器部分
    private long startTime;
    private TextView timerTextView;
    private Handler handler = new Handler();
    private ScrollView input_view;
    private ScrollView input_view2;
    //切换地图
    private Button btn_vcmap;
    private Button btn_rsmap;
    //完成取消评价点
    private Button btn_finish_point;
    private Button btn_cancel_point;
    private Button btn_finish_point2;
    private Button btn_cancel_point2;
    //评价点信息
    private EditText point_name;
    private EditText point_name2;
    private EditText type;
    private EditText type2;
    private EditText CHILL;
    private EditText HEAT_INDEX;
    private EditText T_limit;
    private EditText temp;
    private EditText loudian_temp;
    private EditText diqiu_temp;
    private EditText shiqiu_temp;
    private EditText xiangdui_wet;
    private EditText pressure;
    private EditText altitude;
    private EditText density;
    private EditText air_TVOC;
    private EditText air_C6H6;
    private EditText air_HCHO;
    private EditText air_CO2;
    private EditText air_PM25;
    private EditText air_PM10;
    private EditText light;
    private EditText wind;
    private RadioGroup beauty;
    private RadioGroup safe;
    private RadioGroup clean;
    private RadioGroup alive;
    private RadioGroup depress;
    private RadioGroup boring;
    private RadioGroup rich;
    private RadioGroup hot;
    private RadioGroup body;
    private EditText other;
    private EditText beauty_factor;
    private EditText safe_factor;
    private EditText clean_factor;
    private EditText alive_factor;
    private EditText depress_factor;
    private EditText boring_factor;
    private EditText rich_factor;


    //database
    private DBOpenHelper mDBOpenHelper;
    private String user_name = null;
    private String projectname = null;
    private int point_count = 0;
    private Point point = new Point();
    private ArrayList<Point> feel_points = new ArrayList<Point>();
    private ArrayList<Point> value_points = new ArrayList<Point>();
    private int image_count = 0;
    private int audio_count = 0;
    private String evel_type;
    private boolean is_exist = false;

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

        mDBOpenHelper = new DBOpenHelper(getActivity());

//        // 获取传递过来的Bundle
//        Bundle bundle = getArguments();
//        if (bundle != null) {
//            String someData = bundle.getString("name");
//            user_name = someData;
//            //showMsg(someData);
//            // 使用传递过来的数据
//        }
        if(AuthManager.getInstance().isLoggedIn()){
            user_name = AuthManager.getInstance().getUsername();
        }

        //变量初始化
        btn_new = view.findViewById(R.id.new_project);
        btn_name = view.findViewById(R.id.name_project);
        btn_save = view.findViewById(R.id.save_project);
        btn_finish = view.findViewById(R.id.finish_project);
        btn_cancel = view.findViewById(R.id.cancel_project);
        btn_start = view.findViewById(R.id.start_eval);
        btn_start2 = view.findViewById(R.id.start_eval2);
        inform_point = view.findViewById(R.id.inform_point);
        inform_point2 = view.findViewById(R.id.inform_point2);
        btn_camera = view.findViewById(R.id.btn_camera);
        btn_audio = view.findViewById(R.id.btn_audio);
        btn_play = view.findViewById(R.id.btn_play);
        btn_cleanimages = view.findViewById(R.id.btn_clearimages);
        cameraPicture1 = view.findViewById(R.id.photo_view1);
        cameraPicture2 = view.findViewById(R.id.photo_view2);
        cameraPicture3 = view.findViewById(R.id.photo_view3);
        cameraPicture4 = view.findViewById(R.id.photo_view4);
        timerTextView = view.findViewById(R.id.timer);
        input_view = view.findViewById(R.id.ScrollView_input_point);
        input_view2 = view.findViewById(R.id.ScrollView_input_point2);
        //切换地图
        btn_vcmap = view.findViewById(R.id.vc_map);
        btn_rsmap = view.findViewById(R.id.rs_map);
        //完成取消评价点
        btn_finish_point = view.findViewById(R.id.finish_point);
        btn_cancel_point = view.findViewById(R.id.cancel_point);
        btn_finish_point2 = view.findViewById(R.id.finish_point2);
        btn_cancel_point2 = view.findViewById(R.id.cancel_point2);
        //评价点信息
        point_name = view.findViewById(R.id.name_input);
        point_name2 = view.findViewById(R.id.name_input2);
        type = view.findViewById(R.id.type);
        type2 = view.findViewById(R.id.type2);
        HEAT_INDEX = view.findViewById(R.id.HEAT_INDEX);
        T_limit = view.findViewById(R.id.T_limit);
        temp = view.findViewById(R.id.temperature);
        loudian_temp = view.findViewById(R.id.loudian_temperature);
        diqiu_temp = view.findViewById(R.id.diqiu_temperature);
        shiqiu_temp = view.findViewById(R.id.shiqiu_temperature);
        xiangdui_wet = view.findViewById(R.id.xiangdui_wetness);
        CHILL = view.findViewById(R.id.CHILL);
        pressure = view.findViewById(R.id.pressure);
        altitude = view.findViewById(R.id.altitude);
        density = view.findViewById(R.id.density);
        air_TVOC = view.findViewById(R.id.air_TVOC);
        air_C6H6 = view.findViewById(R.id.air_C6H6);
        air_HCHO = view.findViewById(R.id.air_HCHO);
        air_CO2 = view.findViewById(R.id.air_CO2);
        air_PM25 = view.findViewById(R.id.air_PM25);
        air_PM10 = view.findViewById(R.id.air_PM10);
        light = view.findViewById(R.id.light_intensity);
        wind = view.findViewById(R.id.wind);
        beauty = view.findViewById(R.id.radioGroup_beauty);
        safe = view.findViewById(R.id.radioGroup_safe);
        clean = view.findViewById(R.id.radioGroup_clean);
        alive = view.findViewById(R.id.radioGroup_alive);
        depress = view.findViewById(R.id.radioGroup_depress);
        boring = view.findViewById(R.id.radioGroup_boring);
        rich = view.findViewById(R.id.radioGroup_rich);
        hot = view.findViewById(R.id.radioGroup_hot);
        body = view.findViewById(R.id.radioGroup_body);
        other = view.findViewById(R.id.other);
        beauty_factor = view.findViewById(R.id.beauty_factor);
        safe_factor = view.findViewById(R.id.safe_factor);
        clean_factor = view.findViewById(R.id.clean_factor);
        alive_factor = view.findViewById(R.id.alive_factor);
        depress_factor = view.findViewById(R.id.depress_factor);
        boring_factor = view.findViewById(R.id.boring_factor);
        rich_factor = view.findViewById(R.id.rich_factor);

        //按钮点击事件
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (user_name == null){
                    showTipDialog("请先登录");
                }else {
                    showDialog(view);
                }
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
                if(!mDBOpenHelper.isExistProject(projectname,user_name))
                {
                    mDBOpenHelper.add_project(projectname,user_name);
                }
                mDBOpenHelper.add_points(user_name,projectname,value_points,feel_points);
                showMsg("已保存");
                value_points=new ArrayList<Point>();
                feel_points=new ArrayList<Point>();
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
                btn_start2.setVisibility(View.GONE);
                btn_name.setVisibility(View.GONE);
                btn_save.setVisibility(View.GONE);
                btn_finish.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
                btn_vcmap.setVisibility(View.GONE);
                btn_rsmap.setVisibility(View.GONE);
                input_view.setVisibility(View.VISIBLE);
                String text = "x:"+lon+" Y:"+lat;
                inform_point.setText(text);
                point.set_xy(lon,lat);
                point_count = point_count + 1;
                image_count = 0;
                audio_count = 0;
                evel_type="环境变量评价";
            }
        });
        btn_start2.setOnClickListener(new View.OnClickListener() {
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
                btn_start2.setVisibility(View.GONE);
                btn_name.setVisibility(View.GONE);
                btn_save.setVisibility(View.GONE);
                btn_finish.setVisibility(View.GONE);
                btn_cancel.setVisibility(View.GONE);
                btn_vcmap.setVisibility(View.GONE);
                btn_rsmap.setVisibility(View.GONE);
                input_view2.setVisibility(View.VISIBLE);
                String text = "x:"+lon+" Y:"+lat;
                inform_point2.setText(text);
                point.set_xy(lon,lat);
                point_count = point_count + 1;
                image_count = 0;
                audio_count = 0;
                evel_type="环境感受评价";
            }
        });
        //拍照结果
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    // 处理拍照结果
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Uri uri = null;
                        if (result.getData() != null) {
                            uri = result.getData().getData(); // 这里可能是缩略图的URI，也可能是你传递的URI
                        } else {
                            // 如果没有通过Intent返回URI，则使用你之前设置的URI
                            uri = photoURI; // 确保这个变量在你的作用域内
                        }
                        try {
                            // 这里处理照片数据
                            //Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                            Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                            float scaleWidth = ((float) 1024) / imageBitmap.getWidth();
                            float scaleHeight = ((float) 1024) / imageBitmap.getHeight();
                            float scale = Math.max(scaleWidth, scaleHeight);
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(imageBitmap,
                                    (int) (imageBitmap.getWidth() * scale), (int) (imageBitmap.getHeight() * scale), true);
                            saveImage(scaledBitmap);
                            int imageViewWidth = cameraPicture1.getWidth();
                            int imageViewHeight = cameraPicture1.getHeight();
                            float scaleWidth1 = ((float) imageViewWidth) / scaledBitmap.getWidth();
                            float scaleHeight1 = ((float) imageViewHeight) / scaledBitmap.getHeight();
                            float scale1 = Math.min(scaleWidth1, scaleHeight1);
                            Bitmap scaledBitmap1 = Bitmap.createScaledBitmap(scaledBitmap,
                                    (int) (scaledBitmap.getWidth() * scale1), (int) (scaledBitmap.getHeight() * scale1), true);
                            if(image_count==1){
                                cameraPicture1.setImageBitmap(scaledBitmap1);
                            }
                            if(image_count==2){
                                cameraPicture2.setImageBitmap(scaledBitmap1);
                            }
                            if(image_count==3){
                                cameraPicture3.setImageBitmap(scaledBitmap1);
                            }
                            if(image_count==4){
                                cameraPicture4.setImageBitmap(scaledBitmap1);
                            }
                            // 注意：如果originalBitmap不再需要，你应该及时回收它以释放内存
                            if (imageBitmap != null && !imageBitmap.isRecycled()) {
                                imageBitmap.recycle();
                                imageBitmap = null;
                            }
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
        );
        //相机
        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(image_count==4){
                    showMsg("照片已达上限");
                }
                else {
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
            }
        });
        btn_cleanimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getActivity())
                        .setTitle("提示").setMessage("确认清空？")
                        .setPositiveButton("确定", (dialog, which) -> {
                            point.clear_image();
                            cameraPicture1.setImageBitmap(null);
                            cameraPicture2.setImageBitmap(null);
                            cameraPicture3.setImageBitmap(null);
                            cameraPicture4.setImageBitmap(null);
                            image_count=0;
                            dialog.dismiss();
                        })
                        .setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                builder.show();
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
                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                    audioFileName = "recordinging_" + timeStamp + "_";
                    startRecording();
                    isRecording=true;
                    btn_audio.setText("结束录音");
                    btn_play.setVisibility(View.GONE);
                    start_timer();
                }
                else{
                    stopRecording();
                    isRecording=false;
                    btn_audio.setText("开始录音");
                    btn_play.setVisibility(View.VISIBLE);
                    stop_timer();
                    audio_count = audio_count +1;
                }
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File tempFile = null; // 创建一个临时文件
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                    tempFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RECORDINGS), "temp_audio.mp3");
                }
                try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                    fos.write(point.getAudio_data().get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                    // 处理异常
                }
                if (mPlayer == null) {
                    // 如果 MediaPlayer 为空，则创建一个新的实例
                    mPlayer = new MediaPlayer();
                    try {
                        mPlayer.setDataSource(tempFile.getAbsolutePath());
                        mPlayer.prepare(); // 准备播放
                    } catch (IOException e) {
                        e.printStackTrace();
                        // 处理无法设置数据源或准备播放器的错误
                        Toast.makeText(getActivity(), "无法播放音频文件", Toast.LENGTH_SHORT).show();
                        return; // 退出方法，避免后续代码执行
                    }

                    // 设置播放完成监听器
                    mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            // 播放完成时重置按钮文本
                            btn_play.setText("播放录音");
                            // 释放资源
                            mp.release();
                            mPlayer = null; // 将 MediaPlayer 引用设置为 null
                        }
                    });
                }

                if (mPlayer.isPlaying()) {
                    // 如果 MediaPlayer 正在播放，则停止播放
                    mPlayer.stop();
                    btn_play.setText("播放录音");
                } else {
                    // 如果 MediaPlayer 没有播放，则开始播放
                    mPlayer.start();
                    btn_play.setText("停止播放");
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
                String pointname = point_name.getText().toString().trim();
                String point_type = type.getText().toString().trim();
                String chil = CHILL.getText().toString().trim();
                String heat_ind = HEAT_INDEX.getText().toString().trim();
                String t_limi = T_limit.getText().toString().trim();
                String temperature = temp.getText().toString().trim();
                String loudian_temperature = loudian_temp.getText().toString().trim();
                String diqiu_temperature = diqiu_temp.getText().toString().trim();
                String shiqiu_temperature = shiqiu_temp.getText().toString().trim();
                String xiangdui_wetness = xiangdui_wet.getText().toString().trim();
                String press = pressure.getText().toString().trim();
                String altitu = altitude.getText().toString().trim();
                String densi = density.getText().toString().trim();
                String TVOC = air_TVOC.getText().toString().trim();
                String C6H6 = air_C6H6.getText().toString().trim();
                String HCHO = air_HCHO.getText().toString().trim();
                String CO2 = air_CO2.getText().toString().trim();
                String PM25= air_PM25.getText().toString().trim();
                String PM10 = air_PM10.getText().toString().trim();
                String daylight = light.getText().toString().trim();
                String wind_speed = wind.getText().toString().trim();
                if (TextUtils.isEmpty(pointname)){showMsg("评价点名称不能为空！");}
                else {
                    if (TextUtils.isEmpty(point_type)){showMsg("场景类型不能为空！");}
                    else {
                        if ((TextUtils.isEmpty(temperature) || temperature == "") ||
                                (TextUtils.isEmpty(loudian_temperature) || loudian_temperature == "") ||
                                (TextUtils.isEmpty(diqiu_temperature) || diqiu_temperature == "") ||
                                (TextUtils.isEmpty(shiqiu_temperature) || shiqiu_temperature == "") ||
                                (TextUtils.isEmpty(xiangdui_wetness) || xiangdui_wetness == "") ||
                                (TextUtils.isEmpty(chil) || chil == "") ||
                                (TextUtils.isEmpty(heat_ind) || heat_ind == "") ||
                                (TextUtils.isEmpty(t_limi) || t_limi == "") ||
                                (TextUtils.isEmpty(press) || press == "") ||
                                (TextUtils.isEmpty(altitu) || altitu == "") ||
                                (TextUtils.isEmpty(densi) || densi == "") ||
                                (TextUtils.isEmpty(TVOC) || TVOC == "") ||
                                (TextUtils.isEmpty(HCHO) || HCHO == "") ||
                                (TextUtils.isEmpty(C6H6) || C6H6 == "") ||
                                (TextUtils.isEmpty(CO2) || CO2 == "") ||
                                (TextUtils.isEmpty(PM25) || PM25 == "") ||
                                (TextUtils.isEmpty(PM10) || PM10 == "") ||
                                (TextUtils.isEmpty(daylight) || daylight == "") ||
                                (TextUtils.isEmpty(wind_speed) || wind_speed == "")) {
                            showMsg("评价点信息不能为空！");
                        } else {
                            String pattern1 = "[-+]?[0-9]*";
                            String pattern2 = "[-+]?[0-9]*\\.?[0-9]+";
                            if ((temperature.matches(pattern1) || temperature.matches(pattern2)) &&
                                    (loudian_temperature.matches(pattern1) || loudian_temperature.matches(pattern2)) &&
                                    (diqiu_temperature.matches(pattern1) || diqiu_temperature.matches(pattern2)) &&
                                    (shiqiu_temperature.matches(pattern1) || shiqiu_temperature.matches(pattern2)) &&
                                    (xiangdui_wetness.matches(pattern1) || xiangdui_wetness.matches(pattern2)) &&
                                    (chil.matches(pattern1) || chil.matches(pattern2)) &&
                                    (heat_ind.matches(pattern1) || heat_ind.matches(pattern2)) &&
                                    (t_limi.matches(pattern1) || t_limi.matches(pattern2)) &&
                                    (press.matches(pattern1) || press.matches(pattern2)) &&
                                    (altitu.matches(pattern1) || altitu.matches(pattern2)) &&
                                    (densi.matches(pattern1) || densi.matches(pattern2)) &&
                                    (TVOC.matches(pattern1) || TVOC.matches(pattern2)) &&
                                    (C6H6.matches(pattern1) || C6H6.matches(pattern2)) &&
                                    (HCHO.matches(pattern1) || HCHO.matches(pattern2)) &&
                                    (CO2.matches(pattern1) || CO2.matches(pattern2)) &&
                                    (PM25.matches(pattern1) || PM25.matches(pattern2)) &&
                                    (PM10.matches(pattern1) || PM10.matches(pattern2)) &&
                                    (daylight.matches(pattern1) || daylight.matches(pattern2)) &&
                                    (wind_speed.matches(pattern1) || wind_speed.matches(pattern2))) {
                                point.setname(pointname);
                                point.set_type(point_type);
                                point.set_value(chil,heat_ind,shiqiu_temperature,t_limi,diqiu_temperature,temperature,xiangdui_wetness,loudian_temperature,
                                        press,altitu,densi, TVOC, C6H6, HCHO, CO2, PM25, PM10, daylight, wind_speed);
                                showTipDialog_point("确认完成？");
                            } else {
                                showMsg("评价点信息不规范！");
                            }
                        }
                    }
                }
            }
        });
        btn_finish_point2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pointname = point_name2.getText().toString().trim();
                String point_type = type2.getText().toString().trim();
                String rb_hot="";
                String rb_body="";
                String rb_beauty="";
                String rb_safe="";
                String rb_clean="";
                String rb_alive="";
                String rb_depress="";
                String rb_boring="";
                String rb_rich="";
                String beauty_f = beauty_factor.getText().toString().trim();
                String safe_f = safe_factor.getText().toString().trim();
                String clean_f = clean_factor.getText().toString().trim();
                String alive_f = alive_factor.getText().toString().trim();
                String depress_f = depress_factor.getText().toString().trim();
                String boring_f = boring_factor.getText().toString().trim();
                String rich_f = rich_factor.getText().toString().trim();
                for(int i = 0 ;i < hot.getChildCount();i++){
                    RadioButton rb = (RadioButton)hot.getChildAt(i);
                    if(rb.isChecked()){
                        rb_hot=rb.getText().toString();
                        break;
                    }
                }
                for(int i = 0 ;i < body.getChildCount();i++){
                    RadioButton rb = (RadioButton)body.getChildAt(i);
                    if(rb.isChecked()){
                        rb_body=rb.getText().toString();
                        if(rb_body=="其他（请注明）"){
                            rb_body = other.getText().toString().trim();
                        }
                        break;
                    }
                }
                for(int i = 0 ;i < beauty.getChildCount();i++){
                    RadioButton rb = (RadioButton)beauty.getChildAt(i);
                    if(rb.isChecked()){
                        rb_beauty=rb.getText().toString();
                        break;
                    }
                }
                for(int i = 0 ;i < safe.getChildCount();i++){
                    RadioButton rb = (RadioButton)safe.getChildAt(i);
                    if(rb.isChecked()){
                        rb_safe=rb.getText().toString();
                        break;
                    }
                }
                for(int i = 0 ;i < clean.getChildCount();i++){
                    RadioButton rb = (RadioButton)clean.getChildAt(i);
                    if(rb.isChecked()){
                        rb_clean=rb.getText().toString();
                        break;
                    }
                }
                for(int i = 0 ;i < alive.getChildCount();i++){
                    RadioButton rb = (RadioButton)alive.getChildAt(i);
                    if(rb.isChecked()){
                        rb_alive=rb.getText().toString();
                        break;
                    }
                }
                for(int i = 0 ;i < depress.getChildCount();i++){
                    RadioButton rb = (RadioButton)depress.getChildAt(i);
                    if(rb.isChecked()){
                        rb_depress=rb.getText().toString();
                        break;
                    }
                }
                for(int i = 0 ;i < boring.getChildCount();i++){
                    RadioButton rb = (RadioButton)boring.getChildAt(i);
                    if(rb.isChecked()){
                        rb_boring=rb.getText().toString();
                        break;
                    }
                }
                for(int i = 0 ;i < rich.getChildCount();i++){
                    RadioButton rb = (RadioButton)rich.getChildAt(i);
                    if(rb.isChecked()){
                        rb_rich=rb.getText().toString();
                        break;
                    }
                }
                if (TextUtils.isEmpty(pointname)){showMsg("评价点名称不能为空！");}
                else {
                    if (TextUtils.isEmpty(point_type)){showMsg("场景类型不能为空！");}
                    else {
                        if ((TextUtils.isEmpty(beauty_f) || beauty_f == "") ||
                                (TextUtils.isEmpty(safe_f) || safe_f == "") ||
                                (TextUtils.isEmpty(clean_f) || clean_f == "") ||
                                (TextUtils.isEmpty(alive_f) || alive_f == "") ||
                                (TextUtils.isEmpty(depress_f) || depress_f == "") ||
                                (TextUtils.isEmpty(boring_f) || boring_f == "") ||
                                (TextUtils.isEmpty(rich_f) || rich_f == "") ||
                                (TextUtils.isEmpty(rb_body) || rb_body == "")) {
                            showMsg("评价内容不能为空！");
                        } else {
                                if (image_count < 4) {
                                    showMsg("请拍摄4个方向照片！");
                                } else {
                                    if (audio_count == 0) {
                                        showMsg("请录音！");
                                    } else {
                                        point.setname(pointname);
                                        point.set_type(point_type);
                                        point.set_hotbody(rb_hot, rb_body);
                                        point.set_score(rb_beauty, rb_safe, rb_clean, rb_alive, rb_depress, rb_boring, rb_rich);
                                        point.set_factor(beauty_f,safe_f,clean_f,alive_f,depress_f,boring_f,rich_f);
                                        showTipDialog_point("确认完成？");
                                    }
                                }
                        }
                    }
                }
            }
        });
        //评价点取消
        btn_cancel_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTipDialog_point("确认取消？");
            }
        });
        btn_cancel_point2.setOnClickListener(new View.OnClickListener() {
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
        final Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                if(!is_exist){
                    if(mDBOpenHelper.isExistProject(inputText,user_name))
                    {
                        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity())
                                .setTitle("提示").setMessage("该任务已存在，继续任务？")
                                .setPositiveButton("确定", (dialog, which) -> {
                                    project_name = inputText;
                                    projectname = project_name;
                                    btn_name.setText(project_name);
                                    btn_new.setVisibility(View.GONE);
                                    btn_name.setVisibility(View.VISIBLE);
                                    btn_save.setVisibility(View.VISIBLE);
                                    btn_finish.setVisibility(View.VISIBLE);
                                    btn_cancel.setVisibility(View.VISIBLE);
                                    btn_start.setVisibility(View.VISIBLE);
                                    btn_start2.setVisibility(View.VISIBLE);
                                    getActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
                                    dialog.dismiss();
                                })
                                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
                        builder.show();
                    }else{
                        project_name = inputText;
                        projectname = project_name;
                        btn_name.setText(project_name);
                        btn_new.setVisibility(View.GONE);
                        btn_name.setVisibility(View.VISIBLE);
                        btn_save.setVisibility(View.VISIBLE);
                        btn_finish.setVisibility(View.VISIBLE);
                        btn_cancel.setVisibility(View.VISIBLE);
                        btn_start.setVisibility(View.VISIBLE);
                        btn_start2.setVisibility(View.VISIBLE);
                        getActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
                    }
                }else{
                    showMsg("该任务已存在！");
                }
                return false;
            }
        });
        // 在这里处理接收到的输入文字
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                try {
                    is_exist = connect.exist_col2("project","project_name",inputText,"user_name",user_name);
                } catch (SQLException e) {
                    e.printStackTrace();
                }handler.sendMessage(msg);//跳转到handler
            }
        }).start();
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
                btn_start2.setVisibility(View.GONE);
                getActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);
                dialogInterface.dismiss();
                project_name="任务名称";
                if (text == "确认完成？") {
                    if(!mDBOpenHelper.isExistProject(projectname,user_name))
                    {
                        mDBOpenHelper.add_project(projectname,user_name);
                    }
                    mDBOpenHelper.add_points(user_name,projectname,value_points,feel_points);
                }
                projectname = null;
                point_count = 0;
                feel_points = new ArrayList<Point>();
                value_points = new ArrayList<Point>();
//                ArrayList<String> data = mDBOpenHelper.check();
//                for (int x = 0; x < data.size(); x++){
//                    showTipDialog(data.get(x));
//                }
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
                input_view2.setVisibility(View.GONE);
                btn_play.setVisibility(View.GONE);
                mapView.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.VISIBLE);
                btn_start2.setVisibility(View.VISIBLE);
                btn_name.setVisibility(View.VISIBLE);
                btn_save.setVisibility(View.VISIBLE);
                btn_finish.setVisibility(View.VISIBLE);
                btn_cancel.setVisibility(View.VISIBLE);
                btn_vcmap.setVisibility(View.VISIBLE);
                btn_rsmap.setVisibility(View.VISIBLE);
                dialogInterface.dismiss();
                image_count = 0;
                audio_count = 0;
//                String pointname = point_name.getText().toString().trim();
                if(text=="确认完成？"){
                    if(evel_type=="环境变量评价"){
                        value_points.add(point);
                    }else {
                        if (evel_type == "环境感受评价") {
                            feel_points.add(point);
                        }else{
                            showMsg("评价失败");
                        }
                    }
                }
                evel_type="";
                point = new Point();
                point_name.setText(null);
                type.setText(null);
                inform_point.setText("X:    Y:    ");
                point_name2.setText(null);
                type2.setText(null);
                inform_point2.setText("X:    Y:    ");
                temp.setText(null);
                CHILL.setText(null);
                HEAT_INDEX.setText(null);
                T_limit.setText(null);
                diqiu_temp.setText(null);
                shiqiu_temp.setText(null);
                loudian_temp.setText(null);
                xiangdui_wet.setText(null);
                pressure.setText(null);
                altitude.setText(null);
                density.setText(null);
                air_HCHO.setText(null);
                air_TVOC.setText(null);
                air_C6H6.setText(null);
                air_CO2.setText(null);
                air_PM25.setText(null);
                air_PM10.setText(null);
                light.setText(null);
                wind.setText(null);
                other.setText(null);
                beauty_factor.setText(null);
                safe_factor.setText(null);
                clean_factor.setText(null);
                alive_factor.setText(null);
                depress_factor.setText(null);
                boring_factor.setText(null);
                rich_factor.setText(null);
                timerTextView.setText(null);
                cameraPicture1.setImageBitmap(null);
                cameraPicture2.setImageBitmap(null);
                cameraPicture3.setImageBitmap(null);
                cameraPicture4.setImageBitmap(null);
                if (hot.getChildCount() > 0) {
                    // 获取第一个 RadioButton 并设置为选中
                    RadioButton firstRadioButton = (RadioButton) hot.getChildAt(0);
                    if (firstRadioButton != null) {
                        firstRadioButton.setChecked(true);
                    }
                }
                if (body.getChildCount() > 0) {
                    // 获取第一个 RadioButton 并设置为选中
                    RadioButton firstRadioButton = (RadioButton) body.getChildAt(0);
                    if (firstRadioButton != null) {
                        firstRadioButton.setChecked(true);
                    }
                }
                if (beauty.getChildCount() > 0) {
                    // 获取第一个 RadioButton 并设置为选中
                    RadioButton firstRadioButton = (RadioButton) beauty.getChildAt(0);
                    if (firstRadioButton != null) {
                        firstRadioButton.setChecked(true);
                    }
                }
                if (safe.getChildCount() > 0) {
                    // 获取第一个 RadioButton 并设置为选中
                    RadioButton firstRadioButton = (RadioButton) safe.getChildAt(0);
                    if (firstRadioButton != null) {
                        firstRadioButton.setChecked(true);
                    }
                }
                if (clean.getChildCount() > 0) {
                    // 获取第一个 RadioButton 并设置为选中
                    RadioButton firstRadioButton = (RadioButton) clean.getChildAt(0);
                    if (firstRadioButton != null) {
                        firstRadioButton.setChecked(true);
                    }
                }
                if (alive.getChildCount() > 0) {
                    // 获取第一个 RadioButton 并设置为选中
                    RadioButton firstRadioButton = (RadioButton) alive.getChildAt(0);
                    if (firstRadioButton != null) {
                        firstRadioButton.setChecked(true);
                    }
                }
                if (depress.getChildCount() > 0) {
                    // 获取第一个 RadioButton 并设置为选中
                    RadioButton firstRadioButton = (RadioButton) depress.getChildAt(0);
                    if (firstRadioButton != null) {
                        firstRadioButton.setChecked(true);
                    }
                }
                if (boring.getChildCount() > 0) {
                    // 获取第一个 RadioButton 并设置为选中
                    RadioButton firstRadioButton = (RadioButton) boring.getChildAt(0);
                    if (firstRadioButton != null) {
                        firstRadioButton.setChecked(true);
                    }
                }
                if (rich.getChildCount() > 0) {
                    // 获取第一个 RadioButton 并设置为选中
                    RadioButton firstRadioButton = (RadioButton) rich.getChildAt(0);
                    if (firstRadioButton != null) {
                        firstRadioButton.setChecked(true);
                    }
                }
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
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // 错误处理
                showMsg("error");
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.example.myapplication.fileprovider",
                        photoFile);
                // launch的输入参数是泛型，对应ActivityResultLauncher<Intent>
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 确保有相机应用可用
                if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    activityResultLauncher.launch(takePictureIntent);
                }
            }
        }
    }
    // 辅助方法：创建文件
    private File createImageFile() throws IOException {
        // 创建唯一文件名
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "JPEG_" + timeStamp + "_";
        //File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File storageDir = new File(getActivity().getFilesDir(),"Pictures");
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        File image = File.createTempFile(
                imageFileName,  // 前缀
                ".jpg",         // 后缀
                storageDir      // 目录
        );
        return image;
    }
    private void saveImage(Bitmap bitmap) {
        //在API29及之后是不需要申请的，默认是允许的
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        String image_name = imageFileName+".jpg";
        ByteArrayOutputStream baos= new ByteArrayOutputStream(size);
        try {
            //设置位图的压缩格式，质量为100%，并放入字节数组输出流中
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            //将字节数组输出流转化为字节数组byte[]
            byte[] imagedata = baos.toByteArray();
            point.add_image(imagedata,image_name,size);
            image_count = image_count + 1;
            showMsg("保存照片");
        }catch (Exception e){}
        finally {
            try {
                //bitmap.recycle();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), image_name);
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
        // 开始录音
        /* ①Initial：实例化MediaRecorder对象 */
        if (mMediaRecorder == null) {
            mMediaRecorder = new MediaRecorder();
        }
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
            //showMsg(getOutputPath());
            mMediaRecorder.prepare();
            /* ④开始 */
            mMediaRecorder.start();
            showMsg("开始录音，再次点击结束录音");
        } catch (IOException e) {
            Log.e("RecordingError", "Error starting recording: " + e.getMessage());
        }
    }

    public void stopRecording() {
        try {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
            FileInputStream fileInputStream = new FileInputStream(getOutputPath());
            byte[] bytesArray = new byte[(int) new java.io.File(getOutputPath()).length()];
            int bytesRead = fileInputStream.read(bytesArray);
            if (bytesRead != bytesArray.length) {
                throw new IOException("Could not read the entire file");
            }
            fileInputStream.close();
            String audio_name = audioFileName+".mp3";
            if(audio_count>=1){
                point.del_audio();
                audio_count=0;
            }
            point.add_audio(bytesArray, audio_name);
            showMsg("结束录音");
        } catch (RuntimeException e) {
            Log.e("RecordingError", "Error stopping recording: " + e.getMessage());
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            File file = new File(getOutputPath());
            if (file.exists())
                file.delete();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        audio_count = audio_count +1;
    }
    private String getOutputPath(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RECORDINGS), audioFileName+".mp3");
            return file.getAbsolutePath();
        }
        else{
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), audioFileName+".mp3");
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