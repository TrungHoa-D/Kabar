package com.example.socialnetwork.base;
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public abstract class BaseAdapter<T, VB extends ViewBinding> extends RecyclerView.Adapter<BaseViewHolder<VB>> {

    private final Function<LayoutInflater, VB> bindingInflater;
    private final List<T> dataList;

    // Constructor mặc định, sử dụng ArrayList
    public BaseAdapter(Function<LayoutInflater, VB> bindingInflater) {
        this(bindingInflater, new ArrayList<>());
    }

    // Constructor với danh sách dữ liệu truyền vào
    public BaseAdapter(Function<LayoutInflater, VB> bindingInflater, List<T> dataList) {
        this.bindingInflater = bindingInflater;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public BaseViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        VB binding = bindingInflater.apply(inflater);
        return new BaseViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<VB> holder, int position) {
        T item = dataList.get(position);
        bindData(holder.getBinding(), item, position);
        onItemClick(holder.getBinding(), item, position);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public abstract void bindData(VB binding, T item, int position);

    public abstract void onItemClick(VB binding, T item, int position);

    public void setData(int position, T data) {
        if (position >= 0 && position < dataList.size()) {
            dataList.set(position, data);
            notifyItemChanged(position);
        }
    }

    public void removeData(int position) {
        if (position >= 0 && position < dataList.size()) {
            dataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setDataList(List<T> data) {
        this.dataList.clear();
        this.dataList.addAll(data);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void clearData() {
        dataList.clear();
        notifyDataSetChanged();
    }

    public List<T> getDataList() {
        return dataList;
    }
}