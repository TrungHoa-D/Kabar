package com.example.socialnetwork.ui.main.comment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.databinding.FragmentCommentBinding;

public class CommentFragment extends Fragment {

    private FragmentCommentBinding binding;
    private CommentViewModel viewModel;
    private CommentAdapter adapter;
    private long postId;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            postId = CommentFragmentArgs.fromBundle(getArguments()).getPostId();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCommentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(CommentViewModel.class);

        setupToolbar();
        setupRecyclerView();
        observeState();

        viewModel.loadComments(postId);
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());
    }

    private void setupRecyclerView() {
        adapter = new CommentAdapter();
        binding.rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvComments.setAdapter(adapter);
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state.comments != null) {
                adapter.submitList(state.comments);
            }
            // Xử lý isLoading và error nếu cần
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}