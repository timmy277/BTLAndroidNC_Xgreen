package com.example.xgreen.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.xgreen.Fragment.HistoryFragment;
import com.example.xgreen.Fragment.PendingFragment;

public class ViewHistoryAdapter extends FragmentStateAdapter {
    public ViewHistoryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new PendingFragment();
            case 1:
                return new HistoryFragment();
            default:
                return new PendingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
