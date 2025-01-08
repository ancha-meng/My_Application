package com.example.myapplication.ui.dashboard;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentDashboardBinding;

import java.util.ArrayList;

public class SubListFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private RecyclerView recyclerView;
    private PointsAdapter pointsAdapter;
    private String user_name;
    private String project_name;
    private Application application;

    public SubListFragment(@NonNull Application application, String user_name, String project_name){
        this.user_name = user_name;
        this.project_name = project_name;
        this.application = application;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        SubListViewModel subListViewModel=
//                new ViewModelProvider(this).get(SubListViewModel.class);
        SubListViewModel subListViewModel = new SubListViewModel(application,user_name,project_name);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        recyclerView = binding.recyclerView;

        pointsAdapter=new PointsAdapter();
        recyclerView.setAdapter(pointsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        subListViewModel.getPointList().observe(getViewLifecycleOwner(),pointItems -> {
            pointsAdapter.setPointList(pointItems);
            pointsAdapter.notifyDataSetChanged();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    private void showMsg(String msg){
        Toast.makeText(application,msg,Toast.LENGTH_SHORT).show();
    }
}
