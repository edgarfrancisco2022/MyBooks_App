package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.Category;
import com.edgarfrancisco.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Collection findByCollectionName(String collectionName);

    @Query(value = "SELECT * FROM collection WHERE collection_name LIKE CONCAT('%', :searchQuery, '%') " +
            "AND user_id = :user_id", nativeQuery = true)
    List<Category> searchByCollection(String searchQuery, Long user_id);
}
