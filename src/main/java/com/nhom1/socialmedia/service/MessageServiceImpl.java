package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.ChatRoom;
import com.nhom1.socialmedia.model.Message;
import com.nhom1.socialmedia.repository.ChatRoomRepository;
import com.nhom1.socialmedia.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ChatRoomService chatRoomService;

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public List<Message> getMessages(Long senderId, Long recipientId) {
        ChatRoom room = chatRoomService.findByChatRoom(senderId,recipientId).get();
        return messageRepository.findByChatRoomId(room.getId());
    }
}
