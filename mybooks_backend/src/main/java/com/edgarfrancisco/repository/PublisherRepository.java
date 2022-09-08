package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    @Query(value = "SELECT * FROM publisher WHERE publisher_name LIKE CONCAT('%', :searchQuery, '%') " +
            "AND user_id = :user_id", nativeQuery = true)
    List<Publisher> searchByPublisher(String searchQuery, Long user_id);
}
