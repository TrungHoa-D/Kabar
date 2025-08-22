package com.example.socialnetwork.utils.constant;

public final class SortType {

    /**
     * Private constructor để ngăn việc khởi tạo lớp tiện ích này.
     */
    private SortType() {}

    /**
     * Sắp xếp theo thời gian tạo mới nhất lên đầu.
     */
    public static final String NEWEST = "createdAt,desc";

    /**
     * Sắp xếp theo số lượt thích nhiều nhất lên đầu.
     */
    public static final String MOST_LIKES = "likeCount,desc";

    /**
     * Sắp xếp theo số bình luận nhiều nhất lên đầu.
     */
    public static final String MOST_COMMENTS = "commentCount,desc";
}