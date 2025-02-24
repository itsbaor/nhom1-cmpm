package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.ChatRoom;
import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.repository.ChatRoomRepository;
import com.nhom1.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService{

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<ChatRoom> findByChatRoom(Long senderId, Long receiverId) {
        return chatRoomRepository.findBySenderIdAndRecipientId(senderId,receiverId)
                .or(() -> createChatRoom(senderId, receiverId));
    }

    private Optional<ChatRoom> createChatRoom(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId).get();
        User recipient = userRepository.findById(receiverId).get();

        Long chatroomId = senderId+receiverId;

        ChatRoom senderRecipient = new ChatRoom();
        senderRecipient.setId(chatroomId);
        senderRecipient.setSender(sender);
        senderRecipient.setRecipient(recipient);

        ChatRoom recipientSender = new ChatRoom();
        recipientSender.setId(chatroomId);
        recipientSender.setSender(recipient);
        recipientSender.setRecipient(sender);

        chatRoomRepository.save(senderRecipient);
        chatRoomRepository.save(recipientSender);

        return Optional.of(senderRecipient);
    }
}
