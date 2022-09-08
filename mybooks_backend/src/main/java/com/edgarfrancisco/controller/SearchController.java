package com.edgarfrancisco.controller;

import com.edgarfrancisco.dto.BookResponse;
import com.edgarfrancisco.exception.domain.BlankSearchQueryException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    SearchService searchService;

    @GetMapping("/field/{username}")
    public ResponseEntity<List<BookResponse>> searchByField(@RequestHeader("Search-Field") String searchField,
                                                            @RequestHeader("Search-Query") String searchQuery,
                                                            @PathVariable("username") String username)
            throws UserNotFoundException, BlankSearchQueryException {

        List<BookResponse> books = searchService.searchByField(searchField, searchQuery, username);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
