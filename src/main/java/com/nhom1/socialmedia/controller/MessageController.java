package com.nhom1.socialmedia.controller;

import com.nhom1.socialmedia.Mapper.MessageMapper;
import com.nhom1.socialmedia.dto.MessageDto;
import com.nhom1.socialmedia.model.Message;
import com.nhom1.socialmedia.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/message")
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
    public ResponseEntity<List<MessageDto>> getMessages(@PathVariable Long senderId, @PathVariable Long recipientId){
        List<Message> messages = messageService.getMessages(senderId, recipientId);

        List<MessageDto> messageDtos = new ArrayList<>();
        for (Message message : messages) {
            MessageDto messageDto = MessageMapper.Instance.messageToMessageDto(message);
            messageDtos.add(messageDto);
        }

        return new ResponseEntity<>(messageDtos, HttpStatus.OK);
    }

}
