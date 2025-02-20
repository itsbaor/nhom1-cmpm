package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
