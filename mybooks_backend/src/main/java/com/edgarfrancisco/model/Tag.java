package com.edgarfrancisco.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tagName;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    //@Fetch(value = FetchMode.SUBSELECT) // hibernate does not like two collections with FetchType.EAGER // this fixes the problem but must refactor with FetchType.Lazy
    private List<Book> books; // bidirectional

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public Tag() {
    }

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @JsonBackReference(value = "tag-books")
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> tags) {
        this.books = tags;
    }

    public void addBook(Book book) {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
        book.getTags().remove(this);
    }

    @JsonBackReference(value = "tag-user")
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
