package com.example.socialnetwork.ui.main.explore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.databinding.FragmentExploreBinding;
import com.example.socialnetwork.ui.main.search.topic.TopicAdapter;

public class ExploreFragment extends Fragment implements PopularTopicAdapter.OnPostClickListener {

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

        setupRecyclerViews();
        observeState();
        viewModel.loadExploreData();
    }

    private void setupRecyclerViews() {
        binding.rvTopics.setLayoutManager(new LinearLayoutManager(getContext()));
        topicAdapter = new TopicAdapter();
        binding.rvTopics.setAdapter(topicAdapter);

        binding.rvPopularTopics.setLayoutManager(new LinearLayoutManager(getContext()));
        popularTopicAdapter = new PopularTopicAdapter();
        popularTopicAdapter.setOnPostClickListener(this);
        binding.rvPopularTopics.setAdapter(popularTopicAdapter);
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state.topics != null) {
                topicAdapter.submitList(state.topics);
            }
            if (state.popularPosts != null) {
                popularTopicAdapter.submitList(state.popularPosts);
            }
        });
    }

    @Override
    public void onPostClick(long postId) {
        NavDirections action = ExploreFragmentDirections.actionExploreFragmentToDetailFragment(postId);
        NavHostFragment.findNavController(this).navigate(action);
    }
}