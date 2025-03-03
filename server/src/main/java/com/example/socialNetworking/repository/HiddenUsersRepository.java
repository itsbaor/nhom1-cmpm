package com.example.socialNetworking.repository;

import com.example.socialNetworking.model.HiddenUsers;
import com.example.socialNetworking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HiddenUsersRepository extends JpaRepository<HiddenUsers, Long> {
    List<HiddenUsers> findHiddenUsersByUser(User user);

    HiddenUsers findHiddenUsersByHiddenUser_Id(Long userId);
}
