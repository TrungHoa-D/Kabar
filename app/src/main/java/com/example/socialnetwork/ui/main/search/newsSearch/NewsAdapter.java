package com.example.socialnetwork.ui.main.search.newsSearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.R;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private List<NewsArticle> articles;

    public NewsAdapter(List<NewsArticle> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_news_article, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsArticle article = articles.get(position);
        holder.articleCategory.setText(article.getCategory());
        holder.articleTitle.setText(article.getTitle());
        holder.articleImage.setImageResource(article.getImageResId());
        holder.articleSourceLogo.setImageResource(article.getSourceLogoResId());
        holder.articleSourceName.setText(article.getSourceName());
        holder.articleTime.setText(article.getTime());
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        ShapeableImageView articleImage;
        TextView articleCategory, articleTitle, articleSourceName, articleTime;
        ImageView articleSourceLogo;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            articleImage = itemView.findViewById(R.id.articleImage);
            articleCategory = itemView.findViewById(R.id.articleCategory);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            articleSourceLogo = itemView.findViewById(R.id.articleSourceLogo);
            articleSourceName = itemView.findViewById(R.id.articleSourceName);
            articleTime = itemView.findViewById(R.id.articleTime);
        }
    }
}
