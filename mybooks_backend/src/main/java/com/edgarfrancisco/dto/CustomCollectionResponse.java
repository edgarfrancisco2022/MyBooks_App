package com.edgarfrancisco.dto;

import com.edgarfrancisco.model.Book;
import com.edgarfrancisco.model.CustomCollection;

import java.util.List;

public class CustomCollectionResponse {

    CustomCollection customCollection;
    List<Book> books;

    public CustomCollection getCustomCollection() {
        return customCollection;
    }

    public void setCustomCollection(CustomCollection customCollection) {
        this.customCollection = customCollection;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
