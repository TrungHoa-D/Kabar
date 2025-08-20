package com.example.socialnetwork.ui.main.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.databinding.FragmentDetailBinding;

public class DetailFragment extends Fragment {

    private FragmentDetailBinding binding;
    private DetailViewModel viewModel;
    private long postId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postId = com.example.socialnetwork.ui.main.details.DetailFragmentArgs.fromBundle(getArguments()).getPostId();
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

        setupToolbar();
        observeViewModel();

        viewModel.loadArticleDetail(postId);
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v ->
                NavHostFragment.findNavController(this).navigateUp()
        );
    }

    private void observeViewModel() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);
            binding.contentLayout.setVisibility(state.isLoading ? View.GONE : View.VISIBLE);

            if (state.error != null) {
                Toast.makeText(getContext(), state.error, Toast.LENGTH_SHORT).show();
            }

            if (state.article != null) {
                updateUi(state.article);
            }
        });
    }

    private void updateUi(PostDto article) {
        binding.tvAuthorName.setText(article.getAuthor().getFullName());
        binding.tvPostTime.setText(article.getCreatedAt()); // Cần format lại
        binding.tvCategory.setText(article.getTopic().getName());
        binding.tvTitle.setText(article.getTitle());
        binding.tvContent.setText(article.getContent());

        Glide.with(this).load(article.getAuthor().getAvatarUrl()).into(binding.ivAuthorAvatar);
        Glide.with(this).load(article.getCoverImageUrl()).into(binding.ivCoverImage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}