package com.example.socialnetwork.ui.main.notification;

import androidx.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialnetwork.ui.main.notification.NotificationItem;

import java.util.ArrayList;
import java.util.List;

public class NotificationViewModel extends ViewModel {

    // Sử dụng MutableLiveData để có thể thay đổi dữ liệu bên trong ViewModel
    private final MutableLiveData<List<NotificationItem>> _notifications = new MutableLiveData<>();

    // Expose một LiveData không thể thay đổi ra bên ngoài để Fragment chỉ có thể quan sát
    public LiveData<List<NotificationItem>> getNotifications() {
        return _notifications;
    }

    // Constructor sẽ được gọi khi ViewModel được tạo lần đầu tiên
    public NotificationViewModel() {
        loadNotifications();
    }

    // Phương thức để tải dữ liệu (hiện tại là dữ liệu giả)
    private void loadNotifications() {
        List<NotificationItem> mockList = new ArrayList<>();

        // --- Dữ liệu giả cho các thông báo ---

        // Today, April 22
        mockList.add(new NotificationItem(
                "1",
                NotificationItem.NotificationType.NEWS_POST,
                "https://placehold.co/100x100/C83737/FFFFFF?text=B", // URL ảnh logo BBC
                "BBC News has posted new europe news “Ukraine's President Zele...”",
                "15m ago"
        ));
        mockList.add(new NotificationItem(
                "2",
                NotificationItem.NotificationType.FOLLOW,
                "https://placehold.co/100x100/E5B8B7/333333?text=M", // URL ảnh avatar
                "Modelyn Saris is now following you",
                "1h ago"
        ));
        mockList.add(new NotificationItem(
                "3",
                NotificationItem.NotificationType.COMMENT,
                "https://placehold.co/100x100/A98A70/FFFFFF?text=O", // URL ảnh avatar
                "Omar Merditz comment to your news “Minting Your First NFT: A... ”",
                "1h ago"
        ));

        // Yesterday, April 21
        mockList.add(new NotificationItem(
                "4",
                NotificationItem.NotificationType.FOLLOW,
                "https://placehold.co/100x100/E5B8B7/333333?text=M",
                "Marley Botosh is now following you",
                "1 Day ago"
        ));
        mockList.add(new NotificationItem(
                "5",
                NotificationItem.NotificationType.LIKE,
                "https://placehold.co/100x100/E5B8B7/333333?text=M",
                "Modelyn Saris likes your news “Minting Your First NFT: A... ”",
                "1 Day ago"
        ));
        mockList.add(new NotificationItem(
                "6",
                NotificationItem.NotificationType.NEWS_POST,
                "https://placehold.co/100x100/377EC8/FFFFFF?text=C", // URL ảnh logo CNN
                "CNN has posted new travel news “Her train broke down. Her pho...”",
                "1 Day ago"
        ));

        // Cập nhật giá trị cho LiveData, Fragment sẽ nhận được thông báo này
        _notifications.setValue(mockList);
    }
}
