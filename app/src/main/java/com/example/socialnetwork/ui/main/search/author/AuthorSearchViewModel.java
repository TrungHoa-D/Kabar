package com.example.socialnetwork.ui.main.search.author;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.socialnetwork.R;

import java.util.Arrays;
import java.util.List;

public class AuthorSearchViewModel extends ViewModel {
    private final MutableLiveData<List<Author>> authors = new MutableLiveData<>();

    public LiveData<List<Author>> getAuthors() {
        return authors;
    }

    // Phương thức để tải dữ liệu tác giả
    public void loadAuthors() {
        List<Author> dummyAuthors = Arrays.asList(
                new Author("BBC News", "1.2M Followers", R.drawable.bbc_logo),
                new Author("CNN", "956K Followers", R.drawable.cnn_logo),
                new Author("Vox", "452K Followers", R.drawable.vox),
                new Author("USA Today", "325K Followers", R.drawable.usa_today),
                new Author("CNBC", "21K Followers", R.drawable.cnbc_logo),
                new Author("CNET", "18K Followers", R.drawable.cnet_logo),
                new Author("MSN", "15K Followers", R.drawable.msn_logo)
        );
        authors.setValue(dummyAuthors);
    }
}