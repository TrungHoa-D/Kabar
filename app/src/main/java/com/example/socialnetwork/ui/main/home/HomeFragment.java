package com.example.socialnetwork.ui.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.model.dto.TopicDto;
import com.example.socialnetwork.databinding.FragmentHomeBinding;
import com.example.socialnetwork.ui.main.home.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void setupRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            viewModel.loadHomepageData();
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupClickListeners();
        setupRefreshLayout();
        observeState();

        viewModel.loadHomepageData();
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            // binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);

            if (!state.isLoading) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            if (state.topics != null && !state.topics.isEmpty()) {
                // Chỉ setup ViewPager một lần để tránh tạo lại không cần thiết
                if (binding.articlesViewPager.getAdapter() == null) {
                    setupViewPagerWithTopics(state.topics);
                }
            }

            if (state.trendingArticles != null && !state.trendingArticles.isEmpty()) {
                // Lấy bài viết đầu tiên trong danh sách trending
                PostDto topTrendingArticle = state.trendingArticles.get(0);

                // Hiển thị card và cập nhật dữ liệu
                binding.trendingCard.setVisibility(View.VISIBLE);
                binding.trendingArticleLayout.articleCategory.setText(topTrendingArticle.getTopic().getName());
                binding.trendingArticleLayout.articleTitle.setText(topTrendingArticle.getTitle());
                binding.trendingArticleLayout.articleSourceName.setText(topTrendingArticle.getAuthor().getFullName());

                // Load ảnh bằng Glide
                Glide.with(this)
                        .load(topTrendingArticle.getCoverImageUrl())
                        .placeholder(R.drawable.image_placeholder)
                        .into(binding.trendingArticleLayout.articleImage);

                Glide.with(this)
                        .load(topTrendingArticle.getAuthor().getAvatarUrl())
                        .placeholder(R.drawable.image_placeholder)
                        .into(binding.trendingArticleLayout.articleSourceLogo);
            }
        });
    }

    private void setupViewPagerWithTopics(List<TopicDto> topics) {
        List<String> tabTitles = new ArrayList<>();
        tabTitles.add("All");
        for (TopicDto topic : topics) {
            tabTitles.add(topic.getName());
        }

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, topics);
        binding.articlesViewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.articlesViewPager,
                (tab, position) -> tab.setText(tabTitles.get(position))
        ).attach();
    }

    private void setupClickListeners() {
        binding.ivNotification.setOnClickListener(v ->
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_notificationFragment)
        );

        binding.seeAll.setOnClickListener(v ->
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_trendingFragment)
        );

        binding.searchBarLayout.setOnClickListener(v ->
                NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_searchFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}