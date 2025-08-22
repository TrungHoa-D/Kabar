package com.example.socialnetwork.ui.main.home;

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

import com.example.socialnetwork.databinding.FragmentNewsCategoryBinding;
import com.example.socialnetwork.ui.main.home.adapter.ArticlesPagerAdapter;

public class NewsCategoryFragment extends Fragment implements ArticlesPagerAdapter.OnPostClickListener {

    private FragmentNewsCategoryBinding binding;
    private NewsCategoryViewModel viewModel;
    private ArticlesPagerAdapter adapter;
    private static final String ARG_TOPIC_ID = "topic_id";
    private static final long ALL_TOPICS_ID = -1L;

    public static NewsCategoryFragment newInstanceForAll() {
        NewsCategoryFragment fragment = new NewsCategoryFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TOPIC_ID, ALL_TOPICS_ID);
        fragment.setArguments(args);
        return fragment;
    }

    public static NewsCategoryFragment newInstanceForTopic(long topicId) {
        NewsCategoryFragment fragment = new NewsCategoryFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_TOPIC_ID, topicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentNewsCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(NewsCategoryViewModel.class);

        setupRecyclerView();
        observeState();

        if (getArguments() != null) {
            long topicId = getArguments().getLong(ARG_TOPIC_ID);
            viewModel.loadArticles(topicId);
        }
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ArticlesPagerAdapter();
        adapter.setOnPostClickListener(this);
        binding.recyclerView.setAdapter(adapter);
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state.articles != null) {
                adapter.submitList(state.articles);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPostClick(long postId) {
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(postId);
        NavHostFragment.findNavController(getParentFragment()).navigate(action);
    }
}