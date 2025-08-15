package com.example.socialnetwork.ui.main.search.author;

// File: Author.java
public class Author {
    private String name;
    private String followerCount;
    private int logoResource; // Use resource ID for dummy data

    public Author(String name, String followerCount, int logoResource) {
        this.name = name;
        this.followerCount = followerCount;
        this.logoResource = logoResource;
    }

    public String getName() {
        return name;
    }

    public String getFollowerCount() {
        return followerCount;
    }

    public int getLogoResource() {
        return logoResource;
    }
}