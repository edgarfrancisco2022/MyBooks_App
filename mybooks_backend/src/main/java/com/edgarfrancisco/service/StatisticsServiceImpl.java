package com.edgarfrancisco.service;

import com.edgarfrancisco.dto.BookResponse;
import com.edgarfrancisco.dto.StatisticsResponse;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.model.Book;
import com.edgarfrancisco.model.User;
import com.edgarfrancisco.repository.BookRepository;
import com.edgarfrancisco.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    BookService bookService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;

    public StatisticsResponse getStatistics(String username) throws UserNotFoundException {

        bookService.validateBookAndUsername(null, null, username);

        User user = userRepository.findByUsername(username);

        StatisticsResponse statisticsResponse = new StatisticsResponse();

        if (user.getBooks() != null) {
            statisticsResponse.setTotalBooks(user.getBooks().size());
        } else {
            statisticsResponse.setTotalBooks(0);
        }

        if (user.getAuthors() != null) {
            statisticsResponse.setTotalAuthors(user.getAuthors().size());
        } else {
            statisticsResponse.setTotalAuthors(0);
        }

        if (user.getCustomCollections() != null) {
            statisticsResponse.setTotalLists(user.getCustomCollections().size());
        } else {
            statisticsResponse.setTotalLists(0);
        }

        if (user.getPublishers() != null) {
            statisticsResponse.setTotalPublishers(user.getPublishers().size());
        } else {
            statisticsResponse.setTotalPublishers(0);
        }

        if (user.getCategories() != null) {
            statisticsResponse.setTotalCategories(user.getCategories().size());
        } else {
            statisticsResponse.setTotalCategories(0);
        }

        if (user.getTags() != null) {
            statisticsResponse.setTotalTags(user.getTags().size());
        } else {
            statisticsResponse.setTotalTags(0);
        }

        List<Book> latestBooksAdded = bookRepository.getLatestBooksAdded(user.getId());

        List<BookResponse> listOfBooks = latestBooksAdded.stream().map(x -> bookService.createBookResponse(x))
                .collect(Collectors.toList());

        statisticsResponse.setLatestBooksAdded(listOfBooks);

        return statisticsResponse;
    }
}
