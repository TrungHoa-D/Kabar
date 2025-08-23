package com.example.socialnetwork.data.source.network;
import com.example.socialnetwork.utils.constant.APIConstant;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    // Instance cho các request không yêu cầu xác thực (ví dụ: login, register)
    private static volatile Retrofit publicRetrofitInstance;
    // Instance cho các request yêu cầu xác thực (sau khi đã có token)
    private static volatile Retrofit authenticatedRetrofitInstance;

    // Biến để lưu token hiện tại được sử dụng cho authenticatedRetrofitInstance
    private static String currentAuthToken = null;

    private RetrofitClient() {
    }

    // Phương thức tạo OkHttpClient cơ bản (chỉ có logging, không có auth)
    private static OkHttpClient provideBaseOkHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY); // Log request/response body

        return new OkHttpClient.Builder()
                .connectTimeout(APIConstant.TimeOut.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    // Phương thức tạo OkHttpClient với AuthInterceptor
    private static OkHttpClient provideAuthenticatedOkHttpClient(String token) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .connectTimeout(APIConstant.TimeOut.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new AuthInterceptor(token)) // Thêm AuthInterceptor với token
                .build();
    }

    // Phương thức lấy ApiService cho các request công khai (không cần token)
    public static ApiService getPublicApiService() {
        if (publicRetrofitInstance == null) {
            synchronized (RetrofitClient.class) {
                if (publicRetrofitInstance == null) {
                    publicRetrofitInstance = new Retrofit.Builder()
                            .baseUrl(APIConstant.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(provideBaseOkHttpClient()) // Sử dụng OkHttpClient không có AuthInterceptor
                            .build();
                }
            }
        }
        return publicRetrofitInstance.create(ApiService.class);
    }

    // Phương thức lấy ApiService cho các request đã xác thực (cần token)
// Phương thức lấy ApiService cho các request đã xác thực (cần token)
    public static ApiService getAuthenticatedApiService(String token) {
        if (authenticatedRetrofitInstance == null || !token.equals(currentAuthToken)) {
            // Sửa lỗi ở đây: Sử dụng tên lớp chính xác
            synchronized (RetrofitClient.class) {
                if (authenticatedRetrofitInstance == null || !token.equals(currentAuthToken)) {
                    currentAuthToken = token; // Cập nhật token hiện tại
                    authenticatedRetrofitInstance = new Retrofit.Builder()
                            .baseUrl(APIConstant.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(provideAuthenticatedOkHttpClient(token)) // Sử dụng OkHttpClient có AuthInterceptor
                            .build();
                }
            }
        }
        return authenticatedRetrofitInstance.create(ApiService.class);
    }
}