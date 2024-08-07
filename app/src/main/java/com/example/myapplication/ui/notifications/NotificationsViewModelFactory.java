//自定义的vm初始化类，用于传入context以访问缓存
package com.example.myapplication.ui.notifications;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NotificationsViewModelFactory implements ViewModelProvider.Factory {
    private final Context mContext;

    public NotificationsViewModelFactory(Context context) {
        mContext = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NotificationsViewModel.class)) {
            return (T) new NotificationsViewModel(mContext);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
