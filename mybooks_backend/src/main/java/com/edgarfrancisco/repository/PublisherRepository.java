package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
}
