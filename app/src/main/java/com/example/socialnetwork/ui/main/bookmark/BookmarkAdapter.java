package com.example.socialnetwork.ui.main.bookmark;

import com.example.socialnetwork.ui.main.home.trending.Article;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.databinding.ItemBookmarkArticleBinding;
import com.example.socialnetwork.ui.main.home.trending.Article;

import java.util.List;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ArticleViewHolder> {

    private List<Article> articles;

    public BookmarkAdapter(List<Article> articles) {
        this.articles = articles;
    }

    public void setArticles(List<Article> newArticles) {
        this.articles = newArticles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemBookmarkArticleBinding binding = ItemBookmarkArticleBinding.inflate(inflater, parent, false);
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        holder.bind(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0;
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final ItemBookmarkArticleBinding binding;

        public ArticleViewHolder(ItemBookmarkArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Article article) {
            binding.tvArticleTitle.setText(article.getTitle());
            binding.tvArticleCategory.setText(article.getCategory());
            binding.tvArticleSource.setText(article.getSourceName());
            binding.tvArticleTime.setText(article.getTimeAgo());
        }
    }
}
