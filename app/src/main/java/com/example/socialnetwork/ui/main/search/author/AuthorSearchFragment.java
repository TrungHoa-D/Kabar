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
import com.example.socialnetwork.ui.main.search.SearchViewModel;

public class AuthorSearchFragment extends Fragment {

    private AuthorSearchViewModel authorViewModel;
    private SearchViewModel sharedViewModel;
    private FragmentAuthorSearchBinding binding;
    private AuthorAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthorSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        authorViewModel = new ViewModelProvider(this).get(AuthorSearchViewModel.class);
        sharedViewModel = new ViewModelProvider(requireParentFragment()).get(SearchViewModel.class);

        binding.rvAuthors.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AuthorAdapter();
        binding.rvAuthors.setAdapter(adapter);

        authorViewModel.getFilteredAuthors().observe(getViewLifecycleOwner(), authorList -> {
            adapter.submitList(authorList);
        });

        sharedViewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            authorViewModel.filter(query);
        });
    }
}