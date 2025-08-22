package com.example.socialnetwork.ui.auth.otp;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.navigation.fragment.NavHostFragment;

import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.FragmentOTPBinding;
import com.google.android.material.snackbar.Snackbar;

public class OTPFragment extends Fragment {

    private FragmentOTPBinding binding;
    private OTPViewModel viewModel;

    // Giả sử bạn lấy email từ arguments
    private String email = "example@email.com";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOTPBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(OTPViewModel.class);

        // Hiển thị email hoặc số điện thoại
        // binding.tvEmailOrPhone.setText(email);

        setupInputListeners();
        setupClickListeners();
        observeState();

        viewModel.startOtpTimer();
    }

    private void setupInputListeners() {
        // Gắn TextWatcher cho từng ô OTP để tự động chuyển focus
        EditText[] otpFields = {binding.etOtp1, binding.etOtp2, binding.etOtp3, binding.etOtp4, binding.etOtp5, binding.etOtp6};
        for (int i = 0; i < otpFields.length; i++) {
            final int index = i;
            otpFields[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.length() == 1 && index < otpFields.length - 1) {
                        otpFields[index + 1].requestFocus();
                    } else if (s.length() == 0 && index > 0) {
                        otpFields[index - 1].requestFocus();
                    }
                }
            });
        }
    }

    private void setupClickListeners() {
        binding.btnBack.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_OTPFragment_to_forgotPasswordFragment);
        });

        binding.btnVerify.setOnClickListener(v -> {
            String otp = getOtpFromFields();
            viewModel.verifyOtp(otp, email);
        });

        binding.tvResendOtp.setOnClickListener(v -> {
            viewModel.resendOtp();
        });
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            // Cập nhật giao diện dựa trên trạng thái
            binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);
            binding.btnVerify.setEnabled(!state.isLoading);
            binding.tvResendOtp.setEnabled(state.isTimerFinished);
            binding.tvTimer.setVisibility(state.isTimerFinished ? View.GONE : View.VISIBLE);

            // Hiển thị bộ đếm thời gian
            binding.tvTimer.setText(String.format("00:%02d", state.timerSeconds));

            if (state.error != null) {
                Snackbar.make(binding.getRoot(), state.error, Snackbar.LENGTH_SHORT).show();
            }

            if (state.isVerificationSuccessful) {
                Snackbar.make(binding.getRoot(), "Xác minh thành công!", Snackbar.LENGTH_SHORT).show();
                // Điều hướng sang màn hình tiếp theo
                 NavHostFragment.findNavController(this).navigate(R.id.action_OTPFragment_to_resetPasswordFragment);
            }
        });
    }

    private String getOtpFromFields() {
        return binding.etOtp1.getText().toString() +
                binding.etOtp2.getText().toString() +
                binding.etOtp3.getText().toString() +
                binding.etOtp4.getText().toString() +
                binding.etOtp5.getText().toString() +
                binding.etOtp6.getText().toString();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}