package com.example.socialnetwork.base;

public abstract class DataState<T> {

    // Constructor private để không thể tạo instance trực tiếp
    private DataState() {}

    public static final class Success<T> extends DataState<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        // Có thể thêm hashCode, equals, toString nếu cần
    }

    public static final class Error<T> extends DataState<T> {
        private final Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception getException() {
            return exception;
        }

        // Có thể thêm hashCode, equals, toString nếu cần
    }
}