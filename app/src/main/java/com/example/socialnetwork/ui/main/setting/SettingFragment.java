package com.example.socialnetwork.ui.main.setting;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.FragmentSettingBinding;

public class SettingFragment extends Fragment {

    private FragmentSettingBinding binding;
    private SettingViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(SettingViewModel.class);

//        // Lấy trạng thái Dark Mode đã lưu từ ViewModel và thiết lập cho Switch
//        viewModel.isDarkModeEnabled().observe(getViewLifecycleOwner(), isEnabled -> {
//            binding.switchDarkMode.setChecked(isEnabled);
//        });

        // Xử lý sự kiện click cho các mục
        binding.ivBack.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        binding.llNotificationSettings.setOnClickListener(v -> {
            // Điều hướng đến màn hình Notification Settings
        });

        binding.llLogout.setOnClickListener(v->{
            // 1. Gọi phương thức logout từ ViewModel để xóa token
            viewModel.logout();

            // 2. Điều hướng về màn hình đăng nhập và xóa back stack
            NavHostFragment.findNavController(this).navigate(R.id.action_settingFragment_to_loginFragment,
                    null,
                    new NavOptions.Builder()
                            .setPopUpTo(R.id.nav, true) // Xóa tất cả các fragment khỏi back stack
                            .build());
        });


        // Xử lý sự kiện thay đổi trạng thái của Switch Dark Mode
//        binding.switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
//            // Lưu trạng thái mới vào ViewModel
//            viewModel.setDarkMode(isChecked);
//            // Áp dụng chủ đề Dark/Light
//            if (isChecked) {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
//            } else {
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}