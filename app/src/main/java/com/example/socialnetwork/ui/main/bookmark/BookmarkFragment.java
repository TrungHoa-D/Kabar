package com.example.socialnetwork.ui.main.bookmark;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialnetwork.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.ui.main.home.trending.Article;
import com.example.socialnetwork.databinding.FragmentBookmarkBinding;

import java.util.ArrayList;
import java.util.List;

public class BookmarkFragment extends Fragment {

    private FragmentBookmarkBinding binding;
    private BookmarkViewModel viewModel;
    private BookmarkAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentBookmarkBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BookmarkViewModel.class);

        binding.rvBookmarkArticles.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new BookmarkAdapter(new ArrayList<>());
        binding.rvBookmarkArticles.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvBookmarkArticles.setAdapter(adapter);

        viewModel.getBookmarkedArticles().observe(getViewLifecycleOwner(), articles -> {
            adapter.setArticles(articles);
        });

        // Trigger loading the data
        viewModel.loadBookmarkedArticles();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}