package com.example.socialnetwork.ui.main.home.trending;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import com.example.socialnetwork.databinding.FragmentTrendingBinding; // Thay bằng package của bạn

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
        viewModel = new ViewModelProvider(this).get(TrendingViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeViewModel();
    }

    private void setupToolbar() {
        // Xử lý sự kiện nhấn nút back
        binding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
        // Xử lý sự kiện cho context menu (nút 3 chấm)
        binding.toolbar.setOnMenuItemClickListener(item -> {
            // if (item.getItemId() == R.id.menu_refresh) { ... }
            return true;
        });
    }

    private void setupRecyclerView() {
        adapter = new TrendingAdapter();
        binding.trendingRecyclerView.setAdapter(adapter);
    }

    private void observeViewModel() {
        viewModel.getTrendingArticles().observe(getViewLifecycleOwner(), articles -> {
            adapter.submitList(articles);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
