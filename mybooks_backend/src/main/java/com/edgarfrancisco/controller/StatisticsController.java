package com.edgarfrancisco.controller;

import com.edgarfrancisco.dto.StatisticsResponse;
import com.edgarfrancisco.exception.ExceptionHandling;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/statistics")
public class StatisticsController extends ExceptionHandling {
    @Autowired
    StatisticsService statisticsService;

    @GetMapping("/get") //jwt
    public ResponseEntity<StatisticsResponse> getStatistics(@RequestHeader("Authorization") String authorization)
            throws UserNotFoundException {

        StatisticsResponse stats = statisticsService.getStatistics(authorization);
        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

}
