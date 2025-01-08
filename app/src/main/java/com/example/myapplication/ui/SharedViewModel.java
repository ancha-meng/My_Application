package com.example.myapplication.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private MutableLiveData<String> userName;

    public MutableLiveData<String> getSomeData() {
        if (userName == null) {
            userName = new MutableLiveData<>();
        }
        return userName;
    }
}
