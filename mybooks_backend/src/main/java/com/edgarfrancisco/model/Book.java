package com.edgarfrancisco.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {

    /*
        NotNull - callNumber, title, authors, category, user
        Unique
     */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;
    private String callNumber;
    private String title;
    private String subtitle;
    private String year;
    private int numberOfPages;
    private int numberOfCopies;
    private String description;
    private String bookImageUrl;
    @ManyToMany(fetch = FetchType.LAZY) // refactor - FetchType.Lazy
    //@Fetch(value = FetchMode.SUBSELECT) // hibernate does not like two collections with FetchType.EAGER // this fixes the problem but must refactor with FetchType.Lazy
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors; // bidirectional
    @ManyToMany(fetch = FetchType.LAZY) // refactor FetchType.LAZY
    //@Fetch(value = FetchMode.SUBSELECT) // hibernate does not like two collections with FetchType.EAGER // this fixes the problem but must refactor with FetchType.Lazy
    @JoinTable(name = "book_tag",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags; // bidirectional
    @ManyToMany(fetch = FetchType.LAZY)
    //@Fetch(value = FetchMode.SUBSELECT)
    @JoinTable(name = "book_customcollection",
               joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name = "customcollection_id"))
    private List<CustomCollection> customCollections;
    @ManyToOne(fetch = FetchType.LAZY) // bidirectional
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    //@Column(nullable = false) - @Column not allowed
    private Category category; // bidirectional
    //bidirectional
    //In a One-to-Many/Many-to-One relationship, the owning side is usually defined
    //on the ‘many' side of the relationship. It's usually the side which owns the foreign key.
    //The @JoinColumn annotation defines that actual physical mapping on the owning side:
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collection_id") //foreign-key
    private Collection collection; // si es parte de una colección
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    //@Column(nullable = false) //@Column not allowed
    private User user;  // bidirectional

    public Book() {
    }

    public Book(String callNumber, String title, String subtitle, String year,
                int numberOfPages, int numberOfCopies, String description,
                String bookImageUrl, List<Author> authors, List<Tag> tags,
                Publisher publisher, Category category, Collection collection) {
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

    /************************************************/
    /********** Fields without associations *********/
    /************************************************/

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    /********************************************************/
    /********** Fields with ManyToMany associations *********/
    /********************************************************/

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public void addAuthor(Author author) {
        if (this.authors == null) {
            this.authors = new ArrayList<>();
        }
        this.authors.add(author);
    }

    public void removeAuthor(Author author) {
        this.authors.remove(author);
        author.getBooks().remove(this);
    }


    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags.add(tag);
    }

    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getBooks().remove(this);
    }


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
        customCollection.getBooks().remove(this);
    }

    /********************************************************/
    /********** Fields ManyToOne Associations ***************/
    /********************************************************/

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
    public void setCollection(Collection collectionName) {
        this.collection = collectionName;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
