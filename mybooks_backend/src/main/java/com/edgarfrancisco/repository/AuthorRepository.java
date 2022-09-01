package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByFirstName(String firstName);
}
