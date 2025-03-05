package com.example.socialNetworking.service;

import com.example.socialNetworking.model.Friend;
import com.example.socialNetworking.model.User;

import java.util.List;

public interface FriendService {
    Friend getFriend(User user, User findUser);

    Friend unfriend(User user, User findUser);

    Long countFriends(User user);

    List<Friend> getAllFriend(Long id);
}
