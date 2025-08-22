package com.example.socialnetwork.ui.main.search.topic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.TopicDto;
import com.example.socialnetwork.databinding.ItemSearchTopicBinding;

public class TopicAdapter extends ListAdapter<TopicDto, TopicAdapter.TopicViewHolder> {

    public TopicAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSearchTopicBinding binding = ItemSearchTopicBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TopicViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        TopicDto topic = getItem(position);
        holder.bind(topic);
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        private final ItemSearchTopicBinding binding;

        public TopicViewHolder(@NonNull ItemSearchTopicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(TopicDto topic) {
            binding.tvTopicTitle.setText(topic.getName());

            if (topic.getDescription() != null && !topic.getDescription().isEmpty()) {
                binding.tvTopicDescription.setVisibility(View.VISIBLE);
                binding.tvTopicDescription.setText(topic.getDescription());
            } else {
                binding.tvTopicDescription.setVisibility(View.GONE);
            }

            Glide.with(itemView.getContext())
                    .load(topic.getImageUrl())
                    .placeholder(R.drawable.image_placeholder).error(R.drawable.image_placeholder).into(binding.ivTopicImage);
        }
    }

    private static final DiffUtil.ItemCallback<TopicDto> DIFF_CALLBACK = new DiffUtil.ItemCallback<TopicDto>() {
        @Override
        public boolean areItemsTheSame(@NonNull TopicDto oldItem, @NonNull TopicDto newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull TopicDto oldItem, @NonNull TopicDto newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getDescription().equals(newItem.getDescription());
        }
    };
}