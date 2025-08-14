package com.example.socialnetwork.ui.main.home.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.ItemNewsArticleBinding;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;

public class NewsAdapter extends ListAdapter<NewsArticle, NewsAdapter.NewsViewHolder> {

    public NewsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsArticleBinding binding = ItemNewsArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsArticle article = getItem(position);
        holder.bind(article);
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ItemNewsArticleBinding binding;

        public NewsViewHolder(ItemNewsArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NewsArticle article) {
            binding.articleTitle.setText(article.title);
            binding.articleSourceName.setText(article.source); // Đảm bảo ID trong XML là articleSourceName

            Glide.with(itemView.getContext())
                    .load(article.imageUrl)
                    .placeholder(R.drawable.image_placeholder) // Cần tạo file ảnh này
                    .error(R.drawable.image_placeholder)
                    .into(binding.articleImage);
        }
    }

    private static final DiffUtil.ItemCallback<NewsArticle> DIFF_CALLBACK = new DiffUtil.ItemCallback<NewsArticle>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewsArticle oldItem, @NonNull NewsArticle newItem) {
            return oldItem.title.equals(newItem.title);
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsArticle oldItem, @NonNull NewsArticle newItem) {
            return oldItem.equals(newItem);
        }
    };
}
