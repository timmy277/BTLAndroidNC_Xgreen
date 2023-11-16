package com.example.xgreen.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.example.xgreen.R;

public class SellFragment extends Fragment {
    private TabLayout tab_history;
    private ViewPager2 view_history;

    public SellFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sell, container, false);
//        tab_history = view.findViewById(R.id.tab_history);
//        view_history = view.findViewById(R.id.view_history);
//
//        ViewHistoryAdapter viewHistoryAdapter = new ViewHistoryAdapter(getActivity());
//        view_history.setAdapter(viewHistoryAdapter);
//
//        new TabLayoutMediator(tab_history, view_history,
//                (tab, position) -> {
//                    switch (position) {
//                        case 0:
//                            tab.setText("Đang diễn ra");
//                            break;
//                        case 1:
//                            tab.setText("Lịch sử yêu cầu");
//                            break;
//                    }
//                }).attach();

        return view;
    }
}