package com.example.socialnetwork.ui.auth.resetPassword;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.FragmentResetPasswordBinding;

public class ResetPasswordFragment extends Fragment {

    private ResetPasswordViewModel mViewModel;
    private FragmentResetPasswordBinding binding;

    public static ResetPasswordFragment newInstance() {
        return new ResetPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentResetPasswordBinding.inflate(inflater, container, false);
        binding.btnContinue.setOnClickListener(v -> {
            String newPass = binding.etNewPassword.getText() != null
                    ? binding.etNewPassword.getText().toString().trim() : "";
            String confirmPass = binding.etConfirmPassword.getText() != null
                    ? binding.etConfirmPassword.getText().toString().trim() : "";

            if (newPass.isEmpty() || confirmPass.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPass.equals(confirmPass)) {
                Toast.makeText(requireContext(), "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Điều hướng sang ResetPasswordSuccessFragment
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_resetPasswordFragment_to_resetPasswordSuccessFragment);
        });

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        // TODO: Use the ViewModel
    }

}