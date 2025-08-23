package com.example.socialnetwork.ui.main.chat;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import com.example.socialnetwork.data.model.dto.Message;
import com.example.socialnetwork.data.source.local.TokenManager;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChatViewModel extends AndroidViewModel {
    private final FirebaseFirestore firestore;
    private final TokenManager tokenManager;

    public ChatViewModel(@NonNull Application application) {
        super(application);
        this.firestore = FirebaseFirestore.getInstance();
        this.tokenManager = new TokenManager(application);
    }

    private String getChatRoomId(String receiverId) {
        String currentUserId = tokenManager.getCurrentUserId();
        List<String> participants = Arrays.asList(currentUserId, receiverId);
        Collections.sort(participants);
        return participants.get(0) + "_" + participants.get(1);
    }

    public void sendMessage(String receiverId, String messageText) {
        if (messageText == null || messageText.trim().isEmpty()) {
            return;
        }
        String currentUserId = tokenManager.getCurrentUserId();
        String chatRoomId = getChatRoomId(receiverId);
        Message message = new Message(currentUserId, receiverId, messageText.trim());
        firestore.collection("chats")
                .document(chatRoomId)
                .collection("messages")
                .add(message);
    }

    public Query getMessagesQuery(String receiverId) {
        String chatRoomId = getChatRoomId(receiverId);
        return firestore.collection("chats")
                .document(chatRoomId)
                .collection("messages")
                .orderBy("timestamp", Query.Direction.ASCENDING);
    }
}