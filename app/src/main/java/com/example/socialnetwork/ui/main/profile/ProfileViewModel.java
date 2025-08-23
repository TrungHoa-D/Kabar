package com.example.socialnetwork.ui.main.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialnetwork.ui.auth.dataclass.User;
import com.example.socialnetwork.ui.main.home.model.NewsArticle;

import java.util.ArrayList;
import java.util.List;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<NewsArticle>> newsArticleLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<NewsArticle>> recentArticleLiveData = new MutableLiveData<>();

    public LiveData<User> getUserProfile() {
        return userLiveData;
    }

    public LiveData<List<NewsArticle>> getUserNews() {
        return newsArticleLiveData;
    }

    public LiveData<List<NewsArticle>> getUserRecent() {
        return recentArticleLiveData;
    }

    public void loadUserProfile() {
        // Giả lập dữ liệu người dùng
        User dummyUser = new User();
        dummyUser.setFullName("Wilson Franci");
        dummyUser.setBio("Lorem Ipsum is simply dummy text of the printing and typesetting industry.");
        dummyUser.setAvatarUrl("url_to_wilson_avatar"); // Thay bằng URL ảnh thực tế

        // Giả lập các chỉ số
        // Bạn sẽ cần tính toán các con số này từ dữ liệu thực tế
        userLiveData.setValue(dummyUser);
    }

    public void loadUserNews() {
        // Giả lập dữ liệu bài viết
        List<NewsArticle> newsList = new ArrayList<>();
        // Thêm dữ liệu bài viết của người dùng
        newsArticleLiveData.setValue(newsList);
    }

    public void loadUserRecent() {
        // Giả lập dữ liệu hoạt động gần đây
        List<NewsArticle> recentList = new ArrayList<>();
        // Thêm dữ liệu hoạt động gần đây
        recentArticleLiveData.setValue(recentList);
    }

}


