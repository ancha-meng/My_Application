package com.example.myapplication.ui.notifications;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<Boolean> isLoggedIn;
    private final MutableLiveData<String> userAvatarPath;
    private final MutableLiveData<String> userName; // 新增用户名LiveData
    private static final String PREFS_NAME = "UserPrefs";
    private static final String USER_ID_KEY = "UserID";
    private static final String USER_AVATAR_KEY = "UserAvatar";
    private static final String USER_NAME_KEY = "UserName"; // 新增用户名Key

    public NotificationsViewModel(Context context) {
        mText = new MutableLiveData<>();
        isLoggedIn = new MutableLiveData<>();
        userAvatarPath = new MutableLiveData<>();
        userName = new MutableLiveData<>(); // 初始化用户名LiveData
        mText.setValue("这是通知片段");

        // 获取缓存对象
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        //获取缓存中的UserID
        String userId = prefs.getString(USER_ID_KEY, null);
        //根据UserID判断登录状态
        isLoggedIn.setValue(userId != null);

        //________此段应改为通过ID的数据库查询交互，缓存中只存ID__________
        // 获取缓存中的用户头像路径
        String avatarPath = prefs.getString(USER_AVATAR_KEY, null);
        userAvatarPath.setValue(avatarPath);
        // 获取用户名
        String name = prefs.getString(USER_NAME_KEY, "点击登录");
        userName.setValue(name);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Boolean> getIsLoggedIn() {
        return isLoggedIn;
    }

    public LiveData<String> getUserAvatarPath() {
        return userAvatarPath;
    }

    public LiveData<String> getUserName() {
        return userName;
    }
}
