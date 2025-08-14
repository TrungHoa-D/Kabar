    package com.example.socialnetwork.ui.main.search.author;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialnetwork.R;

import java.util.ArrayList;

    public class AuthorSearchFragment extends Fragment {

    private AuthorSearchViewModel mViewModel;
    private RecyclerView rvAuthors;
    private AuthorAdapter adapter;

    public static AuthorSearchFragment newInstance() {
        return new AuthorSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_search, container, false);
        rvAuthors = view.findViewById(R.id.rv_authors);
        rvAuthors.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AuthorAdapter(new ArrayList<>());
        rvAuthors.setAdapter(adapter);
        return view;
    }
    public void OnViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize the ViewModel
        // Khởi tạo ViewModel
        mViewModel = new ViewModelProvider(this).get(AuthorSearchViewModel.class);

        // Quan sát LiveData từ ViewModel
        mViewModel.getAuthors().observe(getViewLifecycleOwner(), authorList -> {
            // Cập nhật dữ liệu cho adapter khi có thay đổi
            adapter.setAuthors(authorList);
        });

        // Gọi hàm để ViewModel tải dữ liệu tác giả
        mViewModel.loadAuthors();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthorSearchViewModel.class);
        // TODO: Use the ViewModel
    }

}