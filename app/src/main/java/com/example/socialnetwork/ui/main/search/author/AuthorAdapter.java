package com.example.socialnetwork.ui.main.search.author;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.databinding.ItemSearchAuthorBinding;

import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {

    private List<Author> authors;

    public AuthorAdapter(List<Author> authors) {
        this.authors = authors;
    }

    // Phương thức để cập nhật dữ liệu
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // Sử dụng lớp binding để inflate layout thay vì View
        ItemSearchAuthorBinding binding = ItemSearchAuthorBinding.inflate(inflater, parent, false);
        return new AuthorViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        Author author = authors.get(position);
        holder.bind(author);
    }

    @Override
    public int getItemCount() {
        return authors != null ? authors.size() : 0;
    }

    static class AuthorViewHolder extends RecyclerView.ViewHolder {
        private final ItemSearchAuthorBinding binding;

        public AuthorViewHolder(@NonNull ItemSearchAuthorBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Author author) {
            binding.tvTopicTitle.setText(author.getName());
            binding.tvTopicDescription.setText(author.getFollowerCount());
            binding.ivTopicImage.setImageResource(author.getLogoResource());
        }
    }
}