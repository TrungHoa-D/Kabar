package com.example.socialnetwork.utils.constant;

public final class APIConstant {
    public static final String BASE_URL = "http://43.206.215.49:8080/";

    private APIConstant() {

    }

    public static class TimeOut {
        public static final int CONNECT_TIME_OUT = 30;
        public static final int READ_TIME_OUT = 30;
        public static final int WRITE_TIME_OUT = 30;
    }



}
