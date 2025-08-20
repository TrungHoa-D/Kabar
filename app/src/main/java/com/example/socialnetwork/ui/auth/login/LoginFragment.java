package com.example.socialnetwork.ui.auth.login;

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

import com.example.socialnetwork.R;
import com.example.socialnetwork.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(LoginViewModel.class);

        setupClickListeners();
        observeState();
    }

    private void setupClickListeners() {
        binding.btnLogin.setOnClickListener(v -> {
            String emailOrPhone = binding.etUsername.getText().toString().trim();
            String password = binding.etPassword.getText().toString();
            viewModel.login(emailOrPhone, password);
        });

        binding.tvSignUp.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_signUpFragment);
        });

        binding.tvForgotPassword.setOnClickListener(v ->{
            NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
        });
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);
            binding.btnLogin.setEnabled(!state.isLoading);
            binding.btnLogin.setText(state.isLoading ? "" : "Login");

            if (state.error != null) {
                Toast.makeText(getContext(), state.error, Toast.LENGTH_SHORT).show();
            }

            if (state.isLoginSuccessful) {
                Toast.makeText(getContext(), "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                NavHostFragment.findNavController(this).navigate(R.id.action_loginFragment_to_homeFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}