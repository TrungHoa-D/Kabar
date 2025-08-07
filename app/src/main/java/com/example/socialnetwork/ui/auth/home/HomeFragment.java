package com.example.socialnetwork.ui.auth.home;

import android.os.Bundle;

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

import com.example.socialnetwork.databinding.FragmentHomeBinding;
import com.example.socialnetwork.ui.auth.home.adapter.CategoryAdapter;
import com.example.socialnetwork.ui.auth.home.adapter.NewsAdapter;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private NewsAdapter newsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupRecyclerViews();
        observeState();

        viewModel.loadHomepageData();
    }

    private void setupRecyclerViews() {
        categoryAdapter = new CategoryAdapter();
        binding.rvCategories.setAdapter(categoryAdapter);

        newsAdapter = new NewsAdapter();
        binding.rvLatestNews.setAdapter(newsAdapter);
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);
            if (!state.isLoading) {
                if (state.categories != null) {
                    categoryAdapter.setCategories(state.categories);
                }
                if (state.articles != null) {
                    newsAdapter.setArticles(state.articles);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


