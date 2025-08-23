package com.example.socialnetwork.ui.main.home;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.databinding.FragmentNewsCategoryBinding;
import com.example.socialnetwork.ui.main.details.ArticleDetailFragment;
import com.example.socialnetwork.ui.main.home.adapter.ArticlesPagerAdapter;
import com.example.socialnetwork.ui.main.home.model.DataSource;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;

import java.util.List;

public class NewsCategoryFragment extends Fragment {

    private FragmentNewsCategoryBinding binding;

    private static final String ARG_CATEGORY_INDEX = "category_index";

    public static NewsCategoryFragment newInstance(int index) {
        NewsCategoryFragment fragment = new NewsCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_INDEX, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNewsCategoryBinding.inflate(inflater, container, false);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        List<NewsArticle> articles = DataSource.getArticlesByCategory(
                getArguments().getInt(ARG_CATEGORY_INDEX)
        );
        // Adapter sẽ dùng binding cho item_news_article
        binding.recyclerView.setAdapter(new ArticlesPagerAdapter(articles, article -> {
            // Khi click bài viết → mở ArticleDetailFragment
            ArticleDetailFragment fragment = ArticleDetailFragment.newInstance(article);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(com.example.socialnetwork.R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        }));
        return binding.getRoot();
    }
}
