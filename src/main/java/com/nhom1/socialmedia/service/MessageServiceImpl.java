package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.repository.MessageRepository;
import com.nhom1.socialmedia.service.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
}
