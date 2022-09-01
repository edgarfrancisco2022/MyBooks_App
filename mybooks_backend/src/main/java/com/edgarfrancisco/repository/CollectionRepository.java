package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collection, Long> {

    Collection findByCollectionName(String collectionName);
}
