package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.repository.FriendRepository;
import com.example.socialNetworking.repository.Friend_RequestRepository;
import com.example.socialNetworking.service.Friend_RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Friend_RequestServiceImpl implements Friend_RequestService {

    private final Friend_RequestRepository friend_requestRepository;
    private final FriendRepository friendRepository;

}
