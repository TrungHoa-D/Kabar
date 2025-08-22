package com.example.socialnetwork.ui.main.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.databinding.FragmentProfileBinding;
import com.example.socialnetwork.ui.main.home.adapter.ArticlesPagerAdapter;

public class ProfileFragment extends Fragment implements ArticlesPagerAdapter.OnPostClickListener {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private ArticlesPagerAdapter articlesAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        setupRecyclerView();
        setupClickListeners();
        observeState();
        NavController navController = NavHostFragment.findNavController(this);
        navController.getCurrentBackStackEntry().getSavedStateHandle().getLiveData("new_post_created")
                .observe(getViewLifecycleOwner(), result -> {
                    if (Boolean.TRUE.equals(result)) {
                        viewModel.loadCurrentUserProfile();
                        navController.getCurrentBackStackEntry().getSavedStateHandle().remove("new_post_created");
                    }
                });

        viewModel.loadCurrentUserProfile();
    }

    private void setupClickListeners() {
        binding.ivSettings.setOnClickListener(v -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_profileFragment_to_settingsFragment));

        binding.fabAddPost.setOnClickListener(v -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_profileFragment_to_createPostFragment));
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPostClick(long postId) {
        NavDirections action = ProfileFragmentDirections.actionProfileFragmentToDetailFragment(postId);
        NavHostFragment.findNavController(this).navigate(action);
    }
}