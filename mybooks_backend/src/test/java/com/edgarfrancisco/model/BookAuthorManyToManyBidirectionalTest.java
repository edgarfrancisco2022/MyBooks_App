package com.edgarfrancisco.model;

import com.edgarfrancisco.repository.AuthorRepository;
import com.edgarfrancisco.repository.BookRepository;
import com.edgarfrancisco.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BookAuthorManyToManyBidirectionalTest {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private UserRepository userRepository;


    @Test // saving new author with new book +++
    void saveBook1() {
        Book book = new Book();
        book.setTitle("Book test 1");

        Author author = new Author();
        author.setFirstName("Author test 1");

        book.addAuthor(author);
        author.addBook(book);

        User user = new User();
        user.setFirstName("User test 1");
        book.setUser(user);
        author.setUser(user);
        userRepository.save(user);
        authorRepository.save(author);
        bookRepository.save(book);     /******** Owning side must be saved last *********/

    }

    @Test // saving new author with new book +++
    void saveBook2() {
        Book book = new Book();
        book.setTitle("Book 2");

        Author author = new Author();
        author.setFirstName("Author 2");

        book.addAuthor(author);
        author.addBook(book);

        authorRepository.save(author);
        bookRepository.save(book);

    }

    @Test // saving new author with new book +++
    void saveBook3() {
        Book book = new Book();
        book.setTitle("Book 3");

        Author author = new Author();
        author.setFirstName("Author 3");

        book.addAuthor(author);
        author.addBook(book);

        authorRepository.save(author);
        bookRepository.save(book);
    }


    @Test // saving new book with existing author +++
    void saveBook4ExistingAuthor() {
        Book book = new Book();
        book.setTitle("Book 4");

        Author author = authorRepository.findByFirstName("Author 3");

        book.addAuthor(author);
        author.addBook(book);

        authorRepository.save(author);
        bookRepository.save(book);
    }

    @Test // saving new author with existing book +++
    void saveAuthor4ExistingBook() {
        Author author = new Author();
        author.setFirstName("Author 4");

        Book book = bookRepository.findByTitle("Book 3");

        book.addAuthor(author);
        author.addBook(book);

        authorRepository.save(author);
        bookRepository.save(book);
    }

    @Test
    void deleteBook3() { // deleting existing book // conditionally delete authors +++

        Book book = bookRepository.findByTitle("Book 3");
        List<Author> authors = new ArrayList<>();

        for (Author author : book.getAuthors()) {
            authors.add(author);
        }

        for (Author author : authors) { // refactor: add remove methods to both entities
            author.removeBook(book);
            book.removeAuthor(author);
            authorRepository.save(author);
            bookRepository.save(book);
        }

        bookRepository.delete(book); // owning entity must be removed first

        for (Author author : authors) {
            if (author.getBooks().size() == 0) {
                authorRepository.deleteById(author.getId());
            }
        }
    }

    @Test
    void retrieveAuthor() {

        Author author = authorRepository.findByFirstName("Author 3");
        System.out.println(author.getFirstName());
    }

    @Test
    void retrieveBook() {

        Book book = bookRepository.findByTitle("Book 3");
        System.out.println(book.getTitle());
    }
}
