package com.example.myapplication.ui.notifications;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.R;
import com.example.myapplication.ui.login_register.RegisterActivity;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        // 使用自定义的ViewModelProvider.Factory
        notificationsViewModel = new ViewModelProvider(this, new NotificationsViewModelFactory(requireContext())).get(NotificationsViewModel.class);

        final ImageView avatar = root.findViewById(R.id.avatar);
        final TextView username = root.findViewById(R.id.username); // 新增用户名TextView
        final LinearLayout functionalityButtons = root.findViewById(R.id.functionality_buttons);

        notificationsViewModel.getIsLoggedIn().observe(getViewLifecycleOwner(), isLoggedIn -> {
            isLoggedIn = true; // 这里暂时以登录的状态进行显示
            if (isLoggedIn) {
                notificationsViewModel.getUserAvatarPath().observe(getViewLifecycleOwner(), avatarPath -> {
                    if (avatarPath != null) {
                        Bitmap bitmap = BitmapFactory.decodeFile(avatarPath);
                        avatar.setImageBitmap(bitmap); // 设置用户头像
                    } else {
                        avatar.setImageResource(R.drawable.ic_avatar_placeholder); // 设置占位符头像
                    }
                });
                notificationsViewModel.getUserName().observe(getViewLifecycleOwner(), userName -> {
                    username.setText(userName); // 设置用户名
                });
                functionalityButtons.setVisibility(View.VISIBLE);
            } else {
                avatar.setImageResource(R.drawable.ic_avatar_placeholder); // 设置占位符头像
                username.setText("点击登录"); // 设置默认用户名
                functionalityButtons.setVisibility(View.GONE);
            }
        });

        // 为“点击登录”文本设置点击事件
        username.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RegisterActivity.class);
            startActivity(intent);
        });

        return root;
    }
}
