package com.example.socialnetwork.base;

import java.util.concurrent.Callable;
public class BaseRepository {
    public <T> Object getResult(Callable<T> request) {

        try {
            T result = request.call();
            return new DataState.Success<>(result);
        } catch (Exception e) {
            return new DataState.Error<>(e);
        }
    }
}