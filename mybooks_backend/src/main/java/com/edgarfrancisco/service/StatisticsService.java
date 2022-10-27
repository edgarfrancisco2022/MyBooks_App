package com.edgarfrancisco.service;

import com.edgarfrancisco.dto.StatisticsResponse;
import com.edgarfrancisco.exception.domain.UserNotFoundException;

public interface StatisticsService {

    public StatisticsResponse getStatistics(String username) throws UserNotFoundException;
}
