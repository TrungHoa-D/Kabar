package com.example.socialnetwork.ui.main.profile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.FragmentProfileBinding;
import com.example.socialnetwork.ui.main.home.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    private FragmentProfileBinding binding;
    private ViewPager2 articlesViewPager;
//    private ProfileAdapter profileAdapter;
    private final String[] tabTitles = {
            "News", "Recent"
    };
    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(this, tabTitles.length);
        binding.viewPagerProfile.setAdapter(viewPagerAdapter);
        new TabLayoutMediator(binding.tabLayoutProfile, binding.viewPagerProfile,
                (tab, position) -> tab.setText(tabTitles[position])).attach();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

//        profileAdapter = new ProfileAdapter(this);
//        binding.viewPagerProfile.setAdapter(profileAdapter);

        new TabLayoutMediator(binding.tabLayoutProfile, binding.viewPagerProfile,
                (tab, position) -> tab.setText(tabTitles[position])).attach();
        mViewModel.getUserProfile().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.tvProfileName.setText(user.getFullName());
                binding.tvProfileQuote.setText(user.getBio());
            }
        });

        // Tải dữ liệu ban đầu
        mViewModel.loadUserProfile();
        mViewModel.loadUserNews();
        mViewModel.loadUserRecent();


        // Xử lý sự kiện click
        binding.btnFollowing.setOnClickListener(v -> {
            // Logic cho nút "Following" hoặc "Edit Profile"
        });

        binding.btnWebsite.setOnClickListener(v -> {
            // Logic cho nút "Website"
        });

        binding.fabAddPost.setOnClickListener(v -> {
            // Logic cho nút thêm bài viết
        });
        binding.ivSettings.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_profileFragment_to_settingFragment);
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}