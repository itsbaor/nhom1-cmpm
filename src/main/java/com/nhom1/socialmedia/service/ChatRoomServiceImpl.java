package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.repository.ChatRoomRepository;
import com.nhom1.socialmedia.service.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
}
