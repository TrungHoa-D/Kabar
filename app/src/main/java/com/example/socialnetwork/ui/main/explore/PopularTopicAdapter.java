package com.example.socialnetwork.ui.main.explore;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.ItemPopularTopicBinding;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;
import com.example.socialnetwork.ui.main.home.trending.Article;

import java.util.ArrayList;
import java.util.List;

public class PopularTopicAdapter extends RecyclerView.Adapter<PopularTopicAdapter.ArticleViewHolder> {

    private List<NewsArticle> articleList;

    public PopularTopicAdapter() {
        this.articleList = new ArrayList<>();
    }

    public PopularTopicAdapter(List<NewsArticle> articleList) {
        this.articleList = articleList;
    }

    public void setArticleList(List<NewsArticle> newArticleList) {
        this.articleList = newArticleList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPopularTopicBinding binding = ItemPopularTopicBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        NewsArticle article = articleList.get(position);
        holder.binding.tvArticleTopic.setText(article.getCategory());
        holder.binding.tvArticleTitle.setText(article.getTitle());
        holder.binding.tvSourceName.setText(article.getSourceName());
        holder.binding.tvTimeAgo.setText(article.getTime());

        // Load ảnh bằng Glide
        Glide.with(holder.binding.getRoot().getContext())
                .load(article.getImageResId())
                .into(holder.binding.ivArticleImage);

        Glide.with(holder.binding.getRoot().getContext())
                .load(article.getSourceLogoResId())
                .into(holder.binding.ivSourceLogo);

    }

    @Override
    public int getItemCount() {
        return articleList != null ? articleList.size() : 0;
    }



    // ViewHolder để ánh xạ các view trong item
    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        ItemPopularTopicBinding binding;
        public ArticleViewHolder(@NonNull ItemPopularTopicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }
}