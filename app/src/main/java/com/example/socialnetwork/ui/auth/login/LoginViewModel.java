package com.example.socialnetwork.ui.auth.login;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import android.os.Handler;
import android.os.Looper;

public class LoginViewModel extends AndroidViewModel {

    private final MutableLiveData<LoginState> _state = new MutableLiveData<>(new LoginState(false, false, null));
    public LiveData<LoginState> state = _state;

    // Sử dụng AndroidViewModel thay vì ViewModel để có Application context
    public LoginViewModel(Application application) {
        super(application);
    }

    private SharedPreferences getSharedPreferences() {
        return getApplication().getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
    }

    // Phương thức lưu thông tin đăng nhập
    public void saveLoginInfo(String username, String password) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString("username", username);
        editor.putString("password", password);
        editor.apply();
    }

    // Phương thức kiểm tra đã lưu thông tin đăng nhập chưa
    public boolean hasSavedLoginInfo() {
        SharedPreferences prefs = getSharedPreferences();
        return prefs.contains("username") && prefs.contains("password");
    }

    // Phương thức lấy thông tin đã lưu
    public String getSavedUsername() {
        return getSharedPreferences().getString("username", "");
    }

    public String getSavedPassword() {
        return getSharedPreferences().getString("password", "");
    }

    public void login(String username, String password) {
        // ... (Giữ nguyên code validation của bạn)
        if (username.isEmpty()) {
            _state.setValue(new LoginState(false, false, "Vui lòng nhập tên đăng nhập."));
            return;
        }

        if (password.isEmpty()) {
            _state.setValue(new LoginState(false, false, "Vui lòng nhập mật khẩu."));
            return;
        }

        if (password.length() < 6) {
            _state.setValue(new LoginState(false, false, "Mật khẩu phải có ít nhất 6 ký tự."));
            return;
        }
        _state.setValue(new LoginState(true, false, null));

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            if (username.equalsIgnoreCase("test") && password.equals("123456")) {
                _state.setValue(new LoginState(false, true, null));
            } else {
                _state.setValue(new LoginState(false, false, "Tên đăng nhập hoặc mật khẩu không đúng."));
            }
        }, 2000);
    }
}
