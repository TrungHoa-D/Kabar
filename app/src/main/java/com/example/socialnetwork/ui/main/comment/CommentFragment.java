package com.example.socialnetwork.ui.main.comment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.data.model.dto.CommentDto;
import com.example.socialnetwork.databinding.FragmentCommentBinding;

public class CommentFragment extends Fragment implements CommentAdapter.OnCommentLikeClickListener {

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
        setupClickListeners();
        observeState();

        viewModel.loadComments(postId);
    }

    private void setupClickListeners() {
        binding.btnSend.setOnClickListener(v -> {
            String content = binding.etComment.getText().toString();
            if (!content.trim().isEmpty()) {
                viewModel.postComment(postId, content);
                binding.etComment.setText("");
                hideKeyboard();
            } else {
                Toast.makeText(getContext(), "Please enter a comment", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupToolbar() {
        binding.toolbar.setNavigationOnClickListener(v -> NavHostFragment.findNavController(this).navigateUp());
    }

    private void setupRecyclerView() {
        adapter = new CommentAdapter();
        adapter.setOnLikeClickListener(this);
        binding.rvComments.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvComments.setAdapter(adapter);
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            if (state.comments != null) {
                adapter.submitList(state.comments);
            }
            if (state.likeStatusMap != null) {
                adapter.setLikeStatusMap(state.likeStatusMap);
            }
            if (state.error != null) {
                Toast.makeText(getContext(), state.error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void hideKeyboard() {
        View view = requireActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onLikeClick(CommentDto comment) {
        CommentViewModel.CommentState currentState = viewModel.state.getValue();
        if (currentState != null && currentState.likeStatusMap != null) {
            boolean isCurrentlyLiked = currentState.likeStatusMap.getOrDefault(comment.getId(), false);
            if (isCurrentlyLiked) {
                viewModel.unlikeComment(comment.getId());
            } else {
                viewModel.likeComment(comment.getId());
            }
        }
    }
}