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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.FragmentHomeBinding;
import com.example.socialnetwork.ui.main.home.adapter.ArticlesPagerAdapter;
import com.example.socialnetwork.ui.main.home.adapter.ViewPagerAdapter;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;
import com.example.socialnetwork.ui.main.search.SearchFragment;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;
    private ViewPager2 articlesViewPager;
    private final String[] tabTitles = {
            "All", "Sport", "Politic", "Business", "Health", "Travel", "Science", "Fashion"
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, tabTitles.length);
        binding.articlesViewPager.setAdapter(viewPagerAdapter);

        new TabLayoutMediator(binding.tabLayout, binding.articlesViewPager,
                (tab, position) -> tab.setText(tabTitles[position])
        ).attach();

        List<NewsArticle> mockArticles = new ArrayList<>();
        mockArticles.add(
                new NewsArticle(
                        "Europe", // category
                        "Ukraine's President Zelensky to BBC: Blood money being paid...",
                        R.drawable.sample_zelensky,   // imageResId
                        R.drawable.bbc_news,          // sourceLogoResId
                        "BBC News",                   // sourceName
                        "14m ago"                     // time
                )
        );

        mockArticles.add(
                new NewsArticle(
                        "Technology",
                        "The Future of Artificial Intelligence",
                        R.drawable.image_placeholder,
                        R.drawable.bbc_news,
                        "Wired",
                        "2h ago"
                )
        );

        mockArticles.add(
                new NewsArticle(
                        "Health",
                        "Healthy breakfast for a productive day",
                        R.drawable.image_placeholder,
                        R.drawable.bbc_news,
                        "Healthline",
                        "1d ago"
                )
        );

        // Gắn sự kiện click vào thanh tìm kiếm
        binding.searchBarLayout.setOnClickListener(v -> {
            NavController navController =
                    Navigation.findNavController(requireActivity(), R.id.fragment_container);
            navController.navigate(R.id.searchFragment);
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        setupRecyclerViews();
        observeState();
        setupClickListeners();
        viewModel.loadHomepageData();
    }

    private void setupRecyclerViews() {

    }

    private void observeState() {

    }

    private void setupClickListeners() {
        binding.ivNotification.setOnClickListener(v -> {
            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_notificationFragment);
        });
        binding.seeAll.setOnClickListener(v->{
            NavHostFragment.findNavController(HomeFragment.this).navigate(R.id.action_homeFragment_to_trendingFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}


