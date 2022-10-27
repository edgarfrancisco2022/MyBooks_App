package com.edgarfrancisco.controller;

import com.edgarfrancisco.dto.CustomCollectionResponse;
import com.edgarfrancisco.dto.HttpResponse;
import com.edgarfrancisco.exception.ExceptionHandling;
import com.edgarfrancisco.exception.domain.*;
import com.edgarfrancisco.model.CustomCollection;
import com.edgarfrancisco.service.CustomCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/list")
public class CustomCollectionController extends ExceptionHandling {

    @Autowired
    CustomCollectionService customCollectionService;

    @GetMapping("/get/{username}") //jwt
    public ResponseEntity<List<CustomCollectionResponse>> getLists(@PathVariable("username") String username)
            throws UserNotFoundException {

        List<CustomCollectionResponse> lists = customCollectionService.getLists(username);
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<List<CustomCollection>> searchByField(@RequestHeader("Search-Query") String searchQuery,
                                                            @PathVariable("username") String username)
            throws UserNotFoundException, BlankSearchQueryException {

        List<CustomCollection> lists = customCollectionService.searchBySearchQuery(searchQuery, username);
        return new ResponseEntity<>(lists, HttpStatus.OK);
    }

    @PostMapping("/add-list/{listname}/{username}")
    public ResponseEntity<HttpResponse> addNewList(@PathVariable("listname") String listname,
                                             @PathVariable("username") String username)
            throws UserNotFoundException, ListAlreadyExistsException {

        String response = customCollectionService.addNewList(listname, username);
        return this.createHttpResponse(HttpStatus.OK, response);
    }

    @DeleteMapping("/delete-list/{listname}/{username}")
    public ResponseEntity<HttpResponse> deleteList(@PathVariable("listname")String listname,
                                                   @PathVariable("username")String username)
            throws UserNotFoundException, ListNotFoundException {

        String response = customCollectionService.deleteList(listname, username);
        return this.createHttpResponse(HttpStatus.OK, response);
    }

    @PostMapping("/add-book/{listname}/{callnumber}/{username}")
    public ResponseEntity<HttpResponse> addBookToList(@PathVariable("listname")String listname,
                                                @PathVariable("callnumber")String callnumber,
                                                @PathVariable("username")String username)
            throws UserNotFoundException, ListNotFoundException, BookNotFoundException,
                   BookAlreadyExistsException {

        String response = customCollectionService.addBookToList(listname, callnumber, username);
        return this.createHttpResponse(HttpStatus.OK, response);
    }

    @DeleteMapping("/delete-book/{listname}/{callnumber}/{username}")
    public ResponseEntity<HttpResponse> deleteBookFromList(@PathVariable("listname")String listname,
                                                     @PathVariable("callnumber")String callnumber,
                                                     @PathVariable("username")String username)
            throws UserNotFoundException, ListNotFoundException, BookNotFoundException {

        String response = customCollectionService.deleteBookFromList(listname, callnumber, username);
        return this.createHttpResponse(HttpStatus.OK, response);
    }

    private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {

        return new ResponseEntity<>(new HttpResponse(httpStatus.value(), httpStatus,
                httpStatus.getReasonPhrase(), message), httpStatus);
    }
}
