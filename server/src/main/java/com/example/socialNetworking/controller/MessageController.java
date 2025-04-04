package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.MessageDto;
import com.example.socialNetworking.dto.mapper.MessageMapper;
import com.example.socialNetworking.model.Message;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.service.MessageService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public ResponseEntity<Void> sendMessage(@Payload Message message){
        messageService.sendMessage(message);
        MessageDto messageDto = MessageMapper.Instance.messageToMessageDto(message);
        messagingTemplate.convertAndSendToUser(
                String.valueOf(message.getReceiver().getId()),
                "queue/messages",
                messageDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{senderId}/{recipientId}")
    public ResponseEntity<List<MessageDto>> getMessages(
            @PathVariable("senderId") Long senderId, @PathVariable("recipientId") Long recipientId){
        List<Message> messages = messageService.getMessages(senderId, recipientId);

        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            MessageDto messageDto = MessageMapper.Instance.messageToMessageDto(message);
            messageDtos.add(messageDto);
        }

        return new ResponseEntity<>(messageDtos,HttpStatus.OK);
    }

}
