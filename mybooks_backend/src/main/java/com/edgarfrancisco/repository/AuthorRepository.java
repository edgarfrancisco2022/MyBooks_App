package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findByFirstName(String firstName);
    @Query(value = "SELECT * FROM author WHERE full_name LIKE CONCAT('%', :searchQuery, '%') AND user_id = :user_id",
            nativeQuery = true)
    List<Author> searchByAuthor(String searchQuery, Long user_id);
}
