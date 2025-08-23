package com.example.socialnetwork.ui.main.chat;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.socialnetwork.data.model.dto.Message;
import com.example.socialnetwork.databinding.ItemMessageReceivedBinding;
import com.example.socialnetwork.databinding.ItemMessageSentBinding;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MessageAdapter extends FirestoreRecyclerAdapter<Message, RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_SENT = 1;
    private static final int VIEW_TYPE_RECEIVED = 2;
    private final String currentUserId;

    public MessageAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
        this.currentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).getSenderId().equals(currentUserId)) {
            return VIEW_TYPE_SENT;
        } else {
            return VIEW_TYPE_RECEIVED;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_SENT) {
            ItemMessageSentBinding binding = ItemMessageSentBinding.inflate(inflater, parent, false);
            return new SentMessageViewHolder(binding);
        } else {
            ItemMessageReceivedBinding binding = ItemMessageReceivedBinding.inflate(inflater, parent, false);
            return new ReceivedMessageViewHolder(binding);
        }
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull Message model) {
        if (holder.getItemViewType() == VIEW_TYPE_SENT) {
            ((SentMessageViewHolder) holder).bind(model);
        } else {
            ((ReceivedMessageViewHolder) holder).bind(model);
        }
    }

    static class SentMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemMessageSentBinding binding;
        SentMessageViewHolder(ItemMessageSentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(Message message) { binding.tvMessage.setText(message.getMessage()); }
    }

    static class ReceivedMessageViewHolder extends RecyclerView.ViewHolder {
        private final ItemMessageReceivedBinding binding;
        ReceivedMessageViewHolder(ItemMessageReceivedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        void bind(Message message) { binding.tvMessage.setText(message.getMessage()); }
    }
}