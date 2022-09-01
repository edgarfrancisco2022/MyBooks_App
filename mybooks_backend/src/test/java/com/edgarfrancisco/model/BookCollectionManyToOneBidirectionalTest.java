package com.edgarfrancisco.model;

import com.edgarfrancisco.repository.BookRepository;
import com.edgarfrancisco.repository.CollectionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookCollectionManyToOneBidirectionalTest {

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @Test //+++
    public void createBookWithNewCollection() {
        Book book = new Book();
        book.setTitle("Book 1");

        Collection collection = new Collection();
        collection.setCollectionName("Collection 1");

        book.setCollection(collection);
        collection.addBook(book);

        collectionRepository.save(collection);
        bookRepository.save(book);
    }

    @Test //+++
    public void createBook2WithSameCollection() {
        Book book = new Book();
        book.setTitle("Book 2");

        Collection collection = collectionRepository.findByCollectionName("Collection 1");

        book.setCollection(collection);
        collection.addBook(book);

        collectionRepository.save(collection);
        bookRepository.save(book);
    }

    @Test //+++
    public void createBook3WithNewCollection2() {
        Book book = new Book();
        book.setTitle("Book 3");

        Collection collection = new Collection();
        collection.setCollectionName("Collection 2");

        book.setCollection(collection);
        collection.addBook(book);

        collectionRepository.save(collection);
        bookRepository.save(book);
    }

    @Test
    public void deleteBookKeepCollectionConditionally() {
        Book book = bookRepository.findByTitle("Book 1");
        Collection collection = book.getCollection();

        collection.getBooks().remove(book);
        book.setCollection(null);
        collectionRepository.save(collection);
        bookRepository.save(book);
        bookRepository.delete(book);

        if (collection.getBooks().size() == 0) {
            collectionRepository.deleteById(collection.getId());
        }
    }

    @Test
    public void deleteBookDeleteCollectionConditionally() {
        Book book = bookRepository.findByTitle("Book 2");
        Collection collection = book.getCollection();

        collection.getBooks().remove(book);
        book.setCollection(null);
        collectionRepository.save(collection);
        bookRepository.save(book);
        bookRepository.delete(book);

        if (collection.getBooks().size() == 0) {
            collectionRepository.deleteById(collection.getId());
        }
    }
}
