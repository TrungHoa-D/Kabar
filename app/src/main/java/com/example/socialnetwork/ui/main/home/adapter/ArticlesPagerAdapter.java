package com.example.socialnetwork.ui.main.home.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.databinding.ItemNewsArticleBinding;
import com.example.socialnetwork.utils.TimeUtils;

import java.util.Objects;

public class ArticlesPagerAdapter extends ListAdapter<PostDto, ArticlesPagerAdapter.ArticleViewHolder> {

    public interface OnPostClickListener {
        void onPostClick(long postId);
    }

    private OnPostClickListener onPostClickListener;

    public void setOnPostClickListener(OnPostClickListener listener) {
        this.onPostClickListener = listener;
    }

    public ArticlesPagerAdapter() {
        super(DIFF_CALLBACK);
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
        PostDto article = getItem(position);
        holder.bind(article);

        holder.itemView.setOnClickListener(v -> {
            if (onPostClickListener != null) {
                onPostClickListener.onPostClick(article.getId());
            }
        });
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final ItemNewsArticleBinding binding;

        ArticleViewHolder(ItemNewsArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(PostDto article) {
            binding.articleCategory.setText(article.getTopic().getName());
            binding.articleTitle.setText(article.getTitle());
            binding.articleSourceName.setText(article.getAuthor().getFullName());
            String timeAgo = TimeUtils.getTimeAgo(article.getCreatedAt());
            binding.articleTime.setText("• " + timeAgo);

            Glide.with(itemView.getContext())
                    .load(article.getCoverImageUrl())
                    .placeholder(R.drawable.image_placeholder)
                    .error(R.drawable.image_placeholder)
                    .into(binding.articleImage);

            Glide.with(itemView.getContext())
                    .load(article.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.kabar_home_logo)
                    .error(R.drawable.image_placeholder)
                    .into(binding.articleSourceLogo);
        }
    }

    private static final DiffUtil.ItemCallback<PostDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<PostDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull PostDto oldItem, @NonNull PostDto newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull PostDto oldItem, @NonNull PostDto newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };
}