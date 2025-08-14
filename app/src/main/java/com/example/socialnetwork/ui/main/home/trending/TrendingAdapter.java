package com.example.socialnetwork.ui.main.home.trending;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.socialnetwork.databinding.ItemTrendingArticleBinding; // Thay bằng package của bạn
import com.example.socialnetwork.ui.main.home.trending.Article; // Thay bằng package của bạn

public class TrendingAdapter extends ListAdapter<Article, TrendingAdapter.TrendingViewHolder> {

    public TrendingAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public TrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTrendingArticleBinding binding = ItemTrendingArticleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new TrendingViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendingViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class TrendingViewHolder extends RecyclerView.ViewHolder {
        private final ItemTrendingArticleBinding binding;

        public TrendingViewHolder(ItemTrendingArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Article article) {
            binding.tvArticleCategory.setText(article.category);
            binding.tvArticleTitle.setText(article.title);
            binding.tvSourceName.setText(article.sourceName);
            binding.tvTime.setText("• " + article.timeAgo);

            Glide.with(itemView.getContext()).load(article.imageUrl).into(binding.ivArticleImage);
            Glide.with(itemView.getContext()).load(article.sourceLogoUrl).into(binding.ivSourceLogo);
        }
    }

    private static final DiffUtil.ItemCallback<Article> DIFF_CALLBACK = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.id.equals(newItem.id);
        }
        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.equals(newItem);
        }
    };
}