package com.example.socialnetwork.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.FragmentHomeBinding;
import com.example.socialnetwork.ui.main.home.adapter.CategoryAdapter;
import com.example.socialnetwork.ui.main.home.adapter.NewsAdapter;
import com.example.socialnetwork.ui.main.search.SearchFragment;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private CategoryAdapter categoryAdapter;
    private NewsAdapter newsAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // Gắn sự kiện click vào thanh tìm kiếm
        binding.searchBarLayout.setOnClickListener(v -> {
            // Chuyển sang SearchFragment
            FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new SearchFragment()); // R.id.fragment_container là ID của FrameLayout chứa Fragment
            fragmentTransaction.addToBackStack(null); // Thêm vào back stack để người dùng có thể quay lại
            fragmentTransaction.commit();
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupRecyclerViews(); // Gọi phương thức đã được sửa lỗi
        observeState();
        setupClickListeners();
        viewModel.loadHomepageData();
    }

    private void setupRecyclerViews() {
        // --- SỬA LỖI Ở ĐÂY ---

        // 1. Thiết lập cho Category RecyclerView
        categoryAdapter = new CategoryAdapter();
        // Thêm LayoutManager để sắp xếp item theo chiều ngang
        binding.categoriesRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.categoriesRecyclerView.setAdapter(categoryAdapter);

        // 2. Thiết lập cho News RecyclerView
        newsAdapter = new NewsAdapter();
        // Thêm LayoutManager để sắp xếp item theo chiều dọc
        binding.latestRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.latestRecyclerView.setAdapter(newsAdapter);
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
//             binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE); // Tạm thời comment lại nếu chưa có progressBar
            if (!state.isLoading) {
                if (state.categories != null) {
                    categoryAdapter.submitList(state.categories);
                }
                if (state.articles != null) {
                    newsAdapter.submitList(state.articles);
                }
            }
        });
    }

    private void setupClickListeners() {
        binding.ivNotification.setOnClickListener(v -> {
            // Sử dụng action đã định nghĩa trong nav graph để điều hướng
            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment2_to_notificationFragment);
        });
        binding.tvTrending.setOnClickListener(v->{
            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment2_to_trendingFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


