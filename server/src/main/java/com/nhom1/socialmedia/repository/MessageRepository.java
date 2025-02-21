package com.nhom1.socialmedia.repository;

import com.nhom1.socialmedia.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
