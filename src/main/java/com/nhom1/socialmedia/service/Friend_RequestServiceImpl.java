package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.repository.FriendRequestRepository;
import com.nhom1.socialmedia.service.service.Friend_RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Friend_RequestServiceImpl implements Friend_RequestService {

    private final FriendRequestRepository friendRequestRepository;
}
