package com.example.socialnetwork.ui.auth.forgotPassword;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.os.Handler;
import android.os.Looper;

public class ForgotPasswordViewModel extends ViewModel {
    private final MutableLiveData<ForgotPasswordState> _state = new MutableLiveData<>(new ForgotPasswordState(false, false, null));
    public LiveData<ForgotPasswordState> state = _state;

    public void sendOtp(String email) {
        // 1. Kiểm tra trường rỗng
        if (email.isEmpty()) {
            _state.setValue(new ForgotPasswordState(false, false, "Vui lòng nhập địa chỉ email."));
            return;
        }

        // 2. Kiểm tra định dạng email bằng Patterns.EMAIL_ADDRESS
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _state.setValue(new ForgotPasswordState(false, false, "Địa chỉ email không hợp lệ."));
            return;
        }

        // 3. Nếu dữ liệu hợp lệ, hiển thị trạng thái loading
        _state.setValue(new ForgotPasswordState(true, false, null));

        // 4. Giả lập gọi API
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            boolean success = true; // Kết quả giả lập từ API
            if (success) {
                _state.setValue(new ForgotPasswordState(false, true, null));
            } else {
                _state.setValue(new ForgotPasswordState(false, false, "Không thể gửi OTP. Vui lòng thử lại."));
            }
        }, 2000);
    }
}