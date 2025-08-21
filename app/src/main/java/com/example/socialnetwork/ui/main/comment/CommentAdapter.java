package com.example.socialnetwork.ui.main.comment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.data.model.dto.CommentDto;
import com.example.socialnetwork.databinding.ItemCommentBinding;
import com.example.socialnetwork.utils.TimeUtils;

public class CommentAdapter extends ListAdapter<CommentDto, CommentAdapter.CommentViewHolder> {

    public CommentAdapter() { super(DIFF_CALLBACK); }

    @NonNull @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        private final ItemCommentBinding binding;
        public CommentViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CommentDto comment) {
            binding.tvAuthorName.setText(comment.getAuthor().getFullName());
            binding.tvCommentContent.setText(comment.getContent());
            binding.tvTimeAgo.setText(TimeUtils.getTimeAgo(comment.getCreatedAt()));
            String likes = comment.getLikeCount() + " likes";
            binding.tvLikeCount.setText(likes);
            Glide.with(itemView.getContext()).load(comment.getAuthor().getAvatarUrl()).circleCrop().into(binding.ivAvatar);
        }
    }

    private static final DiffUtil.ItemCallback<CommentDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<CommentDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull CommentDto oldItem, @NonNull CommentDto newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @Override
        public boolean areContentsTheSame(@NonNull CommentDto oldItem, @NonNull CommentDto newItem) {
            return oldItem.getContent().equals(newItem.getContent()) && oldItem.getLikeCount() == newItem.getLikeCount();
        }
    };
}