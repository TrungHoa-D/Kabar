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
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.UserDto;
import com.example.socialnetwork.databinding.FragmentProfileBinding;
import com.example.socialnetwork.ui.main.home.adapter.ArticlesPagerAdapter; // Tái sử dụng Adapter

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private ProfileViewModel viewModel;
    private ArticlesPagerAdapter articlesAdapter; // Thêm adapter

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(ProfileViewModel.class);

        setupRecyclerView(); // Gọi hàm setup RecyclerView
        observeState();
        viewModel.loadCurrentUserProfile();
    }

    private void setupRecyclerView() {
        articlesAdapter = new ArticlesPagerAdapter(this);
        binding.rvUserPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvUserPosts.setAdapter(articlesAdapter);
        binding.ivSettings.setOnClickListener(v -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_profileFragment_to_settingsFragment));
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            // binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);

            if (state.error != null) {
                Toast.makeText(getContext(), state.error, Toast.LENGTH_SHORT).show();
            }

            if (state.user != null) {
                updateUi(state.user);
            }

            // Khi có danh sách bài viết, cập nhật cho adapter
            if (state.posts != null) {
                articlesAdapter.setArticles(state.posts);
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
}