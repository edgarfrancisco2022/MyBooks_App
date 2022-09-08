package com.edgarfrancisco.repository;

import com.edgarfrancisco.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByFirstName(String firstName);
    User findByUserId(String userId);
    User findByUsername(String username);
    User findByEmail(String email);

}
