package com.example.socialnetwork.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

public class TimeUtils {

    public static String getTimeAgo(String utcTimestamp) {
        if (utcTimestamp == null || utcTimestamp.isEmpty()) {
            return null;
        }
        try {
            LocalDateTime localDateTime = LocalDateTime.parse(utcTimestamp);

            ZonedDateTime postTime = localDateTime.atZone(ZoneId.of("UTC"));

            ZonedDateTime now = ZonedDateTime.now();

            long seconds = ChronoUnit.SECONDS.between(postTime, now);
            if (seconds < 60) return "vài giây trước";

            long minutes = ChronoUnit.MINUTES.between(postTime, now);
            if (minutes < 60) return minutes + " phút trước";

            long hours = ChronoUnit.HOURS.between(postTime, now);
            if (hours < 24) return hours + " giờ trước";

            long days = ChronoUnit.DAYS.between(postTime, now);
            if (days < 7) return days + " ngày trước";

            long weeks = ChronoUnit.WEEKS.between(postTime, now);
            if (weeks < 4) return weeks + " tuần trước";

            long months = ChronoUnit.MONTHS.between(postTime, now);
            if (months < 12) return months + " tháng trước";

            long years = ChronoUnit.YEARS.between(postTime, now);
            return years + " năm trước";

        } catch (Exception e) {
            e.printStackTrace();
            return utcTimestamp.substring(0, 10);
        }
    }
}