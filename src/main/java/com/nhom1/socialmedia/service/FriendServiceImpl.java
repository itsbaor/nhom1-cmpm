package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.repository.FriendRepository;
import com.nhom1.socialmedia.service.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
}
