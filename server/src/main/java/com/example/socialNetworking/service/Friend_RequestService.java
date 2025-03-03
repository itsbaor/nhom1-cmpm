package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Friend;
import com.example.socialNetworking.model.Friend_Request;
import com.example.socialNetworking.model.User;

import java.util.List;

public interface Friend_RequestService {
    Friend_Request requireAddFriend(User user, User findUser);

    Friend_Request refuseAddFriend(User user, User findUser);

    Friend acceptAddFriend(User user, User findUser);

    Friend_Request getRequestAddFriend(User user, User findUser);

    List<Friend_Request> getAllRequestAddFriend(User user);

    List<Friend_Request> getAllSendRequestAddFriend(User user);
}
