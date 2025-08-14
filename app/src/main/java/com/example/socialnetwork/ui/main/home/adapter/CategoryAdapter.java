package com.example.socialnetwork.ui.main.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetwork.databinding.ItemCategoryBinding;
import com.example.socialnetwork.ui.main.home.model.NewsCategory;

public class CategoryAdapter extends ListAdapter<NewsCategory, CategoryAdapter.CategoryViewHolder> {
    private int selectedPosition = RecyclerView.NO_POSITION;

    private OnCategoryClickListener onCategoryClickListener;
    public interface OnCategoryClickListener {
        void onCategoryClick(NewsCategory category, int position);
    }

    public CategoryAdapter() {
        super(DIFF_CALLBACK);

    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCategoryBinding binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        NewsCategory currentCategory = getItem(position);
        holder.bind(currentCategory, position == selectedPosition);

    }

    public void setSelectedPosition(int position) {
        int previousSelectedPosition = selectedPosition;
        selectedPosition = position;
        if (previousSelectedPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(previousSelectedPosition);
        }
        if (selectedPosition != RecyclerView.NO_POSITION) {
            notifyItemChanged(selectedPosition);
        }
    }

    // (Tùy chọn) Một phương thức để lấy category đang được chọn
    public NewsCategory getSelectedCategory() {
        if (selectedPosition != RecyclerView.NO_POSITION && selectedPosition < getItemCount()) {
            return getItem(selectedPosition);
        }
        return null;
    }

    static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ItemCategoryBinding binding;

        public CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(NewsCategory category, boolean isSelected) {
            binding.tvCategoryName.setText(category.name);
            if (isSelected) {
                binding.viewUnderline.setVisibility(View.VISIBLE);
            } else {
                binding.viewUnderline.setVisibility(View.GONE);
            }
        }
    }

    private static final DiffUtil.ItemCallback<NewsCategory> DIFF_CALLBACK = new DiffUtil.ItemCallback<NewsCategory>() {
        @Override
        public boolean areItemsTheSame(@NonNull NewsCategory oldItem, @NonNull NewsCategory newItem) {
            // Giả sử 'name' là ID duy nhất hoặc bạn có một trường 'id' thực sự
            if (oldItem.name != null && newItem.name != null) {
                return oldItem.name.equals(newItem.name);
            }

            return oldItem.name.equals(newItem.name);
        }

        @Override
        public boolean areContentsTheSame(@NonNull NewsCategory oldItem, @NonNull NewsCategory newItem) {
            return oldItem.equals(newItem);
        }
    };
}
