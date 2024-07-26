package com.example.myapplication.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;

public class InputName_Dialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        final EditText editText = new EditText(getActivity());
        builder.setView(editText)
                .setTitle("输入任务名称")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 在这里处理输入的内容
                        String input = editText.getText().toString();
                        onConfirm(input);
                    }
                })
                .setNegativeButton("取消", null);

        return builder.create();
    }

    private OnInputConfirmedListener listener;
    public interface OnInputConfirmedListener {
        void onInputConfirmed(String inputText);
    }
    public void setOnInputConfirmedListener(OnInputConfirmedListener listener) {
        this.listener = listener;
    }
    // 在确定操作时调用
    public void onConfirm(String inputText) {
        if (listener!= null) {
            listener.onInputConfirmed(inputText);
        }
    }

}
