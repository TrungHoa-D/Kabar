package com.example.socialnetwork.utils.constant;

public final class APIConstant {
    public static final String BASE_URL = "http://43.206.215.49:8080/";


    private APIConstant() {

    }

    public static class TimeOut {
        public static final long CONNECT_TIME_OUT = 30L;
    }


    public static class Endpoint {
        public static final String LOGIN = "api/v1/auth/login";
        public static final String REGISTER = "api/v1/register";
        public static final String USER_PROFILE = "api/v1/users/me";

    }
}
