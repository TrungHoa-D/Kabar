package com.example.socialnetwork.ui.main.notification;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.socialnetwork.databinding.ItemNotificationBinding;
import com.example.socialnetwork.ui.main.notification.NotificationItem;

public class NotificationAdapter extends ListAdapter<NotificationItem, NotificationAdapter.NotificationViewHolder> {

    public NotificationAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNotificationBinding binding = ItemNotificationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NotificationViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    static class NotificationViewHolder extends RecyclerView.ViewHolder {
        private final ItemNotificationBinding binding;

        public NotificationViewHolder(ItemNotificationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(NotificationItem item) {
            binding.tvNotificationTime.setText(item.timeAgo);
            binding.tvNotificationContent.setText(getFormattedContent(item));

            Glide.with(itemView.getContext())
                    .load(item.avatarUrl)
                    .circleCrop()
                    .into(binding.ivAvatar);

            // Hiển thị hoặc ẩn nút Follow dựa trên loại thông báo
            if (item.type == NotificationItem.NotificationType.FOLLOW) {
                binding.btnFollow.setVisibility(View.VISIBLE);
            } else {
                binding.btnFollow.setVisibility(View.GONE);
            }
        }

        // Phương thức để làm đậm một phần của văn bản
        private SpannableStringBuilder getFormattedContent(NotificationItem item) {
            SpannableStringBuilder builder = new SpannableStringBuilder(item.content);
            String[] parts = item.content.split(" ");
            if (parts.length >= 2) {
                // Làm đậm 2 từ đầu tiên (ví dụ: "BBC News", "Modelyn Saris")
                builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, (parts[0] + " " + parts[1]).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            return builder;
        }
    }

    private static final DiffUtil.ItemCallback<NotificationItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<NotificationItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull NotificationItem oldItem, @NonNull NotificationItem newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull NotificationItem oldItem, @NonNull NotificationItem newItem) {
            return oldItem.content.equals(newItem.content); // So sánh đơn giản
        }
    };
}
