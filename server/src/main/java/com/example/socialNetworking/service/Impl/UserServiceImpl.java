package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.exception.UserException;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.model.enumType.Status_User;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserException("not found user");
        }
        return user;
    }

    @Override
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() ->  new UserException("not found user"));
    }

    @Override
    public List<User> findListUserUnlessOwn(String email) {
        return userRepository.findAllUsersExceptEmail(email);
    }

    @Override
    public User updateStatusUser(Long id) {
        User user = findById(id);
        if(user.getStatus() == Status_User.ONLINE){
            user.setStatus(Status_User.OFFLINE);
        }else {
            user.setStatus(Status_User.ONLINE);
        }
        return userRepository.save(user);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
