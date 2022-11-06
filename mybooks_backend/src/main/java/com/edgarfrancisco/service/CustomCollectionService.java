package com.edgarfrancisco.service;

import com.edgarfrancisco.dto.CustomCollectionResponse;
import com.edgarfrancisco.exception.domain.*;
import com.edgarfrancisco.model.CustomCollection;

import java.util.List;

public interface CustomCollectionService {

    List<CustomCollection> searchBySearchQuery(String searchQuery, String authorization)
            throws UserNotFoundException, BlankSearchQueryException;
    List<CustomCollectionResponse> getLists(String authorization) throws UserNotFoundException;

    String addNewList(String listname, String authorization) throws UserNotFoundException, ListAlreadyExistsException;

    String deleteList(String listname, String authorization) throws UserNotFoundException, ListNotFoundException;

    String addBookToList(String listname, String callnumber, String authorization)
            throws UserNotFoundException, BookNotFoundException, ListNotFoundException,
            BookAlreadyExistsException;

    String deleteBookFromList(String listname, String callnumber, String authorization)
            throws UserNotFoundException, BookNotFoundException, ListNotFoundException;
}
