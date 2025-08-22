package com.example.socialnetwork.ui.main.home.trending;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.databinding.ItemTrendingArticleBinding;

public class TrendingAdapter extends ListAdapter<PostDto, TrendingAdapter.TrendingViewHolder> {

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

        void bind(PostDto article) {
            binding.tvArticleCategory.setText(article.getTopic().getName());
            binding.tvArticleTitle.setText(article.getTitle());
            binding.tvSourceName.setText(article.getAuthor().getFullName());
            // binding.tvTime.setText("• " + article.getCreatedAt()); // Cần hàm format thời gian

            Glide.with(itemView.getContext())
                    .load(article.getCoverImageUrl())
                    .placeholder(R.drawable.image_placeholder)
                    .into(binding.ivArticleImage);

            Glide.with(itemView.getContext())
                    .load(article.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.image_placeholder)
                    .into(binding.ivSourceLogo);
        }
    }

    private static final DiffUtil.ItemCallback<PostDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<PostDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull PostDto oldItem, @NonNull PostDto newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @Override
        public boolean areContentsTheSame(@NonNull PostDto oldItem, @NonNull PostDto newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getLikeCount() == newItem.getLikeCount();
        }
    };
}