package com.example.socialnetwork.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtils {

    public static String getTimeAgo(String localTimestamp) {
        if (localTimestamp == null || localTimestamp.isEmpty()) {
            return null;
        }
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(localTimestamp);

            ZonedDateTime postTime = localDateTime.atZone(ZoneId.systemDefault());

            ZonedDateTime now = ZonedDateTime.now();

            long seconds = ChronoUnit.SECONDS.between(postTime, now);
            if (seconds < 60) return seconds + " giây trước";

            long minutes = ChronoUnit.MINUTES.between(postTime, now);
            if (minutes < 60) return minutes + " phút trước";

            long hours = ChronoUnit.HOURS.between(postTime, now);
            if (hours < 24) return hours + " giờ trước";

            long days = ChronoUnit.DAYS.between(postTime, now);
            if (days < 7) return days + " ngày trước";

            long weeks = ChronoUnit.WEEKS.between(postTime, now);
            return weeks + " tuần trước";

        } catch (Exception e) {
            e.printStackTrace();
            return localTimestamp.substring(0, 10);
        }
    }
}