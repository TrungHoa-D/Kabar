package com.example.socialnetwork.data.source.network;


import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.model.dto.TopicDto;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/api/v1/posts")
    Call<PagedResponse<PostDto>> getAllPosts(
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("/api/v1/topics")
    Call<PagedResponse<TopicDto>> getAllTopics(
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("/api/v1/posts/{topicId}/posts")
    Call<PagedResponse<PostDto>> getPostsByTopic(
            @Path("topicId") long topicId,
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("/api/v1/posts/{postId}")
    Call<PostDto> getPostById(@Path("postId") long postId);
}