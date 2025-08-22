package com.example.socialnetwork.ui.auth.login;

import androidx.annotation.Nullable;

public class LoginState {
    public final boolean isLoading;
    public final boolean isLoginSuccessful;
    @Nullable
    public final String error;

    public LoginState(boolean isLoading, boolean isLoginSuccessful, @Nullable String error) {
        this.isLoading = isLoading;
        this.isLoginSuccessful = isLoginSuccessful;
        this.error = error;
    }
}
