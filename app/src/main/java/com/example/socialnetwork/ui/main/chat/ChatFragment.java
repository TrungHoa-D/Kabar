package com.example.socialnetwork.ui.main.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetwork.R;
import com.example.socialnetwork.data.model.dto.Message;
import com.example.socialnetwork.databinding.FragmentChatBinding;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;


public class ChatFragment extends Fragment {
    private FragmentChatBinding binding;
    private ChatViewModel viewModel;
    private ChatFragmentArgs args;
    private MessageAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            args = ChatFragmentArgs.fromBundle(getArguments());
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChatBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        setupToolbar();

        setupRecyclerView();
        binding.btnSend.setOnClickListener(v -> sendMessage());
    }

    private void setupToolbar() {
        View toolbarCustomView = LayoutInflater.from(requireContext())
                .inflate(R.layout.toolbar_chat, binding.toolbar, false);
        binding.toolbar.addView(toolbarCustomView);

        ImageView ivAvatar = toolbarCustomView.findViewById(R.id.iv_toolbar_avatar);
        TextView tvName = toolbarCustomView.findViewById(R.id.tv_toolbar_name);

        tvName.setText(args.getReceiverName());
        Glide.with(this)
                .load(args.getReceiverAvatarUrl())
                .placeholder(R.drawable.ic_launcher_background).error(R.drawable.ic_launcher_background).into(ivAvatar);

        binding.toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        binding.toolbar.setNavigationOnClickListener(v -> {
            NavHostFragment.findNavController(this).navigateUp();
        });
    }

    private void sendMessage() {
        String messageText = binding.etMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            viewModel.sendMessage(args.getReceiverId(), messageText);
            binding.etMessage.setText("");
        }
    }

    private void setupRecyclerView() {
        Query query = viewModel.getMessagesQuery(args.getReceiverId());
        FirestoreRecyclerOptions<Message> options = new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(query, Message.class).build();
        adapter = new MessageAdapter(options);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        binding.rvMessages.setLayoutManager(layoutManager);
        binding.rvMessages.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                binding.rvMessages.scrollToPosition(adapter.getItemCount() - 1);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) adapter.stopListening();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}