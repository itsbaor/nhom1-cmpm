package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.FriendDto;
import com.example.socialNetworking.dto.Friend_RequestDto;
import com.example.socialNetworking.dto.mapper.FriendMapper;
import com.example.socialNetworking.dto.mapper.Friend_RequestMapper;
import com.example.socialNetworking.model.Friend;
import com.example.socialNetworking.model.Friend_Request;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.model.enumType.Status_Friend;
import com.example.socialNetworking.service.FriendService;
import com.example.socialNetworking.service.Friend_RequestService;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(("/api/friend"))
public class FriendController {

    private final Friend_RequestService friend_requestService;
    private final FriendService friendService;
    private final UserService userService;
    private final JwtUtils jwtUtils;

    @GetMapping("/all")
    public ResponseEntity<List<FriendDto>> getAllFriends(@RequestHeader("Authorization") String jwt) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        List<Friend> listFriend = friendService.getAllFriend(user.getId());
        List<FriendDto> friendDtos = new ArrayList<>();

        for(Friend friend : listFriend) {
            FriendDto friendDto = FriendMapper.INSTANCE.friendToFriendDto(friend);
            friendDtos.add(friendDto);
        }
        return new ResponseEntity<>(friendDtos, HttpStatus.OK);
    }

    @GetMapping("/require/{userId}")
    public ResponseEntity<Friend_RequestDto> requestAddFriend(
            @RequestHeader("Authorization") String jwt, @PathVariable Long userId) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);
        User findUser = userService.findById(userId);

        Friend_Request fq = friend_requestService.requireAddFriend(user,findUser);
        Friend_RequestDto requestDto = Friend_RequestMapper.INSTANCE.friend_RequestToFriend_RequestDto(fq);
        return ResponseEntity.ok(requestDto);
    }

    @GetMapping("/refuse/{userId}")
    public ResponseEntity<Friend_RequestDto> refuseAddFriend(
            @RequestHeader("Authorization") String jwt, @PathVariable Long userId) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);
        User findUser = userService.findById(userId);

        Friend_Request fq = friend_requestService.refuseAddFriend(user,findUser);
        Friend_RequestDto requestDto = Friend_RequestMapper.INSTANCE.friend_RequestToFriend_RequestDto(fq);
        return ResponseEntity.ok(requestDto);
    }

    @GetMapping("/accept/{userId}")
    public ResponseEntity<FriendDto> accpeptAddFriend(
            @RequestHeader("Authorization") String jwt, @PathVariable Long userId) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);
        User findUser = userService.findById(userId);

        Friend fr = friend_requestService.acceptAddFriend(user,findUser);
        FriendDto requestDto = FriendMapper.INSTANCE.friendToFriendDto(fr);
        return ResponseEntity.ok(requestDto);
    }

    @GetMapping("/relation/{id}")
    public ResponseEntity<Status_Friend> getRelationshipFriend(
            @RequestHeader("Authorization") String jwt, @PathVariable Long id) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);
        User findUser = userService.findById(id);

        Friend_Request frq = friend_requestService.getRequestAddFriend(user,findUser);
        Friend fr = friendService.getFriend(user,findUser);

        if(frq != null){
            return ResponseEntity.ok(Status_Friend.REQUEST_SENT);
        } else if(fr != null){
            return ResponseEntity.ok(Status_Friend.FRIEND);
        } else {
            return ResponseEntity.ok(Status_Friend.NOT_FRIEND);
        }
    }

    @GetMapping("/all/request_add_friend")
    public ResponseEntity<List<Friend_RequestDto>> getAllRequestAddFriend(
            @RequestHeader("Authorization") String jwt) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        List<Friend_Request> frq =  friend_requestService.getAllRequestAddFriend(user);
        List<Friend_RequestDto> frqDto = new ArrayList<>();

        for(Friend_Request friendRequest : frq){
            Friend_RequestDto dto = Friend_RequestMapper.INSTANCE.friend_RequestToFriend_RequestDto(friendRequest);
            frqDto.add(dto);
        }
        return ResponseEntity.ok(frqDto);
    }

    @GetMapping("/all/send_request_add_friend")
    public ResponseEntity<List<Friend_RequestDto>> getAllSendRequestAddFriend(
            @RequestHeader("Authorization") String jwt) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        List<Friend_Request> frq =  friend_requestService.getAllSendRequestAddFriend(user);
        List<Friend_RequestDto> frqDto = new ArrayList<>();

        for(Friend_Request friendRequest : frq){
            Friend_RequestDto dto = Friend_RequestMapper.INSTANCE.friend_RequestToFriend_RequestDto(friendRequest);
            frqDto.add(dto);
        }
        return ResponseEntity.ok(frqDto);
    }

    @DeleteMapping("/unfriend/{userId}")
    public ResponseEntity<FriendDto> unfiend(
            @RequestHeader("Authorization") String jwt, @PathVariable Long userId) {
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);
        User findUser = userService.findById(userId);

        Friend friend = friendService.unfriend(user,findUser);
        FriendDto friendDto = FriendMapper.INSTANCE.friendToFriendDto(friend);
        return new ResponseEntity<FriendDto>(friendDto,HttpStatus.OK);
    }

    @GetMapping("/size_friend")
    public ResponseEntity<Long> getSizeFriend(@RequestHeader("Authorization") String jwt){
        String email = jwtUtils.getEmailFromToken(jwt);
        User user = userService.getUserByEmail(email);

        Long sizeFriend = friendService.countFriends(user);
        return ResponseEntity.ok(sizeFriend);
    }

}
