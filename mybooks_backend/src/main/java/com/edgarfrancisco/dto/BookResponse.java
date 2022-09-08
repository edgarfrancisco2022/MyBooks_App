package com.edgarfrancisco.dto;

import com.edgarfrancisco.model.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class BookResponse {
    private String callNumber;
    private String title;
    private String subtitle;
    private String year;
    private int numberOfPages;
    private int numberOfCopies;
    private String description;
    private String bookImageUrl;
    private List<Author> authors;
    private List<Tag> tags;
    private List<CustomCollection> customCollections;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //solves error due to lazy loading
    private Publisher publisher;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category category;
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Collection collection;

    public BookResponse() {
    }

    public BookResponse(String callNumber, String title, String subtitle, String year, int numberOfPages,
                        int numberOfCopies, String description, String bookImageUrl, List<Author> authors,
                        List<Tag> tags, List<CustomCollection> customCollections, Publisher publisher,
                        Category category, Collection collection) {
        this.callNumber = callNumber;
        this.title = title;
        this.subtitle = subtitle;
        this.year = year;
        this.numberOfPages = numberOfPages;
        this.numberOfCopies = numberOfCopies;
        this.description = description;
        this.bookImageUrl = bookImageUrl;
        this.authors = authors;
        this.tags = tags;
        this.customCollections = customCollections;
        this.publisher = publisher;
        this.category = category;
        this.collection = collection;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<CustomCollection> getCustomCollections() {
        return customCollections;
    }

    public void setCustomCollections(List<CustomCollection> customCollections) {
        this.customCollections = customCollections;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
}
