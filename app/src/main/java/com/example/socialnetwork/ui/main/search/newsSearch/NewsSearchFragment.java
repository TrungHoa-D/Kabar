package com.example.socialnetwork.ui.main.search.newsSearch;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialnetwork.R;
import com.example.socialnetwork.ui.main.search.SearchViewModel;

public class NewsSearchFragment extends Fragment {

    private NewsSearchViewModel newsViewModel;
    private SearchViewModel sharedViewModel;
    private RecyclerView rvNewsArticles;
    private NewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_search, container, false);
        rvNewsArticles = view.findViewById(R.id.rv_news_articles);
        rvNewsArticles.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter();
        rvNewsArticles.setAdapter(adapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        newsViewModel = new ViewModelProvider(this).get(NewsSearchViewModel.class);

        sharedViewModel = new ViewModelProvider(requireParentFragment()).get(SearchViewModel.class);

        newsViewModel.getFilteredPosts().observe(getViewLifecycleOwner(), posts -> {
            adapter.submitList(posts);
        });

        sharedViewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            newsViewModel.filter(query);
        });
    }
}