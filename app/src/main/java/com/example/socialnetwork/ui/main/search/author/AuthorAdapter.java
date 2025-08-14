package com.example.socialnetwork.ui.main.search.author;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialnetwork.R;
import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {

    private List<String> authors;

    public AuthorAdapter(List<String> authors) {
        this.authors = authors;
    }

    // Phương thức để cập nhật dữ liệu
    public void setAuthors(List<String> authors) {
        this.authors = authors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_search_author, parent, false);
        return new AuthorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        String author = authors.get(position);
        holder.topicTitle.setText(author);
    }

    @Override
    public int getItemCount() {
        return authors != null ? authors.size() : 0;
    }

    static class AuthorViewHolder extends RecyclerView.ViewHolder {
        ImageView topicImage;
        TextView topicTitle;
        TextView topicDescription;
        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            topicImage = itemView.findViewById(R.id.iv_topic_image);
            topicTitle = itemView.findViewById(R.id.tv_topic_title);
            topicDescription = itemView.findViewById(R.id.tv_topic_description);
        }
    }
}