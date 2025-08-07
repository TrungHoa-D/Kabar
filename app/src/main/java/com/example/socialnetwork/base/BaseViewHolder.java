package com.example.socialnetwork.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public class BaseViewHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {
    private final VB binding;

    public BaseViewHolder(@NonNull VB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
    public VB getBinding() {
        return binding;
    }
}