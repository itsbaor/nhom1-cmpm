package com.example.socialNetworking.service;

import com.example.socialNetworking.model.User;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);

    User saveUser(User user);

    User findById(Long userId);

    List<User> findListUserUnlessOwn(String email);

    User updateStatusUser(Long id);

}
