package com.example.socialnetwork.ui.main.home.model;

import java.util.Objects;

public class NewsCategory {
    public final String name;
    public boolean isSelected;

    public NewsCategory(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NewsCategory that = (NewsCategory) o;
        return isSelected == that.isSelected && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isSelected);
    }
}
