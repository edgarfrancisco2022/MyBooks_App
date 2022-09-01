package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitle(String title);
    Book findByCallNumber(String callNumber);

}
