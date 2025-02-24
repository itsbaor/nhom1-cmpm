package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.repository.FriendRepository;
import com.example.socialNetworking.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

}
