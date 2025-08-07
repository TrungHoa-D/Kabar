package com.example.socialnetwork.ui.auth.signup;

import androidx.annotation.Nullable;

public class SignUpState {
    public final boolean isLoading;
    public final boolean isSignUpSuccessful;
    @Nullable
    public final String error;

    public SignUpState(boolean isLoading, boolean isSignUpSuccessful, @Nullable String error) {
        this.isLoading = isLoading;
        this.isSignUpSuccessful = isSignUpSuccessful;
        this.error = error;
    }
}
