package com.example.socialnetwork.ui.main.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.databinding.ItemNewsArticleBinding;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;

import java.util.List;

public class ArticlesPagerAdapter extends RecyclerView.Adapter<ArticlesPagerAdapter.ArticleViewHolder> {

    private List<NewsArticle> listArticles;
    private OnArticleClickListener listener;

    public ArticlesPagerAdapter(List<NewsArticle> listArticles, OnArticleClickListener listener) {
        this.listArticles = listArticles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsArticleBinding binding = ItemNewsArticleBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        NewsArticle article = listArticles.get(position);

        holder.binding.articleCategory.setText(article.getCategory());
        holder.binding.articleTitle.setText(article.getTitle());
        holder.binding.articleSourceName.setText(article.getSourceName());
        holder.binding.articleTime.setText("• " + article.getTime());

        // Load ảnh bằng Glide/Picasso
        Glide.with(holder.itemView.getContext())
                .load(article.getImageResId())
                .into(holder.binding.articleImage);

        Glide.with(holder.itemView.getContext())
                .load(article.getSourceLogoResId())
                .into(holder.binding.articleSourceLogo);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) listener.onArticleClick(article);
        });

    }

    @Override
    public int getItemCount() {
        return listArticles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        ItemNewsArticleBinding binding;

        ArticleViewHolder(ItemNewsArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
