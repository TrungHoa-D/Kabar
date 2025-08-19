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
        list.add(new Topic("Health", "Latest health news...", R.drawable.health));
        list.add(new Topic("Technology", "Tech innovations...", R.drawable.technology));
        list.add(new Topic("Travel", "Explore destinations...", R.drawable.travel));

        list.add(new Topic("Art", "The Art Newspaper is the journal of record for...", R.drawable.art));

        list.add(new Topic("Politics", "opinion and analysis of American and global politi...", R.drawable.politics));

        list.add(new Topic("Sport", " Sports news and live sports coverage including scores..", R.drawable.sport));

        list.add(new Topic("Money", "The latest breaking financial news on the US and world...", R.drawable.money));

        topicsLiveData.setValue(list);
    }

    public LiveData<List<Topic>> getTopics() {
        return topicsLiveData;
    }
}
