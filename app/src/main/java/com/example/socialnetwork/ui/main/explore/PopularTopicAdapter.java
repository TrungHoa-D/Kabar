package com.example.socialnetwork.ui.main.explore;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.databinding.ItemPopularTopicBinding;
import com.example.socialnetwork.utils.TimeUtils;

import java.util.Objects;

public class PopularTopicAdapter extends ListAdapter<PostDto, PopularTopicAdapter.ArticleViewHolder> {

    public PopularTopicAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPopularTopicBinding binding = ItemPopularTopicBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        PostDto article = getItem(position);
        holder.bind(article);
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final ItemPopularTopicBinding binding;

        public ArticleViewHolder(@NonNull ItemPopularTopicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PostDto article) {
            binding.tvArticleTopic.setText(article.getTopic().getName());
            binding.tvArticleTitle.setText(article.getTitle());
            binding.tvSourceName.setText(article.getAuthor().getFullName());
            binding.tvTimeAgo.setText(TimeUtils.getTimeAgo(article.getCreatedAt()));

            Glide.with(binding.getRoot().getContext()).load(article.getCoverImageUrl()).into(binding.ivArticleImage);
            Glide.with(binding.getRoot().getContext()).load(article.getAuthor().getAvatarUrl()).into(binding.ivSourceLogo);
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