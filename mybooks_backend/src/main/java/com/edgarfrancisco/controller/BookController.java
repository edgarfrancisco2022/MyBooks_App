package com.edgarfrancisco.controller;

import com.edgarfrancisco.dto.BookResponse;
import com.edgarfrancisco.dto.HttpResponse;
import com.edgarfrancisco.exception.ExceptionHandling;
import com.edgarfrancisco.exception.domain.BookAlreadyExistsException;
import com.edgarfrancisco.exception.domain.BookNotFoundException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.model.Book;
import com.edgarfrancisco.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/book")
public class BookController extends ExceptionHandling {

    public static final String BOOK_DELETED_SUCCESSFULLY = "Book deleted succesfully";

    @Autowired
    private BookService bookService;

    @GetMapping("/get/{username}") //jwt
    public ResponseEntity<Page<Book>> getBooks(@PathVariable("username") String username)
            throws UserNotFoundException {

        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Book> books = bookService.getBooks(username, pageRequest);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/add/{username}")
    public ResponseEntity<BookResponse> addNewBook(@RequestBody Book book,
                                                   @PathVariable("username") String username)
            throws BookAlreadyExistsException, UserNotFoundException {

        BookResponse bookResponse = bookService.addNewBook(book, username);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @PostMapping("/update/{username}")
    public ResponseEntity<BookResponse> updateBook(@RequestBody Book book,
                                                   @PathVariable("username") String username)
            throws UserNotFoundException, BookNotFoundException {

        BookResponse bookResponse = bookService.updateBook(book, username);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{username}/{callnumber}")
    public ResponseEntity<HttpResponse> deleteBook(@PathVariable("username") String username,
                                                   @PathVariable("callnumber") String callNumber)
            throws UserNotFoundException, BookNotFoundException {

        bookService.deleteBook(username, callNumber);
        return createHttpResponse(HttpStatus.OK, BOOK_DELETED_SUCCESSFULLY);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {

        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase(), message), httpStatus);
    }
}
