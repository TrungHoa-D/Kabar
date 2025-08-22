package com.example.socialnetwork.ui.main.search.newsSearch;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.databinding.ItemNewsArticleBinding;
import com.example.socialnetwork.utils.TimeUtils;

public class NewsAdapter extends ListAdapter<PostDto, NewsAdapter.NewsViewHolder> {

    public interface OnPostClickListener {
        void onPostClick(long postId);
    }

    private OnPostClickListener onPostClickListener;

    public void setOnPostClickListener(OnPostClickListener listener) {
        this.onPostClickListener = listener;
    }

    public NewsAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsArticleBinding binding = ItemNewsArticleBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new NewsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        PostDto article = getItem(position);
        holder.bind(article);

        holder.itemView.setOnClickListener(v -> {
            if (onPostClickListener != null) {
                onPostClickListener.onPostClick(article.getId());
            }
        });
    }


    static class NewsViewHolder extends RecyclerView.ViewHolder {
        private final ItemNewsArticleBinding binding;

        public NewsViewHolder(ItemNewsArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PostDto article) {
            binding.articleCategory.setText(article.getTopic().getName());
            binding.articleTitle.setText(article.getTitle());
            binding.articleSourceName.setText(article.getAuthor().getFullName());
            binding.articleTime.setText("• " + TimeUtils.getTimeAgo(article.getCreatedAt()));

            Glide.with(itemView.getContext()).load(article.getCoverImageUrl()).into(binding.articleImage);
            Glide.with(itemView.getContext()).load(article.getAuthor().getAvatarUrl()).into(binding.articleSourceLogo);
        }
    }

    private static final DiffUtil.ItemCallback<PostDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<PostDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull PostDto oldItem, @NonNull PostDto newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull PostDto oldItem, @NonNull PostDto newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getLikeCount() == newItem.getLikeCount();
        }
    };
}