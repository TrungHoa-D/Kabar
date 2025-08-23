package com.example.socialnetwork.ui.main.message;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.socialnetwork.databinding.FragmentMessageBinding;
import com.google.android.material.snackbar.Snackbar;

public class MessageFragment extends Fragment {
    private FragmentMessageBinding binding;
    private MessageViewModel viewModel;
    private UserAdapter userAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMessageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MessageViewModel.class);
        setupRecyclerView();
        observeState();
        viewModel.loadUsers();
    }

    private void setupRecyclerView() {
        binding.rvUsers.setLayoutManager(new LinearLayoutManager(getContext()));
        userAdapter = new UserAdapter();
        binding.rvUsers.setAdapter(userAdapter);
        userAdapter.setOnUserClickListener(user -> {
            NavDirections action = MessageFragmentDirections.actionMessageFragmentToChatFragment(
                    user.getId(),
                    user.getFullName(),
                    user.getAvatarUrl()
            );
            NavHostFragment.findNavController(this).navigate(action);
        });
    }

    private void observeState() {
        viewModel.state.observe(getViewLifecycleOwner(), state -> {
            binding.progressBar.setVisibility(state.isLoading ? View.VISIBLE : View.GONE);
            if (state.users != null) {
                userAdapter.submitList(state.users);
            }
            if (state.error != null) {
                Snackbar.make(binding.getRoot(), state.error, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}