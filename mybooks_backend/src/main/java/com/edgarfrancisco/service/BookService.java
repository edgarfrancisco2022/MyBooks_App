package com.edgarfrancisco.service;

import com.edgarfrancisco.dto.BookResponse;
import com.edgarfrancisco.exception.domain.BookAlreadyExistsException;
import com.edgarfrancisco.exception.domain.BookNotFoundException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.model.Book;

import java.util.List;

public interface BookService {
    List<BookResponse> getBooks(String username) throws UserNotFoundException;
    BookResponse addNewBook(Book book, String username) throws BookAlreadyExistsException,
                                                               UserNotFoundException;
    BookResponse updateBook(Book book, String username) throws UserNotFoundException,
                                                               BookNotFoundException;
    void deleteBook(String username, String callNumber) throws UserNotFoundException,
                                                               BookNotFoundException;
    boolean validateBookAndUsername(Book book, String callNumber, String username) throws UserNotFoundException;

    BookResponse createBookResponse(Book book);
}
