package com.example.myapplication.ui.notifications;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.myapplication.R;
import com.example.myapplication.database.AuthManager;
import com.example.myapplication.ui.SharedViewModel;
import com.example.myapplication.ui.login_register.LoginActivity;
import com.example.myapplication.ui.login_register.RegisterActivity;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private String user_name = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        final ImageView avatar = root.findViewById(R.id.avatar);
        final TextView username = root.findViewById(R.id.username); // 新增用户名TextView
        final Button exit = root.findViewById(R.id.logout_button);
        final Button feedback = root.findViewById(R.id.feedback_button);
        final Button switch_account = root.findViewById(R.id.switch_account_button);
        final LinearLayout functionalityButtons = root.findViewById(R.id.functionality_buttons);
//        // 获取传递过来的Bundle
//        Bundle bundle = getArguments();
//        if (bundle.getString("name") != null) {
//            String someData = bundle.getString("name");
//            user_name = someData;
//            //showMsg(someData);
//            if(someData!=null) {
//                username.setText(someData);
//            }else{
//                username.setText("点击登录");
//            }
//            // 使用传递过来的数据
//        }
        if(AuthManager.getInstance().isLoggedIn()){
            user_name = AuthManager.getInstance().getUsername();
            username.setText(user_name);
        }
        else{
            username.setText("点击登录");
        }
//        // 使用自定义的ViewModelProvider.Factory
//        notificationsViewModel = new ViewModelProvider(this, new NotificationsViewModelFactory(requireContext())).get(NotificationsViewModel.class);
//

//
//        notificationsViewModel.getIsLoggedIn().observe(getViewLifecycleOwner(), isLoggedIn -> {
//            isLoggedIn = true; // 这里暂时以登录的状态进行显示
//            if (isLoggedIn) {
//                notificationsViewModel.getUserAvatarPath().observe(getViewLifecycleOwner(), avatarPath -> {
//                    if (avatarPath != null) {
//                        Bitmap bitmap = BitmapFactory.decodeFile(avatarPath);
//                        avatar.setImageBitmap(bitmap); // 设置用户头像
//                    } else {
//                        avatar.setImageResource(R.drawable.ic_avatar_placeholder); // 设置占位符头像
//                    }
//                });
//                notificationsViewModel.getUserName().observe(getViewLifecycleOwner(), userName -> {
//                    username.setText(userName); // 设置用户名
//                });
//                functionalityButtons.setVisibility(View.VISIBLE);
//            } else {
//                avatar.setImageResource(R.drawable.ic_avatar_placeholder); // 设置占位符头像
//                username.setText("点击登录"); // 设置默认用户名
//                functionalityButtons.setVisibility(View.GONE);
//            }
//        });

        // 为“点击登录”文本设置点击事件
        username.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        });
        exit.setOnClickListener(view -> {
            AlertDialog.Builder builder= new AlertDialog.Builder(getActivity())
                    .setTitle("提示").setMessage("确定要退出登录吗？")
                    .setPositiveButton("确定", (dialog, which) -> {
                        AuthManager.getInstance().setLoggedIn(false);
                        AuthManager.getInstance().setUsername(null);
                        username.setText("点击登录");
                        dialog.dismiss();
                    })
                    .setNegativeButton("取消", (dialog, which) -> dialog.dismiss());
            builder.show();

        });
        feedback.setOnClickListener(view -> {
            showMsg("功能待开发");
        });
        switch_account.setOnClickListener(view -> {
            showMsg("功能待开发");
        });

        return root;
    }
    private void showMsg(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_SHORT).show();
    }
}
