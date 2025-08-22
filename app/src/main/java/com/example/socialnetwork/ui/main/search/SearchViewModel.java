package com.example.socialnetwork.ui.main.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SearchViewModel extends ViewModel {
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();

    public LiveData<String> getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }
}