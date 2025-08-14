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

public class NewsSearchFragment extends Fragment {

    private NewsSearchViewModel mViewModel;

    public static NewsSearchFragment newInstance() {
        return new NewsSearchFragment();
    }

    private RecyclerView rvNewsArticles;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_search, container, false);
        rvNewsArticles = view.findViewById(R.id.rv_news_articles);
        rvNewsArticles.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NewsSearchViewModel.class);
        // TODO: Use the ViewModel
    }

}