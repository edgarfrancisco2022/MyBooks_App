package com.edgarfrancisco.service;

import com.edgarfrancisco.exception.domain.*;
import com.edgarfrancisco.model.Book;
import com.edgarfrancisco.model.CustomCollection;
import com.edgarfrancisco.model.User;
import com.edgarfrancisco.repository.BookRepository;
import com.edgarfrancisco.repository.CustomCollectionRepository;
import com.edgarfrancisco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.edgarfrancisco.constant.BookImplConstant.NO_BOOK_FOUND_WITH_CALLNUMBER;
import static com.edgarfrancisco.constant.CustomCollectionImplConstant.*;

@Service
public class CustomCollectionServiceImpl implements CustomCollectionService {

    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CustomCollectionRepository customCollectionRepository;
    @Autowired
    UserRepository userRepository;

    public String addNewList(String listname, String username) throws UserNotFoundException, ListAlreadyExistsException {
        bookService.validateBookAndUsername(null, null, username);

        User user = userRepository.findByUsername(username);

        CustomCollection dbList = customCollectionRepository
                .searchByCustomCollectionNameAndUserId(listname, user.getId());

        if (dbList != null) {
            throw new ListAlreadyExistsException(LIST_ALREADY_EXISTS);
        }

        CustomCollection newList = new CustomCollection(listname);
        newList.setUser(user);
        user.addCustomCollection(newList);
        userRepository.save(user);
        customCollectionRepository.save(newList);
        return newList.getCustomCollectionName();
    }

    public String addBookToList(String listname, String callnumber, String username)
            throws UserNotFoundException, BookNotFoundException, ListNotFoundException,
                   BookAlreadyExistsException {

        boolean bookExists = bookService.validateBookAndUsername(null, callnumber, username);

        if (!bookExists) {
            throw new BookNotFoundException(NO_BOOK_FOUND_WITH_CALLNUMBER + callnumber);
        }

        User user = userRepository.findByUsername(username);

        CustomCollection dbList = customCollectionRepository
                .searchByCustomCollectionNameAndUserId(listname, user.getId());

        if (dbList == null) {
            throw new ListNotFoundException(LIST_NOT_FOUND + listname);
        }

        Optional<Book> dbListBook = dbList.getBooks().stream()
                .filter(x -> x.getCallNumber().equals(callnumber)).findAny();

        if (dbListBook.isPresent()) {
            throw new BookAlreadyExistsException(BOOK_ALREADY_EXISTS_IN_LIST + listname);
        }

        Book bookToAdd = user.getBooks().stream().filter(x -> x.getCallNumber().equals(callnumber))
                .findAny().get();

        dbList.addBook(bookToAdd);
        bookToAdd.addCustomCollection(dbList);

        customCollectionRepository.save(dbList);
        bookRepository.save(bookToAdd);

        return String.format(BOOK_ADDED_TO_LIST, callnumber, listname);
    }

    public String deleteBookFromList(String listname, String callnumber, String username)
            throws UserNotFoundException, BookNotFoundException, ListNotFoundException {

        boolean bookExists = bookService.validateBookAndUsername(null, callnumber, username);

        if (!bookExists) {
            throw new BookNotFoundException(NO_BOOK_FOUND_WITH_CALLNUMBER + callnumber);
        }

        User user = userRepository.findByUsername(username);

        CustomCollection dbList = customCollectionRepository
                .searchByCustomCollectionNameAndUserId(listname, user.getId());

        if (dbList == null) {
            throw new ListNotFoundException(LIST_NOT_FOUND + listname);
        }

        Optional<Book> dbListBook = dbList.getBooks().stream()
                .filter(x -> x.getCallNumber().equals(callnumber)).findAny();

        if (dbListBook.isEmpty()) {
            throw new BookNotFoundException(BOOK_DOES_NOT_EXIST_IN_LIST + listname);
        }

        Book bookToDelete = dbListBook.get();

        dbList.removeBook(bookToDelete);
        bookToDelete.removeCustomCollection(dbList);

        customCollectionRepository.save(dbList);
        bookRepository.save(bookToDelete);

        return String.format(BOOK_REMOVED_FROM_LIST, callnumber, listname);
    }
}
