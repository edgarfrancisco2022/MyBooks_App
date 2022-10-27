package com.edgarfrancisco.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY) // refactor - FetchType.LAZY
    //@Fetch(value = FetchMode.SUBSELECT) // hibernate does not like two collections with FetchType.EAGER // this fixes the problem but must refactor with FetchType.Lazy
    private List<Book> books; // bidirectional
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    public Author() {
    }

    public Author(String firstName, String middleName, String lastName) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;

        if (StringUtils.isNotEmpty(middleName)) {
            this.fullName = firstName + " " + middleName + " " + lastName;
        } else {
            this.fullName = firstName + " " + lastName;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    @JsonBackReference
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
        book.getAuthors().remove(this);
    }

    @JsonBackReference
    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return firstName.equals(author.firstName) && Objects.equals(middleName, author.middleName) && lastName.equals(author.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, middleName, lastName);
    }
}
