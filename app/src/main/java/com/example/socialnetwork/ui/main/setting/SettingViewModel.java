package com.example.socialnetwork.ui.main.setting;

import static androidx.lifecycle.AndroidViewModel_androidKt.getApplication;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

public class SettingViewModel extends AndroidViewModel {
    private SharedPreferences getSharedPreferences() {
        return getApplication().getSharedPreferences("login_prefs", Context.MODE_PRIVATE);
    }

    public SettingViewModel(Application application) {
        super(application);
    }

    public boolean isRememberMeChecked() {
        SharedPreferences prefs = getSharedPreferences();
        // Kiểm tra xem có username và password đã lưu không
        return prefs.contains("username") && prefs.contains("password");
    }
    public void logout() {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        if (isRememberMeChecked()) {
            // Nếu "Remember Me" được tick, chỉ xóa token
            editor.remove("token");
        } else {
            // Nếu không, xóa tất cả thông tin đăng nhập
            editor.clear();
        }
        editor.apply();
    }


//    public void logout() {
//        SharedPreferences.Editor editor = getSharedPreferences().edit();
//        editor.remove("token");
//        editor.apply();
//    }
}