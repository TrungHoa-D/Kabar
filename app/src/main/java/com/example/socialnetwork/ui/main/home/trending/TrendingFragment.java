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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.databinding.FragmentTrendingBinding;

public class TrendingFragment extends Fragment {

    private FragmentTrendingBinding binding;
    private TrendingViewModel viewModel;
    private TrendingAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTrendingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(TrendingViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeViewModel();

        viewModel.loadTrendingArticles();
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
    }

    private void setupRecyclerView() {
        adapter = new TrendingAdapter();
        binding.trendingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.trendingRecyclerView.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            // binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);

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
}