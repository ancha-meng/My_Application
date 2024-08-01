package com.example.myapplication.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

import java.util.ArrayList;

public class SubListFragment extends Fragment {

    private RecyclerView recyclerView;
//    private SubListAdapter subListAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_list_layout, container, false);

//        // 绑定RecyclerView
//        recyclerView = view.findViewById(R.id.recycler_view);
//        subListAdapter = new SubListAdapter();
//        recyclerView.setAdapter(subListAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // 模拟数据
//        List<SubListItem> subListItems = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            subListItems.add(new SubListItem("Sub Task " + (i + 1), new Date()));
//        }
//        subListAdapter.setSubListItems(subListItems);
//
        return view;
    }
}
