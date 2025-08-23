package com.example.socialnetwork.ui.main.message;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.databinding.ItemUserBinding;
import java.util.Objects;

public class UserAdapter extends ListAdapter<UserDto, UserAdapter.UserViewHolder> {

    public interface OnUserClickListener {
        void onUserClick(UserDto user);
    }

    private OnUserClickListener onUserClickListener;

    public void setOnUserClickListener(OnUserClickListener listener) {
        this.onUserClickListener = listener;
    }

    public UserAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new UserViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserDto user = getItem(position);
        holder.bind(user);
        holder.itemView.setOnClickListener(v -> {
            if (onUserClickListener != null) {
                onUserClickListener.onUserClick(user);
            }
        });
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {
        private final ItemUserBinding binding;
        public UserViewHolder(@NonNull ItemUserBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(UserDto user) {
            binding.tvUserName.setText(user.getFullName());
            Glide.with(binding.getRoot().getContext()).load(user.getAvatarUrl()).into(binding.ivAvatar);
        }
    }

    private static final DiffUtil.ItemCallback<UserDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<UserDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull UserDto oldItem, @NonNull UserDto newItem) {
            return Objects.equals(oldItem.getId(), newItem.getId());
        }
        @Override
        public boolean areContentsTheSame(@NonNull UserDto oldItem, @NonNull UserDto newItem) {
            return Objects.equals(oldItem, newItem);
        }
    };
}