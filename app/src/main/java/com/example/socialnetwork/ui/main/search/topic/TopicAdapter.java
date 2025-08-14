package com.example.socialnetwork.ui.main.search.topic;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.R;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    private List<Topic> topics;

    public TopicAdapter(List<Topic> topics) {
        this.topics = topics;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_topic, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.tvTitle.setText(topic.getTitle());
        holder.tvDescription.setText(topic.getDescription());
        holder.ivImage.setImageResource(topic.getImageResId());
        holder.btnSave.setOnClickListener(v -> {
            // Xử lý khi bấm Save
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        TextView tvTitle, tvDescription;
        MaterialButton btnSave;

        public TopicViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.iv_topic_image);
            tvTitle = itemView.findViewById(R.id.tv_topic_title);
            tvDescription = itemView.findViewById(R.id.tv_topic_description);
            btnSave = itemView.findViewById(R.id.btn_save);
        }
    }
}
