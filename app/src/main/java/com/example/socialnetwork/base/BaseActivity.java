package com.example.socialnetwork.base;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import java.util.function.Function;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    private VB binding;
    private final Function<LayoutInflater, VB> bindingInflater;

    public BaseActivity(Function<LayoutInflater, VB> bindingInflater) {
        this.bindingInflater = bindingInflater;
    }

    protected VB getBinding() {
        return binding;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = bindingInflater.apply(getLayoutInflater());
        setContentView(binding.getRoot());

        initData();
        bindData();
        setOnClick();
    }

    protected abstract void initData();
    protected abstract void bindData();
    protected abstract void setOnClick();

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev != null && ev.getAction() == MotionEvent.ACTION_DOWN) {
            android.view.View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int) ev.getRawX(), (int) ev.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}