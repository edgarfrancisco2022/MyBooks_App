package com.edgarfrancisco.controller;

import com.edgarfrancisco.exception.domain.*;
import com.edgarfrancisco.service.CustomCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/list")
public class CustomCollectionController {

    @Autowired
    CustomCollectionService customCollectionService;

    @PostMapping("/add-list/{listname}/{username}")
    public ResponseEntity<String> addNewList(@PathVariable("listname") String listname,
                                             @PathVariable("username") String username)
            throws UserNotFoundException, ListAlreadyExistsException {

        String response = customCollectionService.addNewList(listname, username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add-book/{listname}/{callnumber}/{username}")
    public ResponseEntity<String> addBookToList(@PathVariable("listname")String listname,
                                                @PathVariable("callnumber")String callnumber,
                                                @PathVariable("username")String username)
            throws UserNotFoundException, ListNotFoundException, BookNotFoundException,
                   BookAlreadyExistsException {

        String response = customCollectionService.addBookToList(listname, callnumber, username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete_book/{listname}/{callnumber}/{username}")
    public ResponseEntity<String> deleteBookFromList(@PathVariable("listname")String listname,
                                                     @PathVariable("callnumber")String callnumber,
                                                     @PathVariable("username")String username)
            throws UserNotFoundException, ListNotFoundException, BookNotFoundException {

        String response = customCollectionService.deleteBookFromList(listname, callnumber, username);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
