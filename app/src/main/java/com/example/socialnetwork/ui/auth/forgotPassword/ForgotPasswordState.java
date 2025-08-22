package com.example.socialnetwork.ui.auth.forgotPassword;



public class ForgotPasswordState {
    public final boolean isLoading;
    public final boolean isEmailSent;
    public final String error;

    public ForgotPasswordState(boolean isLoading, boolean isEmailSent, String error) {
        this.isLoading = isLoading;
        this.isEmailSent = isEmailSent;
        this.error = error;
    }
}