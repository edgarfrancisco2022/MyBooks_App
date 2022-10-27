package com.edgarfrancisco.controller;

import com.edgarfrancisco.dto.StatisticsResponse;
import com.edgarfrancisco.exception.ExceptionHandling;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController extends ExceptionHandling {
    @Autowired
    StatisticsService statisticsService;

    @GetMapping("/get/{username}") //jwt
    public ResponseEntity<StatisticsResponse> getStatistics(@PathVariable("username") String username)
            throws UserNotFoundException {

        StatisticsResponse stats = statisticsService.getStatistics(username);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

}
