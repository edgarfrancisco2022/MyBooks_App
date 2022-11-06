package com.edgarfrancisco.service;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.edgarfrancisco.dto.BookResponse;
import com.edgarfrancisco.exception.domain.BookAlreadyExistsException;
import com.edgarfrancisco.exception.domain.BookNotFoundException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.model.*;
import com.edgarfrancisco.repository.*;
import com.edgarfrancisco.security.utility.JWTTokenProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.edgarfrancisco.constant.BookImplConstant.BOOK_ALREADY_EXISTS;
import static com.edgarfrancisco.constant.BookImplConstant.NO_BOOK_FOUND_WITH_CALLNUMBER;
import static com.edgarfrancisco.constant.SecurityConstant.TOKEN_PREFIX;
import static com.edgarfrancisco.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private CustomCollectionRepository customCollectionRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    public Page<Book> getBooks(String authorization, PageRequest pageRequest) throws UserNotFoundException {

        validateBookAndUsername(null, StringUtils.EMPTY, authorization);

        String username = getUserName(authorization);

        User user = userRepository.findByUsername(username);

        Page<Book> books = bookRepository.getAllBooks(user.getId(), pageRequest);

        return books;
    }

    @Transactional
    public BookResponse addNewBook(Book book, String authorization) throws UserNotFoundException,
            BookAlreadyExistsException {

        boolean response = validateBookAndUsername(book, StringUtils.EMPTY, authorization);

        if (response) {
            throw new BookAlreadyExistsException(BOOK_ALREADY_EXISTS);
        }

        String username = getUserName(authorization);

        return addNewBookOrUpdateBook(book, username);
    }

    public BookResponse updateBook(Book book, String authorization) throws UserNotFoundException, BookNotFoundException {

        boolean response = validateBookAndUsername(book, StringUtils.EMPTY, authorization);

        if (!response) {
            throw new BookNotFoundException(NO_BOOK_FOUND_WITH_CALLNUMBER + book.getCallNumber());
        }

        String username = getUserName(authorization);

        deleteBook(authorization, book.getCallNumber());

        return addNewBookOrUpdateBook(book, username);
    }

    private BookResponse addNewBookOrUpdateBook(Book book, String username) {

        User user = userRepository.findByUsername(username);

        book.setDateAdded(new Date());

        List<Author> authors = book.getAuthors();  // guarda autores del frontend
        book.setAuthors(null); // borra autores del libro del frontend

        if (authors != null && user != null && authors.size() > 0) {
            for (Author author : authors) {
                Optional authorExists = Optional.empty();

                if (user.getAuthors() != null) {
                    authorExists = user.getAuthors().stream().filter(x -> x.equals(author)).findFirst();
                }

                if (authorExists.isPresent()) {
                    book.addAuthor((Author) authorExists.get());
                    ((Author) authorExists.get()).addBook(book);
                    authorRepository.save(((Author) authorExists.get()));
                } else {
                    author.setUser(user);
                    book.addAuthor(author);
                    author.addBook(book);
                    authorRepository.save(author);
                }
            }
        }

        List<Tag> tags = book.getTags(); // crea una lista con los tags del libro que viene del frontend
        book.setTags(null); // borra los tags del libro del frontend para volverlos añadir con el libro

        if (tags != null && user != null) {
            for (Tag tag : tags) {
                Optional tagExists = Optional.empty();

                if (user.getTags() != null) {
                    // checa si los tags existen en el backend
                    tagExists = user.getTags().stream().filter(x -> x.getTagName().equals(tag.getTagName()))
                            .findFirst();
                }

                if (tagExists.isPresent()) {
                    // si el tag existe en la base de datos añade el tag al libro y el libro al tag y actualiza el tag
                    book.addTag((Tag) tagExists.get());
                    ((Tag) tagExists.get()).addBook(book);
                    tagRepository.save(((Tag) tagExists.get()));
                } else {
                    // si el tag no existe en la base de datos añade el user al tag el tag al libro y el libro al tag
                    // y guarda un nuevo tag en la base de datos
                    tag.setUser(user);
                    book.addTag(tag);
                    tag.addBook(book);
                    tagRepository.save(tag);
                }
            }
        }

        Publisher publisher = book.getPublisher();
        book.setPublisher(null);

        if (publisher != null && user != null) {
            Optional publisherExists = Optional.empty();

            if (user.getPublishers() != null) {
                publisherExists = user.getPublishers().stream()
                        .filter(x -> x.getPublisherName().equals(publisher.getPublisherName())).findFirst();
            }

            if (publisherExists.isPresent()) {
                book.setPublisher((Publisher) publisherExists.get());
                ((Publisher) publisherExists.get()).addBook(book);
                publisherRepository.save(((Publisher) publisherExists.get()));
            } else {
                publisher.setUser(user);
                book.setPublisher(publisher);
                publisher.addBook(book);
                publisherRepository.save(publisher);
            }
        }

        Category category = book.getCategory();
        book.setCategory(null);

        if (category != null && user != null) {
            Optional categoryExists = Optional.empty();

            if (user.getCategories() != null) {
                categoryExists = user.getCategories().stream()
                        .filter(x -> x.getCategoryName().equals(category.getCategoryName())).findFirst();
            }

            if (categoryExists.isPresent()) {
                book.setCategory((Category) categoryExists.get());
                ((Category) categoryExists.get()).addBook(book);
                categoryRepository.save(((Category) categoryExists.get()));
            } else {
                category.setUser(user);
                book.setCategory(category);
                category.addBook(book);
                categoryRepository.save(category);
            }
        }

        Collection collection = book.getCollection();
        book.setCollection(null);

        if (collection != null && user != null) {
            Optional collectionExists = Optional.empty();

            if (user.getCollections() != null) {
                collectionExists = user.getCollections().stream()
                        .filter(x -> x.getCollectionName().equals(collection.getCollectionName())).findFirst();
            }

            if (collectionExists.isPresent()) {
                book.setCollection((Collection) collectionExists.get());
                ((Collection) collectionExists.get()).addBook(book);
                collectionRepository.save(((Collection) collectionExists.get()));
            } else {
                collection.setUser(user);
                book.setCollection(collection);
                collection.addBook(book);
                collectionRepository.save(collection);
            }
        }

        book.setUser(user);
        bookRepository.save(book);

        return createBookResponse(book);
    }

    @Transactional
    public void deleteBook(String authorization, String callNumber) throws UserNotFoundException, BookNotFoundException {

        boolean response = validateBookAndUsername(null, callNumber, authorization);

        if (!response) {
            throw new BookNotFoundException(NO_BOOK_FOUND_WITH_CALLNUMBER + callNumber);
        }

        String username = getUserName(authorization);

        User dbUser = userRepository.findByUsername(username);
        List<Book> books = dbUser.getBooks();
        Book book = null;

        for (Book dbBook : books) {
            if (dbBook.getCallNumber().equals(callNumber)) {
                book = dbBook;
            }
        }

        List<Author> authors = new ArrayList<>();

        if (book.getAuthors() != null) {
            for (Author author : book.getAuthors()) {
                authors.add(author);
            }
            for (Author author : authors) {
                book.removeAuthor(author);
                author.removeBook(book);
                if (author.getBooks().size() == 0) {
                    author.setUser(null);
                    dbUser.removeAuthor(author);
                }
            }
        }

        List<Tag> tags = new ArrayList<>();

        if (book.getTags() != null) {
            for (Tag tag : book.getTags()) {
                tags.add(tag);
            }
            for (Tag tag : tags) {
                book.removeTag(tag);
                tag.removeBook(book);
                if (tag.getBooks().size() == 0) {
                    tag.setUser(null);
                    dbUser.removeTag(tag);
                }
            }
        }

        List<CustomCollection> customCollections = new ArrayList<>();

        if (book.getCustomCollections() != null) {
            for (CustomCollection customCollection : book.getCustomCollections()) {
                customCollections.add(customCollection);
            }
            for (CustomCollection customCollection : customCollections) {
                book.removeCustomCollection(customCollection);
                customCollection.removeBook(book);
            }
        }

        Publisher publisher = book.getPublisher();

        if (publisher != null) {
            book.setPublisher(null);
            publisher.removeBook(book);
            if (publisher.getBooks().size() == 0) {
                publisher.setUser(null);
                dbUser.removePublisher(publisher);
            }

        }

        Category category = book.getCategory();

        if (category != null) {
            book.setCategory(null);
            category.removeBook(book);
            if (category.getBooks().size() == 0) {
                category.setUser(null);
                dbUser.removeCategory(category);
            }
        }

        Collection collection = book.getCollection();

        if (collection != null) {
            book.setCollection(null);
            collection.removeBook(book);
            if (collection.getBooks().size() == 0) {
                collection.setUser(null);
                dbUser.removeCollection(collection);
            }
        }

        bookRepository.delete(book); // owning entity must be removed first

        for (Author author : authors) {
            if (author.getBooks().size() == 0) {
                authorRepository.deleteById(author.getId());
            }
        }

        for (Tag tag : tags) {
            if (tag.getBooks().size() == 0) {
                tagRepository.deleteById(tag.getId());
            }
        }

        for (CustomCollection customCollection : customCollections) {
            if (customCollection.getBooks().size() == 0) {
                customCollectionRepository.deleteById(customCollection.getId());
            }
        }

        if (publisher != null) {
            if (publisher.getBooks().size() == 0) {
                publisherRepository.deleteById(publisher.getId());
            }
        }

        if (category != null) {
            if (category.getBooks().size() == 0) {
                categoryRepository.deleteById(category.getId());
            }
        }

        if (collection != null) {
            if (collection.getBooks().size() == 0) {
                collectionRepository.deleteById(collection.getId());
            }
        }
    }

    public BookResponse createBookResponse(Book book) {

        BookResponse bookResponse = new BookResponse();
        bookResponse.setDateAdded(book.getDateAdded());
        bookResponse.setCallNumber(book.getCallNumber());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setSubtitle(book.getSubtitle());
        bookResponse.setYear(book.getYear());
        bookResponse.setNumberOfPages(book.getNumberOfPages());
        bookResponse.setNumberOfCopies(book.getNumberOfCopies());
        bookResponse.setDescription(book.getDescription());
        bookResponse.setBookImageUrl(book.getBookImageUrl());

        if (book.getAuthors() != null) {
            List<Author> authors = book.getAuthors().stream()
                    .map(x -> new Author(x.getFirstName(), x.getMiddleName(), x.getLastName()))
                    .collect(Collectors.toList());

            bookResponse.setAuthors(authors);
        }


        if (book.getTags() != null) {
            List<Tag> tags = book.getTags().stream().map(x -> new Tag(x.getTagName())).collect(Collectors.toList());

            bookResponse.setTags(tags);
        }

        if (book.getCustomCollections() != null) {
            List<CustomCollection> customCollections = book.getCustomCollections().stream()
                    .map(x -> new CustomCollection(x.getCustomCollectionName())).collect(Collectors.toList());

            bookResponse.setCustomCollections(customCollections);
        }

        if (book.getPublisher() != null) {
            Publisher publisher = new Publisher(book.getPublisher().getPublisherName());
            bookResponse.setPublisher(publisher);
        }

        if (book.getCategory() != null) {
            Category category = new Category(book.getCategory().getCategoryName());
            bookResponse.setCategory(category);
        }


        if (book.getCollection() != null) {
            Collection collection = new Collection((book.getCollection().getCollectionName()));
            bookResponse.setCollection(collection);
        }

        return bookResponse;
    }

    @Transactional
    public boolean validateBookAndUsername(Book book, String callNumber, String authorization)
            throws UserNotFoundException {

        String username = getUserName(authorization);

        if (username.equals("invalid-token")) {
            throw new TokenExpiredException("Token is invalid");
        }

        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UserNotFoundException(NO_USER_FOUND_BY_USERNAME + username);
        }

        List<Book> books = user.getBooks();

        if (StringUtils.isNotEmpty(callNumber)) { //handles deleteBook()
            Book dbBook = books.stream().filter(x -> x.getCallNumber().equals(callNumber)).findFirst().orElse(null);
            if (dbBook != null) {
                return true;
            }
        } else {
            if (book != null && books != null && books.size() > 0) { //handles addNewBook()
                for (Book elem : books) {
                    if (elem.getCallNumber().equals(book.getCallNumber())) {
                        return true;
                    }
                }
            } else { //handles getBooks()
                if (book == null && books != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getUserName(String authorization) {
        String token = authorization.substring(TOKEN_PREFIX.length());
        String username = jwtTokenProvider.getSubject(token);

        if (jwtTokenProvider.isTokenValid(username, token)) {
            return username;
        }

        return "invalid-token";
    }

}
