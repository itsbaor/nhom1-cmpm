package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.Message;

import java.util.List;

public interface MessageService {
    void sendMessage(Message message);

    List<Message> getMessages(Long senderId, Long recipientId);
}
