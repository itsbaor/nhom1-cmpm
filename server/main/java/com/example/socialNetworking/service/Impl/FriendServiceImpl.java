package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.model.Friend;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.FriendRepository;
import com.example.socialNetworking.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;

    @Override
    public Friend getFriend(User user, User findUser) {
        return friendRepository.findByUserIdAndFriendId(user.getId(), findUser.getId());
    }

    @Override
    @Transactional
    public Friend unfriend(User user, User findUser) {
        Friend findFriend1 = friendRepository.findByUserIdAndFriendId(user.getId(), findUser.getId());
        Friend findFriend2 = friendRepository.findByUserIdAndFriendId(findUser.getId(), user.getId());
        friendRepository.deleteById(findFriend1.getId());
        friendRepository.deleteById(findFriend2.getId());
        return findFriend1;
    }

    @Override
    public Long countFriends(User user) {
        return friendRepository.countByUserId(user.getId());
    }

    @Override
    public List<Friend> getAllFriend(Long userId) {
        return friendRepository.findByUserId(userId);
    }
}
