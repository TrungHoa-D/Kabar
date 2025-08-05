package com.example.socialnetwork.data.source.local;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Converters {

    // Chuyển từ Long (timestamp) sang Date
    public static Date fromTimestamp(Long value) {
        return value != null ? new Date(value) : null;
    }

    // Chuyển từ Date sang Long (timestamp)
    public static Long dateToTimestamp(Date date) {
        return date != null ? date.getTime() : null;
    }

    // Lấy chuỗi ngày hiện tại định dạng yyyy-MM-dd
    public static String getCurrentDate(Date currentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(currentDate);
    }
}
