package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.exception.UserException;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

}
