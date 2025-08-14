package com.example.socialnetwork.ui.main.search.author;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Arrays;
import java.util.List;

public class AuthorSearchViewModel extends ViewModel {
    private final MutableLiveData<List<String>> authors = new MutableLiveData<>();

    public LiveData<List<String>> getAuthors() {
        return authors;
    }

    // Phương thức để tải dữ liệu tác giả
    public void loadAuthors() {
        List<String> dummyAuthors = Arrays.asList("BBC News", "CNN", "Vox", "USA Today", "CNBC");
        authors.setValue(dummyAuthors);
    }
}