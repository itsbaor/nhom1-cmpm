package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.exception.UserException;
import com.example.socialNetworking.model.HiddenUsers;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.HiddenUsersRepository;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.service.HiddenUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HiddenUsersServiceImpl implements HiddenUsersService {

    private final UserRepository userRepository;
    private final HiddenUsersRepository hiddenUsersRepository;

    @Override
    public void createHiddenUser(Long userId, User userReq) {
        User hiddenUser = userRepository.findById(userId).orElseThrow(() -> new UserException("not found user"));

        HiddenUsers hiddenUsers = new HiddenUsers();
        hiddenUsers.setUser(userReq);
        hiddenUsers.setHiddenUser(hiddenUser);
        hiddenUsers.setCreatedAt(LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh")));
        hiddenUsersRepository.save(hiddenUsers);
    }

    @Override
    public List<User> findAllBlockUser(User user) {
        List<HiddenUsers> hiddenUsersList = hiddenUsersRepository.findHiddenUsersByUser(user);
        List<User> userList = hiddenUsersList.stream().map(HiddenUsers::getHiddenUser).toList();

        return userList;
    }

    @Override
    public User removeBlockUser(Long userId) {
        HiddenUsers findHiddenUser = hiddenUsersRepository.findHiddenUsersByHiddenUser_Id(userId);
        return findHiddenUser.getHiddenUser();
    }
}
