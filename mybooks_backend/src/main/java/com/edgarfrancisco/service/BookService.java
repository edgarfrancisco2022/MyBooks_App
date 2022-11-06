package com.edgarfrancisco.service;

import com.edgarfrancisco.dto.BookResponse;
import com.edgarfrancisco.exception.domain.BookAlreadyExistsException;
import com.edgarfrancisco.exception.domain.BookNotFoundException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface BookService {
    Page<Book> getBooks(String username, PageRequest pageRequest) throws UserNotFoundException;
    BookResponse addNewBook(Book book, String authorization) throws BookAlreadyExistsException,
                                                               UserNotFoundException;
    BookResponse updateBook(Book book, String authorization) throws UserNotFoundException,
            BookNotFoundException;
    void deleteBook(String authorization, String callNumber) throws UserNotFoundException,
                                                               BookNotFoundException;
    boolean validateBookAndUsername(Book book, String callNumber, String username) throws UserNotFoundException;

    BookResponse createBookResponse(Book book);

    String getUserName(String authorization);
}
