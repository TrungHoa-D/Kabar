package com.example.socialnetwork.ui.auth.resetPassword;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.FragmentResetPasswordSuccessBinding;

public class ResetPasswordSuccessFragment extends Fragment {
    private FragmentResetPasswordSuccessBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentResetPasswordSuccessBinding.inflate(inflater, container, false);

        binding.btnBackToLogin.setOnClickListener(v -> {
            // Điều hướng về màn Login
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_resetPasswordSuccessFragment_to_loginFragment);
        });

        return binding.getRoot();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}