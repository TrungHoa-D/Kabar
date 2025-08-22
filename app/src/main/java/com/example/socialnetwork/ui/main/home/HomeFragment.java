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
    private HomeViewModel homeViewModel;
    private long trendingPostId = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupClickListeners();
        setupRefreshLayout();
        observeState();
        homeViewModel.loadHomepageData();
    }

    private void setupRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            homeViewModel.loadHomepageData();
        });
    }

    private void observeState() {
        homeViewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (!state.isLoading) {
                binding.swipeRefreshLayout.setRefreshing(false);
            }

            if (state.topics != null && !state.topics.isEmpty()) {
                if (binding.articlesViewPager.getAdapter() == null) {
                    setupViewPagerWithTopics(state.topics);
                }
            }

            if (state.trendingArticles != null && !state.trendingArticles.isEmpty()) {
                PostDto topTrendingArticle = state.trendingArticles.get(0);

                this.trendingPostId = topTrendingArticle.getId();

                binding.trendingCard.setVisibility(View.VISIBLE);
                binding.trendingArticleLayout.articleCategory.setText(topTrendingArticle.getTopic().getName());
                binding.trendingArticleLayout.articleTitle.setText(topTrendingArticle.getTitle());
                binding.trendingArticleLayout.articleSourceName.setText(topTrendingArticle.getAuthor().getFullName());
                Glide.with(this).load(topTrendingArticle.getCoverImageUrl()).into(binding.trendingArticleLayout.articleImage);
                Glide.with(this).load(topTrendingArticle.getAuthor().getAvatarUrl()).into(binding.trendingArticleLayout.articleSourceLogo);
            } else {
                binding.trendingCard.setVisibility(View.GONE);
                this.trendingPostId = -1;
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
        binding.trendingCard.setOnClickListener(v -> {
            if (trendingPostId != -1) {
                NavDirections action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(trendingPostId);
                NavHostFragment.findNavController(HomeFragment.this).navigate(action);
            }
        });

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