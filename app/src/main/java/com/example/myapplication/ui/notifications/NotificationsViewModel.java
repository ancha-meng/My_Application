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
    private static final String PREFS_NAME = "UserPrefs";
    private static final String USER_ID_KEY = "UserID";
    private static final String USER_AVATAR_KEY = "UserAvatar";

    public NotificationsViewModel(Context context) {
        mText = new MutableLiveData<>();
        isLoggedIn = new MutableLiveData<>();
        userAvatarPath = new MutableLiveData<>();
        mText.setValue("这是通知片段");

        // 检查登录状态
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String userId = prefs.getString(USER_ID_KEY, null);
        isLoggedIn.setValue(userId != null);

        // 获取用户头像路径
        String avatarPath = prefs.getString(USER_AVATAR_KEY, null);
        userAvatarPath.setValue(avatarPath);
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
}
