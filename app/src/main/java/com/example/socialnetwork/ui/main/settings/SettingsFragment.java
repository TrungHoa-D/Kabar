package com.example.socialnetwork.ui.main.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.FragmentSettingsBinding;

public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;
    private SettingsViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(SettingsViewModel.class);

        setupListeners();
        observeState();
    }

    private void setupListeners() {
        // Sự kiện click cho nút Back
        binding.toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).popBackStack());

        // Sự kiện click cho nút Logout
        binding.tvLogout.setOnClickListener(v -> {
            viewModel.logout();
        });
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state.error != null) {
                Toast.makeText(getContext(), state.error, Toast.LENGTH_SHORT).show();
            }

            if (state.isLogoutSuccessful) {
                NavOptions navOptions = new NavOptions.Builder()
                        .setPopUpTo(R.id.nav, true)
                        .build();
                NavHostFragment.findNavController(this).navigate(R.id.loginFragment, null, navOptions);
                Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}