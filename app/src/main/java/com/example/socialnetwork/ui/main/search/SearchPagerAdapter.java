package com.example.socialnetwork.ui.main.search;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.socialnetwork.ui.main.search.author.AuthorSearchFragment;
import com.example.socialnetwork.ui.main.search.newsSearch.NewsSearchFragment;
import com.example.socialnetwork.ui.main.search.topic.TopicsSearchFragment;

public class SearchPagerAdapter extends FragmentStateAdapter {

    public SearchPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new NewsSearchFragment();
            case 1:
                return new TopicsSearchFragment();
            case 2:
                return new AuthorSearchFragment();
            default:
                return new NewsSearchFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}