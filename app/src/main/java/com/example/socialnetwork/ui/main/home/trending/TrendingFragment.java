package com.example.socialnetwork.ui.main.home.trending;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.databinding.FragmentTrendingBinding;
import com.example.socialnetwork.ui.main.home.adapter.ArticlesPagerAdapter;

public class TrendingFragment extends Fragment implements ArticlesPagerAdapter.OnPostClickListener {

    private FragmentTrendingBinding binding;
    private TrendingViewModel viewModel;
    private ArticlesPagerAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTrendingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(TrendingViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeState();

        viewModel.loadTrendingArticles();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
    }

    private void setupRecyclerView() {
        binding.recyclerViewTrending.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ArticlesPagerAdapter();

        adapter.setOnPostClickListener(this);

        binding.recyclerViewTrending.setAdapter(adapter);
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {

            if (state.error != null) {
                Toast.makeText(getContext(), state.error, Toast.LENGTH_SHORT).show();
            }

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
        NavDirections action = TrendingFragmentDirections.actionTrendingFragmentToDetailFragment(postId);
        NavHostFragment.findNavController(this).navigate(action);
    }
}