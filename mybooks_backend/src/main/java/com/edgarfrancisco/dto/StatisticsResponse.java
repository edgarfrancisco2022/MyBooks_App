package com.edgarfrancisco.dto;

import java.util.List;

public class StatisticsResponse {

    private int totalBooks;
    private int totalAuthors;
    private int totalLists;
    private int totalPublishers;
    private int totalCategories;
    private int totalTags;
    private List<BookResponse> latestBooksAdded;

    public StatisticsResponse() {
    }

    public StatisticsResponse(int totalBooks, int totalAuthors, int totalLists, int totalPublishers,
                              int totalCategories, int totalTags, List<BookResponse> latestBooksAdded) {
        this.totalBooks = totalBooks;
        this.totalAuthors = totalAuthors;
        this.totalLists = totalLists;
        this.totalPublishers = totalPublishers;
        this.totalCategories = totalCategories;
        this.totalTags = totalTags;
        this.latestBooksAdded = latestBooksAdded;
    }

    public int getTotalBooks() {
        return totalBooks;
    }

    public void setTotalBooks(int totalBooks) {
        this.totalBooks = totalBooks;
    }

    public int getTotalAuthors() {
        return totalAuthors;
    }

    public void setTotalAuthors(int totalAuthors) {
        this.totalAuthors = totalAuthors;
    }

    public int getTotalLists() {
        return totalLists;
    }

    public void setTotalLists(int totalLists) {
        this.totalLists = totalLists;
    }

    public int getTotalPublishers() {
        return totalPublishers;
    }

    public void setTotalPublishers(int totalPublishers) {
        this.totalPublishers = totalPublishers;
    }

    public int getTotalCategories() {
        return totalCategories;
    }

    public void setTotalCategories(int totalCategories) {
        this.totalCategories = totalCategories;
    }

    public int getTotalTags() {
        return totalTags;
    }

    public void setTotalTags(int totalTags) {
        this.totalTags = totalTags;
    }

    public List<BookResponse> getLatestBooksAdded() {
        return latestBooksAdded;
    }

    public void setLatestBooksAdded(List<BookResponse> latestBooksAdded) {
        this.latestBooksAdded = latestBooksAdded;
    }
}
