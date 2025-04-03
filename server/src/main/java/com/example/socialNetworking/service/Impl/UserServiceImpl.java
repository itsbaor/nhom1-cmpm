package com.example.socialNetworking.service.Impl;

import com.example.socialNetworking.exception.UserException;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.model.enumType.Status_User;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

//    private final UserRepository userRepository;
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
    public User updateUser(Long userId,User req) {
        User user = findById(userId);

        if(req.getFullName() != null && !req.getFullName().trim().isEmpty()) user.setFullName(req.getFullName());
        if(req.getImage() != null && !req.getImage().trim().isEmpty()) user.setImage(req.getImage());
        if(req.getBackgroundImage() != null && !req.getBackgroundImage().trim().isEmpty()) user.setBackgroundImage(req.getBackgroundImage());
        if(req.getDateOfBirth() != null) user.setDateOfBirth(req.getDateOfBirth()); // không cần kiểm tra isEmpty với Date
        if(req.getLocation() != null && !req.getLocation().trim().isEmpty()) user.setLocation(req.getLocation());
        if(req.getBio() != null && !req.getBio().trim().isEmpty()) user.setBio(req.getBio());
        if(req.getNumberPhone() != null && !req.getNumberPhone().trim().isEmpty()) user.setNumberPhone(req.getNumberPhone());

        return userRepository.save(user);
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
    public List<User> getUsersByIds(Set<Long> onlineUsers) {
        List<User> users = new ArrayList<>();
        for(Long id : onlineUsers){
            User user = findById(id);
            users.add(user);
        }
        return users;
    }


    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
