package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.model.ChatRoom;
import com.example.socialNetworking.model.Message;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.MessageRepository;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.service.ChatRoomService;
import com.example.socialNetworking.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    private final ChatRoomService chatRoomService;

    @Override
    public Message sendMessage(Message reqMess) {
        User sender = userRepository.findById(reqMess.getSender().getId()).get();
        User receiver = userRepository.findById(reqMess.getReceiver().getId()).get();

        ChatRoom room = chatRoomService.findByChatRoom(sender.getId(),receiver.getId()).get();

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(reqMess.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setChatRoom(room);

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(Long senderId, Long receiverId) {
        ChatRoom room = chatRoomService.findByChatRoom(senderId,receiverId).get();
        return messageRepository.findByChatRoomId(room.getId());
    }
}
