package com.example.socialnetwork.ui.auth.home.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.ItemCategoryBinding;
import com.example.socialnetwork.ui.auth.home.model.NewsCategory;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private List<NewsCategory> categories = new ArrayList<>();

    public void setCategories(List<NewsCategory> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryBinding binding;

        public CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NewsCategory category) {
            binding.tvCategoryName.setText(category.name);
            if (category.isSelected) {
                binding.cardCategory.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.purple_500));
                binding.tvCategoryName.setTextColor(Color.WHITE);
            } else {
                binding.cardCategory.setCardBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.grey));
                binding.tvCategoryName.setTextColor(ContextCompat.getColor(itemView.getContext(), R.color.grey));
            }
        }
    }
}
