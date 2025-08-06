package com.example.socialnetwork.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import java.util.function.Function;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {

    private VB binding;
    private final Function<LayoutInflater, VB> bindingInflater;

    protected abstract BaseViewModel getViewModel();

    // Sử dụng lazy trong Kotlin, Java không có. Tạo getter cho dialog
    protected Dialog getLoadingDialog() {
        if (getContext() != null) {
            return new Dialog(getContext());
        }
        return null;
    }

    public BaseFragment(Function<LayoutInflater, VB> bindingInflater) {
        this.bindingInflater = bindingInflater;
    }

    protected VB getBinding() {
        return binding;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = bindingInflater.apply(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getViewModel().getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            Dialog loadingDialog = getLoadingDialog();
            if (isLoading) {
                if (loadingDialog != null && !loadingDialog.isShowing()) {
                    // Logic hiển thị dialog loading
                }
            } else {
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
            }
        });

        bindData();
        observeData();
        setOnClick();
    }

    protected abstract void initData();

    protected abstract void bindData();

    protected abstract void observeData();

    protected abstract void setOnClick();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}