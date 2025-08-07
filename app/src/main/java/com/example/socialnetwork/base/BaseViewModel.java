package com.example.socialnetwork.base;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BaseViewModel extends ViewModel {

    private final MutableLiveData<Boolean> _loading = new MutableLiveData<>(false);
    public final LiveData<Boolean> loading = _loading;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    public interface TaskCallback<T> {
        void onSuccess(T data);
        void onError(Exception exception);
    }

    public interface BackgroundTask<T> {
        DataState<T> execute() throws Exception;
    }

    protected <T> void executeTask(
            final BackgroundTask<T> request,
            final TaskCallback<T> callback,
            final boolean showLoading
    ) {
        if (showLoading) {
            showLoading();
        }

        executorService.execute(() -> {
            try {
                DataState<T> response = request.execute();
                mainHandler.post(() -> {
                    if (response instanceof DataState.Success) {
                        callback.onSuccess(((DataState.Success<T>) response).getData());
                    } else if (response instanceof DataState.Error) {
                        callback.onError(((DataState.Error<T>) response).getException());
                    }
                    if (showLoading) {
                        hideLoading();
                    }
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    callback.onError(e);
                    if (showLoading) {
                        hideLoading();
                    }
                });
            }
        });
    }

    public void showLoading() {
        _loading.postValue(true);
    }

    public void hideLoading() {
        _loading.postValue(false);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        executorService.shutdownNow();
    }
}
