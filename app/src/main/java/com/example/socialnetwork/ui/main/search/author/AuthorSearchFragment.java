package com.example.socialnetwork.ui.main.search.author;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.databinding.FragmentAuthorSearchBinding;

import java.util.ArrayList;

public class AuthorSearchFragment extends Fragment {

    private AuthorSearchViewModel mViewModel;
    private FragmentAuthorSearchBinding binding;
    private AuthorAdapter adapter;

    public static AuthorSearchFragment newInstance() {
        return new AuthorSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthorSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthorSearchViewModel.class);

        binding.rvAuthors.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new AuthorAdapter(new ArrayList<>());
        binding.rvAuthors.setAdapter(adapter);

        mViewModel.getAuthors().observe(getViewLifecycleOwner(), authorList -> {
            adapter.setAuthors(authorList);
        });

        mViewModel.loadAuthors();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AuthorSearchViewModel.class);
        // TODO: Use the ViewModel
    }

}