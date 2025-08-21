// filepath: com/example/socialnetwork/ui/main/search/author/AuthorAdapter.java
package com.example.socialnetwork.ui.main.search.author;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.databinding.ItemSearchAuthorBinding;

public class AuthorAdapter extends ListAdapter<UserDto, AuthorAdapter.AuthorViewHolder> {
    private static final String TAG = "AuthorAdapter";

    public interface OnAuthorClickListener {
        void onAuthorClick(String userId);
    }
    private OnAuthorClickListener onAuthorClickListener;

    public void setOnAuthorClickListener(OnAuthorClickListener listener) {
        this.onAuthorClickListener = listener;
    }

    public AuthorAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchAuthorBinding binding = ItemSearchAuthorBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new AuthorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        UserDto author = getItem(position);
        holder.bind(author);

        holder.itemView.setOnClickListener(v -> {
            if (onAuthorClickListener != null) {
                onAuthorClickListener.onAuthorClick(author.getId());
            }
        });
    }

    static class AuthorViewHolder extends RecyclerView.ViewHolder {
        private final ItemSearchAuthorBinding binding;

        public AuthorViewHolder(@NonNull ItemSearchAuthorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(UserDto author) {
            binding.tvTopicTitle.setText(author.getFullName());
            String followerText = author.getFollowersCount() + " Followers";
            binding.tvTopicDescription.setText(followerText);

            Glide.with(itemView.getContext())
                    .load(author.getAvatarUrl())
                    .placeholder(R.drawable.avatar_default_svgrepo_com)
                    .circleCrop()
                    .into(binding.ivTopicImage);
        }
    }

    private static final DiffUtil.ItemCallback<UserDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserDto oldItem, @NonNull UserDto newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserDto oldItem, @NonNull UserDto newItem) {
            return oldItem.getFullName().equals(newItem.getFullName()) &&
                    oldItem.getFollowersCount() == newItem.getFollowersCount();
        }
    };
}