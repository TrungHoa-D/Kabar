package com.example.socialnetwork.ui.main.search.topic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.R;
import com.example.socialnetwork.ui.main.search.SearchViewModel;

public class TopicsSearchFragment extends Fragment {

    private TopicsSearchViewModel topicsViewModel;
    private SearchViewModel sharedViewModel;
    private TopicAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_topics_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvTopics = view.findViewById(R.id.rv_topics);
        rvTopics.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TopicAdapter();
        rvTopics.setAdapter(adapter);

        topicsViewModel = new ViewModelProvider(this).get(TopicsSearchViewModel.class);

        sharedViewModel = new ViewModelProvider(requireParentFragment()).get(SearchViewModel.class);

        topicsViewModel.getFilteredTopics().observe(getViewLifecycleOwner(), topics -> {
            adapter.submitList(topics);
        });

        sharedViewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            topicsViewModel.filter(query);
        });
    }
}