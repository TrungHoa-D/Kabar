package com.example.socialnetwork.data.source.network;


import com.example.socialnetwork.data.model.dto.AuthorDto;
import com.example.socialnetwork.data.model.dto.CommentDto;
import com.example.socialnetwork.data.model.dto.PagedResponse;
import com.example.socialnetwork.data.model.dto.PostDto;
import com.example.socialnetwork.data.model.dto.TopicDto;
import com.example.socialnetwork.data.model.dto.UserDto;

import retrofit2.Call;
import retrofit2.http.*;

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

    @GET("/api/v1/users/getAll")
    Call<PagedResponse<UserDto>> getAllUsers(
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

    @GET("/api/v1/users/me")
    Call<UserDto> getCurrentUser();

    @GET("/api/v1/users/{userId}/posts")
    Call<PagedResponse<PostDto>> getPostsByUser(
            @Path("userId") String userId,
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("/api/v1/posts/{postId}/likes")
    Call<PagedResponse<AuthorDto>> getPostLikes(
            @Path("postId") long postId,
            @Query("page") int page,
            @Query("size") int size
    );

    @POST("/api/v1/posts/{postId}/like")
    Call<Void> likePost(@Path("postId") long postId);

    @DELETE("/api/v1/posts/{postId}/like")
    Call<Void> unlikePost(@Path("postId") long postId);

    @GET("/api/v1/posts/{postId}/comments")
    Call<PagedResponse<CommentDto>> getCommentsForPost(
            @Path("postId") long postId,
            @Query("page") int page,
            @Query("size") int size
    );
}