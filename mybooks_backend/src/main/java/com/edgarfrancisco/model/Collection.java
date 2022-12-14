package com.edgarfrancisco.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String collectionName;

    //bidirectional
    //the value of mappedBy is the name of the association-mapping attribute on the owning side
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "collection")
    //@Fetch(value = FetchMode.SUBSELECT) // hibernate does not like two collections with FetchType.EAGER // this fixes the problem but must refactor with FetchType.Lazy
    private List<Book> books;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public Collection() {
    }

    public Collection(String collectionName) {
        this.collectionName = collectionName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCollectionName() {
        return this.collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    @JsonBackReference(value = "collection-books")
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public void addBook(Book book) {
        if (this.books == null) {
            this.books = new ArrayList<>();
        }
        this.books.add(book);
    }

    public void removeBook(Book book) {
        this.books.remove(book);
    }

    @JsonBackReference(value = "collection-user")
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }
}
