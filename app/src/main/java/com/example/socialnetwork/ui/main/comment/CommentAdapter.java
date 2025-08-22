package com.example.socialnetwork.ui.main.comment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.CommentDto;
import com.example.socialnetwork.databinding.ItemCommentBinding;
import com.example.socialnetwork.utils.TimeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CommentAdapter extends ListAdapter<CommentDto, CommentAdapter.CommentViewHolder> {

    // ✅ Thêm interface để xử lý sự kiện click vào nút like
    public interface OnCommentLikeClickListener {
        void onLikeClick(CommentDto comment);
    }
    private OnCommentLikeClickListener likeClickListener;
    private Map<Long, Boolean> likeStatusMap = new HashMap<>();

    public void setOnLikeClickListener(OnCommentLikeClickListener listener) {
        this.likeClickListener = listener;
    }

    // ✅ Thêm hàm để Fragment cập nhật bản đồ trạng thái like
    public void setLikeStatusMap(Map<Long, Boolean> map) {
        this.likeStatusMap = map;
        notifyDataSetChanged(); // Yêu cầu RecyclerView vẽ lại các item bị ảnh hưởng
    }

    public CommentAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCommentBinding binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        CommentDto comment = getItem(position);
        // Lấy trạng thái like từ bản đồ
        Boolean isLiked = likeStatusMap.getOrDefault(comment.getId(), false);
        holder.bind(comment, isLiked, likeClickListener);
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        private final ItemCommentBinding binding;
        public CommentViewHolder(ItemCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        // ✅ Cập nhật hàm bind để nhận thêm trạng thái like và listener
        void bind(CommentDto comment, boolean isLiked, OnCommentLikeClickListener listener) {
            binding.tvAuthorName.setText(comment.getAuthor().getFullName());
            binding.tvCommentContent.setText(comment.getContent());
            binding.tvTimeAgo.setText(TimeUtils.getTimeAgo(comment.getCreatedAt()));
            String likes = comment.getLikeCount() + " likes";
            binding.tvLikeCount.setText(likes);

            Glide.with(itemView.getContext())
                    .load(comment.getAuthor().getAvatarUrl())
                    .circleCrop()
                    .placeholder(R.drawable.avatar_default_svgrepo_com)
                    .into(binding.ivAvatar);

            // ✅ Cập nhật icon trái tim dựa trên trạng thái isLiked
            if (isLiked) {
                binding.ivHeart.setImageResource(R.drawable.ic_heart_filled);
            } else {
                binding.ivHeart.setImageResource(R.drawable.ic_heart_outline);
            }

            // ✅ Bắt sự kiện click
            binding.ivHeart.setOnClickListener(v -> {
                if(listener != null) {
                    listener.onLikeClick(comment);
                }
            });
        }
    }

    private static final DiffUtil.ItemCallback<CommentDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<CommentDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull CommentDto oldItem, @NonNull CommentDto newItem) {
            return oldItem.getId() == newItem.getId();
        }
        @Override
        public boolean areContentsTheSame(@NonNull CommentDto oldItem, @NonNull CommentDto newItem) {
            // ✅ Cập nhật logic so sánh để bao gồm cả trạng thái like
            return oldItem.getContent().equals(newItem.getContent())
                    && oldItem.getLikeCount() == newItem.getLikeCount();
        }
    };
}