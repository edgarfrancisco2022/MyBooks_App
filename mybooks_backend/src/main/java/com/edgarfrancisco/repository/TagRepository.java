package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Tag findByTagName(String tagName);

    @Query(value = "SELECT * FROM tag WHERE tag_name LIKE CONCAT('%', :searchQuery, '%') AND user_id = :user_id",
            nativeQuery = true)
    List<Tag> searchByTag(String searchQuery, Long user_id);
}
