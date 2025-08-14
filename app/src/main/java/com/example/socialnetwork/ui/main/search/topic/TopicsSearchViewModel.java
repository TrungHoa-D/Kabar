package com.example.socialnetwork.ui.main.search.topic;

import androidx.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialnetwork.R;

import java.util.ArrayList;
import java.util.List;

public class TopicsSearchViewModel extends ViewModel {

    private final MutableLiveData<List<Topic>> topicsLiveData = new MutableLiveData<>();

    public TopicsSearchViewModel() {
        loadTopics();
    }

    private void loadTopics() {
        // Fake data, có thể thay bằng API
        List<Topic> list = new ArrayList<>();
        list.add(new Topic("Health", "Latest health news...", R.drawable.image_placeholder));
        list.add(new Topic("Technology", "Tech innovations...", R.drawable.image_placeholder));
        list.add(new Topic("Travel", "Explore destinations...", R.drawable.image_placeholder));
        topicsLiveData.setValue(list);
    }

    public LiveData<List<Topic>> getTopics() {
        return topicsLiveData;
    }
}
