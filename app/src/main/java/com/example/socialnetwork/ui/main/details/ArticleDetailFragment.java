package com.example.socialnetwork.ui.main.details;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialnetwork.R;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.socialnetwork.databinding.FragmentArticleDetailBinding;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;

public class ArticleDetailFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";
    private static final String ARG_TITLE = "title";
    private static final String ARG_IMAGE = "image";
    private static final String ARG_SOURCE_LOGO = "source_logo";
    private static final String ARG_SOURCE_NAME = "source_name";
    private static final String ARG_TIME = "time";

    private FragmentArticleDetailBinding binding;

    public static ArticleDetailFragment newInstance(NewsArticle article) {
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, article.getCategory());
        args.putString(ARG_TITLE, article.getTitle());
        args.putInt(ARG_IMAGE, article.getImageResId());
        args.putInt(ARG_SOURCE_LOGO, article.getSourceLogoResId());
        args.putString(ARG_SOURCE_NAME, article.getSourceName());
        args.putString(ARG_TIME, article.getTime());

        ArticleDetailFragment fragment = new ArticleDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentArticleDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            binding.tvArticleCategory.setText(getArguments().getString(ARG_CATEGORY));
            binding.tvArticleTitle.setText(getArguments().getString(ARG_TITLE));
            binding.tvAuthorName.setText(getArguments().getString(ARG_SOURCE_NAME));
            binding.tvTimeAgo.setText(getArguments().getString(ARG_TIME));
            binding.ivArticleMainImage.setImageResource(getArguments().getInt(ARG_IMAGE));
            binding.ivAuthorLogo.setImageResource(getArguments().getInt(ARG_SOURCE_LOGO));
        }

        binding.ivBack.setOnClickListener(v -> requireActivity().onBackPressed());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
