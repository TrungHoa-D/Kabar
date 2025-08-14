package com.example.socialnetwork.ui.auth.forgotPassword;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.FragmentForgotPasswordBinding;
import com.google.android.material.snackbar.Snackbar;

public class ForgotPasswordFragment extends Fragment {
    private FragmentForgotPasswordBinding binding;
    private ForgotPasswordViewModel viewModel;

    public static ForgotPasswordFragment newInstance() {
        return new ForgotPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding  = FragmentForgotPasswordBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        setupClickListeners();
        observeState();
    }

    private void setupClickListeners() {
        binding.btnBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_forgotPasswordFragment_to_loginFragment);
        });

        binding.btnSubmit.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            viewModel.sendOtp(email);
        });
    }
    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {

            binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);
            binding.btnSubmit.setEnabled(!state.isLoading);
            binding.btnSubmit.setText(state.isLoading ? "" : "Submit");

            if (state.error != null) {
                Snackbar.make(binding.getRoot(), state.error, Snackbar.LENGTH_SHORT).show();
            }

            if (state.isEmailSent) {
                Snackbar.make(binding.getRoot(), "Mã OTP đã được gửi đến email của bạn.", Snackbar.LENGTH_SHORT).show();

                // Chuyển sang màn hình OTP sau khi gửi thành công
                NavHostFragment.findNavController(this).navigate(R.id.action_forgotPasswordFragment_to_OTPFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}