package com.example.socialnetwork.ui.auth.signup;

import android.os.Handler;
import android.os.Looper;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {

    private final MutableLiveData<SignUpState> _state = new MutableLiveData<>(new SignUpState(false, false, null));
    public LiveData<SignUpState> state = _state;

    public void signUp(String username, String email, String pass) {
        _state.setValue(new SignUpState(true, false, null));
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (username.trim().isEmpty() || email.trim().isEmpty() || pass.isEmpty()) {
                _state.setValue(new SignUpState(false, false, "Please fill in all fields"));
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _state.setValue(new SignUpState(false, false, "Invalid email address"));
            } else if (pass.length() < 6) {
                _state.setValue(new SignUpState(false, false, "Password must be at least 6 characters"));
            } else {
                _state.setValue(new SignUpState(false, true, null));
            }
        }, 2000);
    }
}
