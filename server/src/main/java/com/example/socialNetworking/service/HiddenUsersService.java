package com.example.socialNetworking.service;

import com.example.socialNetworking.model.User;

import java.util.List;

public interface HiddenUsersService {
    void createHiddenUser(Long userId, User userReq);

    List<User> findAllBlockUser(User userreq);

    User removeBlockUser(Long userId);
}
