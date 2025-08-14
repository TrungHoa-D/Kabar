package com.example.socialnetwork.ui.main.search.topic;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.databinding.ItemSearchTopicBinding;

import java.util.ArrayList;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private List<Topic> topics = new ArrayList<>();

    public TopicAdapter() {

    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics != null ? topics : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemSearchTopicBinding binding = ItemSearchTopicBinding.inflate(inflater, parent, false);
        return new TopicViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.binding.tvTopicTitle.setText(topic.getTitle());
        holder.binding.tvTopicDescription.setText(topic.getDescription());
        holder.binding.ivTopicImage.setImageResource(topic.getImageResId());
        holder.binding.btnSave.setOnClickListener(v -> {
            // Xử lý khi bấm Save
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        ItemSearchTopicBinding binding;

        public TopicViewHolder(@NonNull ItemSearchTopicBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
