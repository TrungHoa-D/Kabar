package com.example.socialnetwork.ui.auth.otp;

import androidx.lifecycle.ViewModel;

// OtpViewModel.java

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OTPViewModel extends ViewModel {

    private static final long TIMER_DURATION_SECONDS = 60;
    private static final long TIMER_INTERVAL = 1000;

    private final MutableLiveData<OTPState> _state = new MutableLiveData<>(new OTPState(false, false, null, TIMER_DURATION_SECONDS, false));
    public LiveData<OTPState> state = _state;

    private CountDownTimer countDownTimer;

    public void startOtpTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(TIMER_DURATION_SECONDS * 1000, TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                _state.setValue(new OTPState(false, false, null, millisUntilFinished / 1000, false));
            }

            @Override
            public void onFinish() {
                _state.setValue(new OTPState(false, false, null, 0, true));
            }
        }.start();
    }

    public void resendOtp() {
        // Giả lập logic gửi lại OTP
        startOtpTimer();
    }

    public void verifyOtp(String otp, String email) {
        // 1. Kiểm tra OTP
        if (otp.length() != 6) {
            _state.setValue(new OTPState(false, false, "Mã OTP phải có 6 chữ số.", _state.getValue().timerSeconds, _state.getValue().isTimerFinished));
            return;
        }

        // 2. Hiển thị trạng thái loading
        _state.setValue(new OTPState(true, false, null, _state.getValue().timerSeconds, _state.getValue().isTimerFinished));

        // 3. Giả lập gọi API kiểm tra OTP
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (otp.equals("123456")) { // Giả lập OTP đúng
                _state.setValue(new OTPState(false, true, null, _state.getValue().timerSeconds, _state.getValue().isTimerFinished));

            } else {
                _state.setValue(new OTPState(false, false, "Mã OTP không đúng.", _state.getValue().timerSeconds, _state.getValue().isTimerFinished));
            }
        }, 2000);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}