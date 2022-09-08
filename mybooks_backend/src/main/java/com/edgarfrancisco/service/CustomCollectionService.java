package com.edgarfrancisco.service;

import com.edgarfrancisco.exception.domain.*;

public interface CustomCollectionService {

    String addNewList(String listname, String username) throws UserNotFoundException, ListAlreadyExistsException;

    String addBookToList(String listname, String callnumber, String username)
            throws UserNotFoundException, BookNotFoundException, ListNotFoundException,
            BookAlreadyExistsException;

    String deleteBookFromList(String listname, String callnumber, String username)
            throws UserNotFoundException, BookNotFoundException, ListNotFoundException;
}
