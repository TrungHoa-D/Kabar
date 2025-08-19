package com.example.socialnetwork.ui.main.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.socialnetwork.ui.main.home.NewsCategoryFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private int tabCount;

    public ViewPagerAdapter(@NonNull Fragment fragment, int tabCount) {
        super(fragment);
        this.tabCount = tabCount;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return NewsCategoryFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return tabCount;
    }
}
