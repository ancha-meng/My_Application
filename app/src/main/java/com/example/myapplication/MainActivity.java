package com.example.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.database.DBOpenHelper;
import com.example.myapplication.ui.SharedViewModel;
import com.example.myapplication.ui.login_register.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    //请求权限码
    private static final int REQUEST_PERMISSIONS = 100;//9527;

    //缓存值。用于表明登录状态
    private String user_name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        checkingAndroidVersion();

//        Intent intent=getIntent();
//
//        Bundle bundle = new Bundle();
//        bundle.putString("name", user_name);
//        if (intent.getStringExtra("name") != null) {
//            String someData = intent.getStringExtra("name");
//            user_name=someData;
//            bundle.putString("name", someData);
//            navController.navigate(R.id.navigation_notifications, bundle);
//        }
//        //导航项选择监听器
//        navView.setOnNavigationItemSelectedListener(item -> {
//            if (item.getItemId() == R.id.navigation_notifications) {
//                navController.navigate(R.id.navigation_notifications, bundle);
//            }
//            if (item.getItemId() == R.id.navigation_home) {
//                navController.navigate(R.id.navigation_home, bundle);
//            }
//            if (item.getItemId() == R.id.navigation_dashboard) {
//                navController.navigate(R.id.navigation_dashboard, bundle);
//            }
//            return NavigationUI.onNavDestinationSelected(item, navController);
//        });
    }
    /**
     * 检查Android版本
     */
    private void checkingAndroidVersion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            //Android6.0及以上先获取权限再定位
            requestPermission();
        }
    }
    /**
     * 动态请求权限
     */
    @AfterPermissionGranted(REQUEST_PERMISSIONS)
    private void requestPermission() {
        String[] permissions = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                // 添加拍照权限
                Manifest.permission.CAMERA,
                // 添加录音权限
                Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (EasyPermissions.hasPermissions(this, permissions)) {
            //true 有权限 开始定位
            //showMsg("已获得权限，可以定位啦！");
        } else {
            //false 无权限
            //showMsg("无权限");
            EasyPermissions.requestPermissions(this, "需要权限", REQUEST_PERMISSIONS, permissions);
        }
    }
    /**
     * 请求权限结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //设置权限请求结果
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    /**
     * Toast提示
     * @param msg 提示内容
     */
    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder= new AlertDialog.Builder(this)
                .setTitle("提示").setMessage("确定要退出程序吗？")
                .setPositiveButton("确定", (dialog, which) -> {
                    finish();
                    dialog.dismiss();
                })
                .setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}