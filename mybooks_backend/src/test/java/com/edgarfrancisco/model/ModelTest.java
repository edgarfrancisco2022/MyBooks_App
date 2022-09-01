package com.edgarfrancisco.model;

import com.edgarfrancisco.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
public class ModelTest {

    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private CustomCollectionRepository customCollectionRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;

    @Test //+++
    public void createBook1() {
        Book book1 = new Book();

        book1.setTitle("Book 1");

        Author author1 = new Author();
        author1.setFirstName("Author 1");

        author1.addBook(book1);
        book1.addAuthor(author1);

        Tag tag1 = new Tag();
        tag1.setTagName("Tag 1");

        tag1.addBook(book1);
        book1.addTag(tag1);

        Publisher publisher1 = new Publisher();
        publisher1.setPublisherName("Publisher 1");

        publisher1.addBook(book1);
        book1.setPublisher(publisher1);

        User user1 = new User();
        user1.setFirstName("User 1");
        user1.setJoinDate(new Date());
        user1.addBook(book1);
        book1.setUser(user1);

        authorRepository.save(author1);
        tagRepository.save(tag1);
        publisherRepository.save(publisher1);
        userRepository.save(user1);
        bookRepository.save(book1);
    }

    @Test //+++
    public void createBook2() {
        // Different book, author, tag, publisher, user

        Book book2 = new Book();

        book2.setTitle("Book 2");

        Author author2 = new Author();
        author2.setFirstName("Author 2");

        author2.addBook(book2);
        book2.addAuthor(author2);

        Tag tag2 = new Tag();
        tag2.setTagName("Tag 2");

        tag2.addBook(book2);
        book2.addTag(tag2);

        Publisher publisher2 = new Publisher();
        publisher2.setPublisherName("Publisher 2");

        publisher2.addBook(book2);
        book2.setPublisher(publisher2);

        User user2 = new User();
        user2.setFirstName("User 2");
        user2.setJoinDate(new Date());
        user2.addBook(book2);
        book2.setUser(user2);

        authorRepository.save(author2);
        tagRepository.save(tag2);
        publisherRepository.save(publisher2);
        userRepository.save(user2);
        bookRepository.save(book2);
    }

    @Test
    public void createBook3() {
        // Different book, author, tag, publisher,
        // same user

        Book book3 = new Book();

        book3.setTitle("Book 3");

        Author author3 = new Author();
        author3.setFirstName("Author 3");

        author3.addBook(book3);
        book3.addAuthor(author3);

        Tag tag3 = new Tag();
        tag3.setTagName("Tag 3");

        tag3.addBook(book3);
        book3.addTag(tag3);

        Publisher publisher3 = new Publisher();
        publisher3.setPublisherName("Publisher 3");

        publisher3.addBook(book3);
        book3.setPublisher(publisher3);

        User user2 = userRepository.findByFirstName("User 2");
        user2.addBook(book3);
        book3.setUser(user2);

        authorRepository.save(author3);
        tagRepository.save(tag3);
        publisherRepository.save(publisher3);
        userRepository.save(user2);
        bookRepository.save(book3);
    }

    @Test
    public void createCustomCollection1ExistingUserExistingBook() {
        User user2 = userRepository.findByFirstName("User 2");
        List<Book> user2Books = user2.getBooks();

        Book user2Book2ToAddToCustomCollection = null;

        for (Book book : user2Books) {
            if (book.getTitle().equals("Book 2")) {
                user2Book2ToAddToCustomCollection = book;
            }
        }

        CustomCollection customCollection1 = new CustomCollection();
        customCollection1.setCustomCollectionName("Custom Collection 1");

        customCollection1.addBook(user2Book2ToAddToCustomCollection);
        user2Book2ToAddToCustomCollection.addCustomCollection(customCollection1);

        customCollection1.setUser(user2);
        user2.addCustomCollection(customCollection1);

        userRepository.save(user2);
        customCollectionRepository.save(customCollection1);
        bookRepository.save(user2Book2ToAddToCustomCollection);

    }

    @Test
    public void addBookCascadeTypeAll() {

        Book book = new Book();
        book.setCallNumber("001");
        book.setTitle("Book 1");
        book.setYear(1975);
        book.setDescription("Add first book");

        Author author = new Author();
        author.setFirstName("Author 1 firstName");
        author.setLastName("Author 1 lastName");
        Author author2 = new Author();
        author2.setFirstName("Author 2 firstName");
        author2.setLastName("Author 2 lastName");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(author2);

        Tag tag = new Tag();
        tag.setTagName("Tag 1");
        Tag tag2 = new Tag();
        tag2.setTagName("Tag 2");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("Publisher 1");

        Category category = new Category();
        category.setCategoryName("Category 1");

        User user = new User();
        user.setFirstName("User 1");

        book.setAuthors(authors);
        book.setTags(tags);
        book.setPublisher(publisher);
        book.setCategory(category);
        book.setUser(user);

        bookRepository.save(book);
    }

    @Test
    public void addBook2CascadeTypeAll() {

        Book book = new Book();
        book.setCallNumber("002");
        book.setTitle("Book 2");
        book.setYear(1975);
        book.setDescription("Add second book");

        Author author = new Author();
        author.setFirstName("Author 3 firstName");
        author.setLastName("Author 3 lastName");
        Author author2 = new Author();
        author2.setFirstName("Author 4 firstName");
        author2.setLastName("Author 4 lastName");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(author2);

        Tag tag = new Tag();
        tag.setTagName("Tag 3");
        Tag tag2 = new Tag();
        tag2.setTagName("Tag 4");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("Publisher 2");

        Category category = new Category();
        category.setCategoryName("Category 2");

        User user = new User();
        user.setFirstName("User 2");

        book.setAuthors(authors);
        book.setTags(tags);
        book.setPublisher(publisher);
        book.setCategory(category);
        book.setUser(user);

        bookRepository.save(book);
    }

    @Test
    public void addBook3CascadeTypeAll() {

        Book book = new Book();
        book.setCallNumber("003");
        book.setTitle("Book 3");
        book.setYear(1975);
        book.setDescription("Add third book");

        Author author = new Author();
        author.setFirstName("Author 4 firstName");
        author.setLastName("Author 4 lastName");
        Author author2 = new Author();
        author2.setFirstName("Author 5 firstName");
        author2.setLastName("Author 5 lastName");
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        authors.add(author2);

        Tag tag = new Tag();
        tag.setTagName("Tag 3");
        Tag tag2 = new Tag();
        tag2.setTagName("Tag 4");
        List<Tag> tags = new ArrayList<>();
        tags.add(tag);
        tags.add(tag2);

        Publisher publisher = new Publisher();
        publisher.setPublisherName("Publisher 3");

        Category category = new Category();
        category.setCategoryName("Category 3");

        User user = new User();
        user.setFirstName("User 2");

        book.setAuthors(authors);
        book.setTags(tags);
        book.setPublisher(publisher);
        book.setCategory(category);
        book.setUser(user);

        bookRepository.save(book);
    }
}
