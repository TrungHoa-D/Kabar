package com.example.socialnetwork.ui.main.home.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.databinding.ItemNewsArticleBinding;
import com.example.socialnetwork.ui.main.home.HomeFragmentDirections;

import java.util.ArrayList;
import java.util.List;

// Lưu ý: Adapter này nên được chuyển thành ListAdapter với DiffUtil để tối ưu hiệu năng sau này
public class ArticlesPagerAdapter extends RecyclerView.Adapter<ArticlesPagerAdapter.ArticleViewHolder> {

    private List<PostDto> articles = new ArrayList<>();
    private final Fragment fragment;

    public ArticlesPagerAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setArticles(List<PostDto> articles) {
        this.articles = articles;
        notifyDataSetChanged(); // Tạm thời dùng notifyDataSetChanged, sau này nên dùng DiffUtil
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewsArticleBinding binding = ItemNewsArticleBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ArticleViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        PostDto article = articles.get(position);
        holder.bind(article);

        holder.itemView.setOnClickListener(v -> {
            NavDirections action = HomeFragmentDirections
                    .actionHomeFragmentToDetailFragment(article.getId());
            NavHostFragment.findNavController(fragment).navigate(action);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    static class ArticleViewHolder extends RecyclerView.ViewHolder {
        private final ItemNewsArticleBinding binding;

        ArticleViewHolder(ItemNewsArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(PostDto article) {
            binding.articleCategory.setText(article.getTopic().getName());
            binding.articleTitle.setText(article.getTitle());
            binding.articleSourceName.setText(article.getAuthor().getFullName());
            // binding.articleTime.setText("• " + article.getCreatedAt()); // Cần hàm format thời gian

            // Load ảnh bài viết từ URL
            Glide.with(itemView.getContext())
                    .load(article.getCoverImageUrl())
                    .placeholder(R.drawable.image_placeholder) // Ảnh chờ
                    .error(R.drawable.image_placeholder) // Ảnh lỗi
                    .into(binding.articleImage);

            // Load logo tác giả từ URL
            Glide.with(itemView.getContext())
                    .load(article.getAuthor().getAvatarUrl())
                    .placeholder(R.drawable.kabar_home_logo) // Ảnh chờ
                    .error(R.drawable.image_placeholder) // Ảnh lỗi
                    .into(binding.articleSourceLogo);
        }
    }
}