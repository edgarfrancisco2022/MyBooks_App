package com.edgarfrancisco.service;

import com.edgarfrancisco.dto.BookResponse;
import com.edgarfrancisco.exception.domain.BookAlreadyExistsException;
import com.edgarfrancisco.exception.domain.BookNotFoundException;
import com.edgarfrancisco.exception.domain.UserNotFoundException;
import com.edgarfrancisco.model.*;
import com.edgarfrancisco.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.edgarfrancisco.constant.BookImplConstant.*;
import static com.edgarfrancisco.constant.UserImplConstant.NO_USER_FOUND_BY_USERNAME;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private CustomCollectionRepository customCollectionRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private UserRepository userRepository;

    public List<BookResponse> getBooks(String username) throws UserNotFoundException {

        validateBookAndUsername(null, StringUtils.EMPTY, username);

        User user = userRepository.findByUsername(username);
        List<Book> books = user.getBooks();

        if (books == null) {
            books = new ArrayList<>();
        }

        List<BookResponse> listOfBooks = books.stream().map(x -> createBookResponse(x))
                .collect(Collectors.toList());

        return listOfBooks;
    }

    public BookResponse addNewBook(Book book, String username) throws UserNotFoundException, BookAlreadyExistsException {

        boolean alreadyExists = validateBookAndUsername(book, StringUtils.EMPTY, username);

        if (alreadyExists) {
            throw new BookAlreadyExistsException(BOOK_ALREADY_EXISTS);
        }

        return addNewBookOrUpdateBook(book, username);
    }

    public BookResponse updateBook(Book book, String username) throws UserNotFoundException, BookNotFoundException {

        boolean alreadyExists = validateBookAndUsername(book, StringUtils.EMPTY, username);

        if (!alreadyExists) {
            throw new BookNotFoundException(NO_BOOK_FOUND_WITH_CALLNUMBER + book.getCallNumber());
        }

        deleteBook(username, book.getCallNumber());

        return addNewBookOrUpdateBook(book, username);
    }

    private BookResponse addNewBookOrUpdateBook(Book book, String username) {

        User user = userRepository.findByUsername(username);

        List<Author> authors = book.getAuthors();
        book.setAuthors(null);

        if (authors != null && user != null) {
            for (Author author : authors) {
                Optional authorExists = user.getAuthors().stream().filter(x -> x.equals(author)).findFirst();
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

        List<Tag> tags = book.getTags();
        book.setTags(null);

        if (tags != null && user != null) {
            for (Tag tag : tags) {
                Optional tagExists = user.getTags().stream().filter(x -> x.getTagName().equals(tag.getTagName()))
                        .findFirst();
                if (tagExists.isPresent()) {
                    book.addTag((Tag) tagExists.get());
                    ((Tag) tagExists.get()).addBook(book);
                    tagRepository.save(((Tag) tagExists.get()));
                } else {
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
            Optional publisherExists = user.getPublishers().stream()
                    .filter(x -> x.getPublisherName().equals(publisher.getPublisherName())).findFirst();
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
            Optional categoryExists = user.getCategories().stream()
                    .filter(x -> x.getCategoryName().equals(category.getCategoryName())).findFirst();
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
            Optional collectionExists = user.getCollections().stream()
                    .filter(x -> x.getCollectionName().equals(collection.getCollectionName())).findFirst();
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

    public void deleteBook(String username, String callNumber) throws UserNotFoundException, BookNotFoundException {

        boolean alreadyExists = validateBookAndUsername(null, callNumber, username);

        if (!alreadyExists) {
            throw new BookNotFoundException(NO_BOOK_FOUND_WITH_CALLNUMBER + callNumber);
        }

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

    private BookResponse createBookResponse(Book book) {

        BookResponse bookResponse = new BookResponse();
        bookResponse.setCallNumber(book.getCallNumber());
        bookResponse.setTitle(book.getTitle());
        bookResponse.setSubtitle(book.getSubtitle());
        bookResponse.setYear(book.getYear());
        bookResponse.setNumberOfPages(book.getNumberOfPages());
        bookResponse.setNumberOfCopies(book.getNumberOfCopies());
        bookResponse.setDescription(book.getDescription());
        bookResponse.setBookImageUrl(book.getBookImageUrl());

        List<Author> authors = book.getAuthors();
        for (Author author : authors) {
            author.setBooks(null);
            author.setUser(null);
        }
        bookResponse.setAuthors(authors);

        List<Tag> tags = book.getTags();
        if (tags != null) {
            for (Tag tag : tags) {
                tag.setBooks(null);
                tag.setUser(null);
            }
        }
        bookResponse.setTags(tags);

        List<CustomCollection> customCollections = book.getCustomCollections();
        if (customCollections != null) {
            for (CustomCollection customCollection : customCollections) {
                customCollection.setBooks(null);
                customCollection.setUser(null);
            }
        }
        bookResponse.setCustomCollections(customCollections);

        Publisher publisher = book.getPublisher();
        if (publisher != null) {
            publisher.setBooks(null);
            publisher.setUser(null);
        }
        bookResponse.setPublisher(publisher);

        Category category = book.getCategory();
        if (category != null) {
            category.setBooks(null);
            category.setUser(null);
        }
        bookResponse.setCategory(category);

        Collection collection = book.getCollection();
        if (collection != null) {
            collection.setBooks(null);
            collection.setUser(null);
        }
        bookResponse.setCollection(collection);

        return bookResponse;
    }

    private boolean validateBookAndUsername(Book book, String callNumber, String username) throws UserNotFoundException {
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
            if (book != null && books != null) { //handles addNewBook()
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
}
