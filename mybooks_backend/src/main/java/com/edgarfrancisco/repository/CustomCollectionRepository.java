package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.CustomCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomCollectionRepository extends JpaRepository<CustomCollection, Long> {

    @Query(value = "SELECT * FROM custom_collection WHERE custom_collection_name LIKE CONCAT('%', :searchQuery, '%') " +
            "AND user_id = :user_id", nativeQuery = true)
    List<CustomCollection> searchByCustomCollection(String searchQuery, Long user_id);

    @Query(value = "SELECT * FROM custom_collection WHERE custom_collection_name = :listname " +
            "AND user_id = :user_id", nativeQuery = true)
    CustomCollection searchByCustomCollectionNameAndUserId(String listname, Long user_id);
}
