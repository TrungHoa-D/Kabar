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
import com.example.socialnetwork.ui.main.home.model.NewsArticle;

import java.util.ArrayList;
import java.util.List;

public class NewsSearchFragment extends Fragment {

    private NewsSearchViewModel mViewModel;
    private RecyclerView rvNewsArticles;

    public static NewsSearchFragment newInstance() {
        return new NewsSearchFragment();
    }

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

        // Dữ liệu mẫu
        List<NewsArticle> articles = new ArrayList<>();
        articles.add(new NewsArticle(
                "Europe",
                "Ukraine's President Zelensky to BBC: Blood money being paid...",
                R.drawable.sample_zelensky,
                R.drawable.bbc_news,
                "BBC News",
                "• 14m ago"
        ));
        articles.add(new NewsArticle(
                "Tech",
                "Google announces new AI breakthrough in language models",
                R.drawable.image_placeholder,
                R.drawable.bbc_news,
                "TechCrunch",
                "• 1h ago"
        ));

        NewsAdapter adapter = new NewsAdapter(articles);
        rvNewsArticles.setAdapter(adapter);
    }
}
