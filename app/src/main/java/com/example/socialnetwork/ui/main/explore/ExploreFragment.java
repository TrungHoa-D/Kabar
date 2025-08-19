package com.example.socialnetwork.ui.main.explore;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialnetwork.R;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.socialnetwork.databinding.FragmentExploreBinding;
import com.example.socialnetwork.ui.main.search.topic.TopicAdapter;

public class ExploreFragment extends Fragment {

    private FragmentExploreBinding binding;
    private ExploreViewModel viewModel;
    private TopicAdapter topicAdapter;
    private PopularTopicAdapter popularTopicAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentExploreBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(ExploreViewModel.class);

        // Thiết lập RecyclerView cho phần Topic
        binding.rvTopics.setLayoutManager(new LinearLayoutManager(getContext()));
        topicAdapter = new TopicAdapter();
        binding.rvTopics.setAdapter(topicAdapter);

        // Thiết lập RecyclerView cho phần Popular Topic
        binding.rvPopularTopics.setLayoutManager(new LinearLayoutManager(getContext()));
        popularTopicAdapter = new PopularTopicAdapter();
        binding.rvPopularTopics.setAdapter(popularTopicAdapter);

        // Quan sát dữ liệu từ ViewModel
        viewModel.getTopics().observe(getViewLifecycleOwner(), topics -> {
            topicAdapter.setTopics(topics);
        });

        viewModel.getPopularTopics().observe(getViewLifecycleOwner(), popularTopics -> {
            popularTopicAdapter.setArticleList(popularTopics);
        });

        // Tải dữ liệu ban đầu
        viewModel.loadExploreData();
    }




}