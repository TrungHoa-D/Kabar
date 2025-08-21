package com.example.socialnetwork.ui.main.profile;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.data.source.local.TokenManager;
import com.example.socialnetwork.databinding.FragmentUserProfileBinding;
import com.example.socialnetwork.ui.main.home.adapter.ArticlesPagerAdapter;
import com.google.android.material.button.MaterialButton;

public class UserProfileFragment extends Fragment implements ArticlesPagerAdapter.OnPostClickListener {

    private FragmentUserProfileBinding binding;
    private UserProfileViewModel viewModel;
    private ArticlesPagerAdapter articlesAdapter;
    private String userId;
    private TokenManager tokenManager;
    private UserDto currentUser;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = UserProfileFragmentArgs.fromBundle(getArguments()).getUserId();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
        tokenManager = new TokenManager(requireContext());

        setupToolbar();
        setupRecyclerView();
        observeState();
        setupClickListeners();

        viewModel.loadUserProfile(userId);
    }

    private void setupClickListeners() {
        binding.btnFollow.setOnClickListener(v -> {
            if (currentUser != null) {
                viewModel.toggleFollow(currentUser);
            }
        });
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());
    }

    private void setupRecyclerView() {
        articlesAdapter = new ArticlesPagerAdapter();
        binding.rvUserPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvUserPosts.setAdapter(articlesAdapter);
        articlesAdapter.setOnPostClickListener(this);
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state.error != null) {
                Toast.makeText(getContext(), state.error, Toast.LENGTH_SHORT).show();
            }
            if (state.user != null) {
                currentUser = state.user;
                updateUi(state.user);
            }
            if (state.posts != null) {
                articlesAdapter.submitList(state.posts);
                binding.tvNewsCount.setText(String.valueOf(state.posts.size()));
            }
        });
    }

    private void updateUi(UserDto user) {
        binding.tvProfileName.setText(user.getFullName());
        binding.tvProfileQuote.setText(user.getBio());
        binding.tvFollowersCount.setText(String.valueOf(user.getFollowersCount()));
        binding.tvFollowingCount.setText(String.valueOf(user.getFollowingCount()));

        Glide.with(this)
                .load(user.getAvatarUrl())
                .placeholder(R.drawable.avatar_default_svgrepo_com)
                .circleCrop()
                .into(binding.ivProfileAvatar);

        String currentLoginUserId = tokenManager.getCurrentUserId();
        if (currentLoginUserId != null && currentLoginUserId.equals(user.getId())) {
            binding.btnFollow.setVisibility(View.GONE);
        } else {
            binding.btnFollow.setVisibility(View.VISIBLE);
            updateFollowButton(user.isFollowed());
        }
    }

    private void updateFollowButton(boolean isFollowed) {
        if (isFollowed) {
            binding.btnFollow.setText("Following");
            ((MaterialButton)binding.btnFollow).setIcon(null);
            binding.btnFollow.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.md_theme_light_surfaceVariant));
            binding.btnFollow.setTextColor(ContextCompat.getColor(requireContext(), R.color.md_theme_light_onSurfaceVariant));

        } else {
            binding.btnFollow.setText("Follow");
            binding.btnFollow.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.blue));
            binding.btnFollow.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        }
    }

    @Override
    public void onPostClick(long postId) {
        NavDirections action = UserProfileFragmentDirections.actionUserProfileFragmentToDetailFragment(postId);
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}