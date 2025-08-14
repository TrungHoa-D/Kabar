package com.example.socialnetwork.ui.main.notification;

public class NotificationItem {

    public enum NotificationType {
        NEWS_POST,
        FOLLOW,
        COMMENT,
        LIKE
    }

    public final String id;
    public final NotificationType type;
    public final String avatarUrl;
    public final String content;
    public final String timeAgo;

    public NotificationItem(String id, NotificationType type, String avatarUrl, String content, String timeAgo) {
        this.id = id;
        this.type = type;
        this.avatarUrl = avatarUrl;
        this.content = content;
        this.timeAgo = timeAgo;
    }
}
