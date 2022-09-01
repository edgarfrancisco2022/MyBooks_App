package com.edgarfrancisco.model;

import com.edgarfrancisco.repository.BookRepository;
import com.edgarfrancisco.repository.TagRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class BookTagManyToManyBidirectionalTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TagRepository tagRepository;

    @Test
    public void saveBookTag1() {
        Book book = new Book();
        book.setTitle("Book 1");

        Tag tag = new Tag();
        tag.setTagName("Tag 1");

        book.addTag(tag);
        tag.addBook(book);

        tagRepository.save(tag);
        bookRepository.save(book);
    }

    @Test
    void saveBookTag2() {
        Book book = new Book();
        book.setTitle("Book 2");

        Tag tag = new Tag();
        tag.setTagName("Tag 2");

        book.addTag(tag);
        tag.addBook(book);

        tagRepository.save(tag);
        bookRepository.save(book);
    }

    @Test
    void saveBook3ExistingTag() {
        Book book = new Book();
        book.setTitle("Book 3");

        Tag tag = tagRepository.findByTagName("Tag 2");

        book.addTag(tag);
        tag.addBook(book);

        tagRepository.save(tag);
        bookRepository.save(book);
    }

    @Test // saving new author with existing book +++
    void saveTag3ExistingBook() {
        Tag tag = new Tag();
        tag.setTagName("Tag 3");

        Book book = bookRepository.findByTitle("Book 3");

        book.addTag(tag);
        tag.addBook(book);

        tagRepository.save(tag);
        bookRepository.save(book);
    }

    @Test
    void deleteBook3() { // deleting existing book // conditionally delete authors

        Book book = bookRepository.findByTitle("Book 3");
        List<Tag> tags = new ArrayList<>();

        for (Tag tag : book.getTags()) {
            tags.add(tag);
        }

        for (Tag tag : tags) { // refactor: add remove methods to both entities
            tag.removeBook(book);
            book.removeTag(tag);
            tagRepository.save(tag);
            bookRepository.save(book);
        }

        bookRepository.delete(book); // owning entity must be removed first

        for (Tag tag : tags) {
            if (tag.getBooks().size() == 0) {
                tagRepository.deleteById(tag.getId());
            }
        }
    }
}
