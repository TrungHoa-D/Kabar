package com.example.socialnetwork.ui.auth.otp;

public class OTPState {
    public final boolean isLoading;
    public final boolean isVerificationSuccessful;
    public final String error;
    public final long timerSeconds;
    public final boolean isTimerFinished;

    public OTPState(boolean isLoading, boolean isVerificationSuccessful, String error, long timerSeconds, boolean isTimerFinished) {
        this.isLoading = isLoading;
        this.isVerificationSuccessful = isVerificationSuccessful;
        this.error = error;
        this.timerSeconds = timerSeconds;
        this.isTimerFinished = isTimerFinished;
    }
}