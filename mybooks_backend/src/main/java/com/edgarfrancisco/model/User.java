package com.edgarfrancisco.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String profileImageUrl;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Date lastLoginDate;
    private Date displayLastLogin;
    private Date joinDate;
    private String role; // user | admin | guest
    private String[] Authorities;
    private boolean isActive;
    private boolean isLocked;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    //@Fetch(value = FetchMode.SUBSELECT) // hibernate does not like two collections with FetchType.EAGER // this fixes the problem but must refactor with FetchType.Lazy
    List<Book> books;  // bidirectional
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    //@Fetch(value = FetchMode.SUBSELECT)
    List<Author> authors;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    //@Fetch(value = FetchMode.SUBSELECT)
    List<Tag> tags;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    //@Fetch(value = FetchMode.SUBSELECT)
    List<CustomCollection> customCollections;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    //@Fetch(value = FetchMode.SUBSELECT)
    List<Publisher> publishers;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    //@Fetch(value = FetchMode.SUBSELECT)
    List<Category> categories;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    //@Fetch(value = FetchMode.SUBSELECT)
    List<Collection> collections;

    public User() {
    }

    public User(String firstName, String lastName, String username, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImageUrl() { return profileImageUrl; }

    public void setProfileImageUrl(String profileImageUrl) { this.profileImageUrl = profileImageUrl; }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getDisplayLastLogin() {
        return displayLastLogin;
    }

    public void setDisplayLastLogin(Date displayLastLogin) {
        this.displayLastLogin = displayLastLogin;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String[] getAuthorities() {
        return Authorities;
    }

    public void setAuthorities(String[] authorities) {
        Authorities = authorities;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }


//    @JsonBackReference(value = "user-books")
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


//    @JsonBackReference(value = "user-authors")
    public List<Author> getAuthors() { return authors; }

    public void setAuthors(List<Author> authors) { this.authors = authors; }

    public void addAuthor(Author author) {
        if (this.authors == null) {
            this.authors = new ArrayList<>();
        }
        this.authors.add(author);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
    }


//    @JsonBackReference(value = "user-tags")
    public List<Tag> getTags() { return tags; }

    public void setTags(List<Tag> tags) { this.tags = tags; }

    public void addTag(Tag tag) {
        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
    }


//    @JsonBackReference(value = "user-custom-collections")
    public List<CustomCollection> getCustomCollections() {
        return customCollections;
    }

    public void setCustomCollections(List<CustomCollection> customCollections) {
        this.customCollections = customCollections;
    }
    public void addCustomCollection(CustomCollection customCollection) {
        if (this.customCollections == null) {
            this.customCollections = new ArrayList<>();
        }
        this.customCollections.add(customCollection);
    }

    public void removeCustomCollection(CustomCollection customCollection) {
        this.customCollections.remove(customCollection);
    }


//    @JsonBackReference(value = "user-publisher")
    public List<Publisher> getPublishers() { return publishers; }

    public void setPublishers(List<Publisher> publishers) { this.publishers = publishers; }

    public void addPublisher(Publisher publisher) {
        if (this.publishers == null) {
            this.publishers = new ArrayList<>();
        }
        this.publishers.add(publisher);
    }

    public void removePublisher(Publisher publisher) {
        this.publishers.remove(publisher);
    }


//    @JsonBackReference(value = "user-category")
    public List<Category> getCategories() { return categories; }

    public void setCategories(List<Category> categories) { this.categories = categories; }

    public void addCategory(Category category) {
        if (this.categories == null) {
            this.categories = new ArrayList<>();
        }
        this.categories.add(category);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
    }


//    @JsonBackReference(value = "user-collection")
    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    public void addCollection(Collection collection) {
        if (this.collections == null) {
            this.collections = new ArrayList<>();
        }
        this.collections.add(collection);
    }

    public void removeCollection(Collection collection) {
        this.collections.remove(collection);
    }
}
