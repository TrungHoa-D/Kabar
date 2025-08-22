package com.example.socialnetwork.data.model.dto;

public class BaseResponse<T> {
    private String status;
    private String message;
    private T data;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
