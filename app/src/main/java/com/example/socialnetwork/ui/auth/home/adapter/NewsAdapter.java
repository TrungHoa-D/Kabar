package com.example.socialnetwork.ui.auth.home.adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.databinding.ItemNewsArticleBinding;
import com.example.socialnetwork.ui.auth.home.model.NewsArticle;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsArticle> articles = new ArrayList<>();

    public void setArticles(List<NewsArticle> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsArticleBinding binding = ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ItemNewsArticleBinding binding;

        public NewsViewHolder(ItemNewsArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NewsArticle article) {
            binding.tvArticleTitle.setText(article.title);
            binding.tvArticleSource.setText(article.source);
            // Hiện tại chưa load ảnh từ URL, sẽ cập nhật khi có API
        }
    }
}
