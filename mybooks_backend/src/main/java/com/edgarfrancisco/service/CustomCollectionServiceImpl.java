package com.edgarfrancisco.service;

import com.edgarfrancisco.dto.CustomCollectionResponse;
import com.edgarfrancisco.exception.domain.*;
import com.edgarfrancisco.model.Book;
import com.edgarfrancisco.model.CustomCollection;
import com.edgarfrancisco.model.User;
import com.edgarfrancisco.repository.BookRepository;
import com.edgarfrancisco.repository.CustomCollectionRepository;
import com.edgarfrancisco.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<CustomCollection> searchBySearchQuery(String searchQuery, String username)
            throws UserNotFoundException, BlankSearchQueryException {

        bookService.validateBookAndUsername(null, StringUtils.EMPTY, username);

        User user = userRepository.findByUsername(username);

        List<CustomCollection> lists =
                customCollectionRepository.searchByCustomCollection(searchQuery, user.getId());

        if (lists == null) {
            lists = new ArrayList<>();
        }

        Comparator<CustomCollection> sortLists =
                (b1, b2) -> b1.getCustomCollectionName().compareTo(b2.getCustomCollectionName());

        Collections.sort(lists, sortLists);

        lists.forEach(list -> {
            list.setUser(null);
            list.getBooks().forEach(book -> {
                book.setAuthors(null);
                book.setPublisher(null);
                book.setCollection(null);
                book.setCategory(null);
                book.setTags(null);
                book.setCustomCollections(null);
                book.setUser(null);
            });
        });

        return lists;
    }

    public List<CustomCollectionResponse> getLists(String username) throws UserNotFoundException {

        bookService.validateBookAndUsername(null, null, username);

        User user = userRepository.findByUsername(username);
        List<CustomCollection> lists = user.getCustomCollections();

        if (lists == null) {
            lists = new ArrayList<>();
        }

        Comparator<CustomCollection> sortLists =
                (b1, b2) -> b1.getCustomCollectionName().compareTo(b2.getCustomCollectionName());

        Collections.sort(lists, sortLists);

        List<CustomCollectionResponse> listDTO = new ArrayList<>();

        lists.forEach(list -> {
            list.setUser(null);
            list.getBooks().forEach(book -> {
                book.setAuthors(null);
                book.setPublisher(null);
                book.setCollection(null);
                book.setCategory(null);
                book.setTags(null);
                book.setCustomCollections(null);
                book.setUser(null);
            });

            CustomCollectionResponse customCollectionResponse = new CustomCollectionResponse();
            customCollectionResponse.setCustomCollection(list);
            customCollectionResponse.setBooks(list.getBooks());
            listDTO.add(customCollectionResponse);
        });

        return listDTO;
    }

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

    public String deleteList(String listname, String username)
            throws UserNotFoundException, ListNotFoundException {

        bookService.validateBookAndUsername(null, null, username);

        User user = userRepository.findByUsername(username);

        CustomCollection dbList = customCollectionRepository
                .searchByCustomCollectionNameAndUserId(listname, user.getId());

        if (dbList == null) {
            throw new ListNotFoundException(LIST_NOT_FOUND + listname);
        }

        List<Book> books = new ArrayList<>();

        // need to clone it first, otherwise when removing book from dbList
        // the size of the list being iterated changes and throws an exception
        if (dbList.getBooks() != null) {
            for (Book book : dbList.getBooks()) {
                books.add(book);
            }
        }

        if (books != null && books.size() > 0) {
            for (Book book : books) {
                book.removeCustomCollection(dbList);  //must remove from 'owning' entity first
                dbList.removeBook(book);
            }
        }

        //'reference' entities will be updated automatically - user
        customCollectionRepository.deleteById(dbList.getId());

        return String.format(LIST_REMOVED_FROM_DATABASE, listname);
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
