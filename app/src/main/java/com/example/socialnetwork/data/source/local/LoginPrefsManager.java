package com.example.socialnetwork.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

public class LoginPrefsManager {
    private static final String PREFS_NAME = "login_prefs";
    private static final String KEY_USERNAME = "remembered_username";
    private final SharedPreferences prefs;

    public LoginPrefsManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveUsername(String username) {
        prefs.edit().putString(KEY_USERNAME, username).apply();
    }

    public String getRememberedUsername() {
        return prefs.getString(KEY_USERNAME, null);
    }

    public void clearRememberedUsername() {
        prefs.edit().remove(KEY_USERNAME).apply();
    }
}