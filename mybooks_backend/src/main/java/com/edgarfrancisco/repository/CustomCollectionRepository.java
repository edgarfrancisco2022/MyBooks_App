package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.CustomCollection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomCollectionRepository extends JpaRepository<CustomCollection, Long> {
}
