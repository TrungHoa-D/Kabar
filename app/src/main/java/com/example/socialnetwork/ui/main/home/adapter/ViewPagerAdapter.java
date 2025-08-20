package com.example.socialnetwork.ui.main.home.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.socialnetwork.data.model.dto.TopicDto;
import com.example.socialnetwork.ui.main.home.NewsCategoryFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStateAdapter {

    private final List<TopicDto> topics;

    public ViewPagerAdapter(@NonNull Fragment fragment, List<TopicDto> topics) {
        super(fragment);
        this.topics = topics;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            // Vị trí đầu tiên là tab "All"
            return NewsCategoryFragment.newInstanceForAll();
        } else {
            // Các vị trí sau là cho từng topic
            long topicId = topics.get(position - 1).getId();
            return NewsCategoryFragment.newInstanceForTopic(topicId);
        }
    }

    @Override
    public int getItemCount() {
        // +1 cho tab "All"
        return (topics != null) ? topics.size() + 1 : 0;
    }
}
