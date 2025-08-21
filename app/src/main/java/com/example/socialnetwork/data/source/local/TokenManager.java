package com.example.socialnetwork.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenManager {
    private static final String PREFS_NAME = "auth_prefs";
    private static final String KEY_TOKEN = "auth_token";
    private static final String KEY_USER_ID = "user_id";
    private final SharedPreferences prefs;

    public TokenManager(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token) {
        prefs.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return prefs.getString(KEY_TOKEN, null);
    }

    public void saveUserId(String userId) {
        prefs.edit().putString(KEY_USER_ID, userId).apply();
    }

    public String getCurrentUserId() {
        return prefs.getString(KEY_USER_ID, null);
    }

    public void clearToken() {
        prefs.edit()
                .remove(KEY_TOKEN)
                .remove(KEY_USER_ID).apply();
    }
}