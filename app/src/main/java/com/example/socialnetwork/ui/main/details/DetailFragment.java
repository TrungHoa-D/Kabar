package com.example.socialnetwork.ui.main.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.source.local.TokenManager;
import com.example.socialnetwork.databinding.FragmentDetailBinding;
import com.example.socialnetwork.utils.TimeUtils;
import com.google.android.material.button.MaterialButton;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private DetailViewModel viewModel;
    private long postId;
    private TokenManager tokenManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postId = DetailFragmentArgs.fromBundle(getArguments()).getPostId();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DetailViewModel.class);
        tokenManager = new TokenManager(requireContext());

        setupToolbar();
        setupClickListeners();
        observeViewModel();

        if (savedInstanceState == null) {
            viewModel.loadArticleDetail(postId);
        }
    }

    private void setupClickListeners() {
        binding.ivLike.setOnClickListener(v -> {
            Boolean isLiked = viewModel.isLiked.getValue();
            if (isLiked != null) {
                if (isLiked) {
                    viewModel.unlikePost(postId);
                } else {
                    viewModel.likePost(postId);
                }
            }
        });
        binding.ivComment.setOnClickListener(v -> {
            NavDirections action = DetailFragmentDirections.actionDetailFragmentToCommentFragment(postId);
            NavHostFragment.findNavController(this).navigate(action);
        });
        binding.btnFollow.setOnClickListener(v -> {
            viewModel.toggleFollowAuthor();
        });
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
    }

    private void observeViewModel() {
        viewModel.isLoading.observe(getViewLifecycleOwner(), isLoading -> {
            binding.progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            binding.contentLayout.setVisibility(isLoading ? View.GONE : View.VISIBLE);
        });

        viewModel.error.observe(getViewLifecycleOwner(), error -> {
            if (error != null && !error.isEmpty()) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.article.observe(getViewLifecycleOwner(), this::updateUi);

        viewModel.isLiked.observe(getViewLifecycleOwner(), isLiked -> {
            if (isLiked) {
                binding.ivLike.setImageResource(R.drawable.ic_heart_filled);
            } else {
                binding.ivLike.setImageResource(R.drawable.ic_heart_outline);
            }
        });

        viewModel.isAuthorFollowed.observe(getViewLifecycleOwner(), this::updateFollowButton);
    }

    private void updateUi(PostDto article) {
        if (article == null) return;

        binding.tvAuthorName.setText(article.getAuthor().getFullName());
        binding.tvCategory.setText(article.getTopic().getName());
        binding.tvTitle.setText(article.getTitle());
        binding.tvContent.setText(article.getContent());
        binding.tvLikeCount.setText(String.valueOf(article.getLikeCount()));
        binding.tvCommentCount.setText(String.valueOf(article.getCommentCount()));

        String timeAgo = TimeUtils.getTimeAgo(article.getCreatedAt());
        binding.tvPostTime.setText(timeAgo);

        Glide.with(this).load(article.getAuthor().getAvatarUrl()).circleCrop().into(binding.ivAuthorAvatar);
        Glide.with(this).load(article.getCoverImageUrl()).into(binding.ivCoverImage);

        String currentUserId = tokenManager.getCurrentUserId();
        String authorId = article.getAuthor().getId();

        if (currentUserId != null && currentUserId.equals(authorId)) {
            binding.btnFollow.setVisibility(View.GONE);
        } else {
            binding.btnFollow.setVisibility(View.VISIBLE);
        }
    }

    private void updateFollowButton(boolean isFollowed) {
        if (isFollowed) {
            binding.btnFollow.setText("Following");
        } else {
            binding.btnFollow.setText("Follow");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}