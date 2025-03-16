package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.email <> :email")
    List<User> findAllUsersExceptEmail(@Param("email") String email);
}
