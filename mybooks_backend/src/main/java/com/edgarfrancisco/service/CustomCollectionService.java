package com.edgarfrancisco.service;

import com.edgarfrancisco.dto.CustomCollectionResponse;
import com.edgarfrancisco.exception.domain.*;
import com.edgarfrancisco.model.CustomCollection;

import java.util.List;

public interface CustomCollectionService {

    List<CustomCollection> searchBySearchQuery(String searchQuery, String username)
            throws UserNotFoundException, BlankSearchQueryException;
    List<CustomCollectionResponse> getLists(String username) throws UserNotFoundException;

    String addNewList(String listname, String username) throws UserNotFoundException, ListAlreadyExistsException;

    String deleteList(String listname, String username) throws UserNotFoundException, ListNotFoundException;

    String addBookToList(String listname, String callnumber, String username)
            throws UserNotFoundException, BookNotFoundException, ListNotFoundException,
            BookAlreadyExistsException;

    String deleteBookFromList(String listname, String callnumber, String username)
            throws UserNotFoundException, BookNotFoundException, ListNotFoundException;
}
