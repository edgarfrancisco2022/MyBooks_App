package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT * FROM category WHERE category_name LIKE CONCAT('%', :searchQuery, '%') " +
            "AND user_id = :user_id", nativeQuery = true)
    List<Category> searchByCategory(String searchQuery, Long user_id);
}
