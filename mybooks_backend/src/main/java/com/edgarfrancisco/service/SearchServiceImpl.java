package com.edgarfrancisco.service;

import com.edgarfrancisco.dto.BookResponse;
import com.edgarfrancisco.exception.domain.BlankSearchQueryException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.model.Book;
import com.edgarfrancisco.model.User;
import com.edgarfrancisco.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.edgarfrancisco.constant.BookImplConstant.SEARCH_QUERY_IS_BLANK;

@Service
@Transactional
public class SearchServiceImpl implements SearchService {

    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    CustomCollectionRepository customCollectionRepository;
    @Autowired
    PublisherRepository publisherRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CollectionRepository collectionRepository;
    @Autowired
    private UserRepository userRepository;

    private static final List<Book> books = new ArrayList<>();

    public List<BookResponse> searchByField(String searchField, String searchQuery, String authorization)
            throws UserNotFoundException, BlankSearchQueryException {

        bookService.validateBookAndUsername(null, StringUtils.EMPTY, authorization);
        validateSearchQuery(searchQuery);

        String username = bookService.getUserName(authorization);

        User user = userRepository.findByUsername(username);

        // query dinamico
        switch(searchField) {
            case "callnumber":
                bookRepository.searchByCallnumber(searchQuery, user.getId()).forEach(book -> books.add(book));
                break;
            case "title":
                bookRepository.searchByTitle(searchQuery, user.getId()).forEach(book -> books.add(book));
                break;
            case "subtitle":
                bookRepository.searchBySubtitle(searchQuery, user.getId()).forEach(book -> books.add(book));
            case "year":
                bookRepository.searchByYear(searchQuery, user.getId()).forEach(book -> books.add(book));
                break;
            case "author":
                authorRepository.searchByAuthor(searchQuery, user.getId())
                        .forEach(auth -> books.addAll(auth.getBooks()));
                break;
            case "tag":
                tagRepository.searchByTag(searchQuery, user.getId())
                        .forEach(tag -> books.addAll(tag.getBooks()));
                break;
            case "custom_collection":
                customCollectionRepository.searchByCustomCollection(searchQuery, user.getId())
                        .forEach(custom -> books.addAll(custom.getBooks()));
                break;
            case "publisher":
                publisherRepository.searchByPublisher(searchQuery, user.getId())
                        .forEach(pub -> books.addAll(pub.getBooks()));
                break;
            case "category":
                categoryRepository.searchByCategory(searchQuery, user.getId())
                        .forEach(cat -> books.addAll(cat.getBooks()));
                break;
            case "collection":
                collectionRepository.searchByCollection(searchQuery, user.getId())
                        .forEach(col -> books.addAll(col.getBooks()));
                break;
            default:
                break;
        }

        Comparator<Book> sortBooks = (b1, b2) -> b1.getTitle().compareTo(b2.getTitle());

        Collections.sort(books, sortBooks);

        List<BookResponse> listOfBooks = books.stream().map(x -> bookService.createBookResponse(x))
                .collect(Collectors.toList());

        books.clear();

        return listOfBooks;
    }


    private void validateSearchQuery(String searchQuery) throws BlankSearchQueryException {
//        boolean isNotBlank = StringUtils.isNotBlank(searchQuery);
//        if (!isNotBlank) {
//            throw new BlankSearchQueryException(SEARCH_QUERY_IS_BLANK);
//        }
        boolean isNotNull = searchQuery != null;
        if (!isNotNull) {
            throw new BlankSearchQueryException(SEARCH_QUERY_IS_BLANK);
        }
    }


}
