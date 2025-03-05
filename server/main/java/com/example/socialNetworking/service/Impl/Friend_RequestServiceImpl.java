package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.model.Friend;
import com.example.socialNetworking.model.Friend_Request;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.FriendRepository;
import com.example.socialNetworking.repository.Friend_RequestRepository;
import com.example.socialNetworking.service.Friend_RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class Friend_RequestServiceImpl implements Friend_RequestService {

    private final Friend_RequestRepository friend_requestRepository;
    private final FriendRepository friendRepository;

    @Override
    public Friend_Request requireAddFriend(User user, User findUser) {
        Friend_Request req = friend_requestRepository.findBySenderIdAndReceiverId(user.getId(), findUser.getId());

        if (req != null) {
            friend_requestRepository.deleteById(req.getId());
            return req;
        }

        Friend_Request newReq = new Friend_Request();
        newReq.setSender(user);
        newReq.setReceiver(findUser);
        newReq.setTimestamp(LocalDateTime.now());
        newReq.setSeen(false);

        friend_requestRepository.save(newReq);
        return newReq;
    }

    @Override
    public Friend_Request refuseAddFriend(User user, User findUser) {
        Friend_Request req = friend_requestRepository.findBySenderIdAndReceiverId(findUser.getId(),user.getId());

        if (req != null) {
            friend_requestRepository.deleteById(req.getId());
            return req;
        }
        return null;
    }

    @Override
    @Transactional
    public Friend acceptAddFriend(User user, User findUser) {
        Friend_Request req = friend_requestRepository.findBySenderIdAndReceiverId(findUser.getId(),user.getId());

        if (req != null) {
            friend_requestRepository.deleteById(req.getId());

            Friend friend1 = new Friend();
            friend1.setUser(user);
            friend1.setFriend(findUser);

            Friend friend2 = new Friend();
            friend2.setUser(findUser);
            friend2.setFriend(user);

            friendRepository.save(friend1);
            friendRepository.save(friend2);
            return friend1;
        }
        return null;
    }

    @Override
    public Friend_Request getRequestAddFriend(User user, User findUser) {
        Friend_Request req = friend_requestRepository.findBySenderIdAndReceiverId(user.getId(), findUser.getId());

        if (req != null ) {
            req.setSeen(true);
            return req;
        }
        return null;
    }

    @Override
    public List<Friend_Request> getAllRequestAddFriend(User user) {
        List<Friend_Request> list = friend_requestRepository.findByReceiverId(user.getId());
        return list;
    }

    @Override
    public List<Friend_Request> getAllSendRequestAddFriend(User user) {
        List<Friend_Request> list = friend_requestRepository.findBySenderId(user.getId());
        return list;
    }

}
