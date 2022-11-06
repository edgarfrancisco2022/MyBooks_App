package com.edgarfrancisco.service;

import com.edgarfrancisco.dto.BookResponse;
import com.edgarfrancisco.exception.domain.BlankSearchQueryException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;

import java.util.List;

public interface SearchService {

    List<BookResponse> searchByField(String searchField, String searchQuery, String authorization)
            throws UserNotFoundException, BlankSearchQueryException;
}
