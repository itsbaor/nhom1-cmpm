package com.nhom1.socialmedia.service;


import com.nhom1.socialmedia.exception.AppException;
import com.nhom1.socialmedia.exception.ErrorCode;
import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.model.dto.Requset.UserDto;
import com.nhom1.socialmedia.repository.UserRepository;
import com.nhom1.socialmedia.service.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceIpml implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceIpml.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> create(UserDto dto) {
        // Kiểm tra email đã tồn tại chưa
        Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser.isPresent()) {
            logger.error("Email already exists: " + dto.getEmail());
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Mã hóa mật khẩu trước khi lưu
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        User user = new User();
        user.setBackgroundImage(dto.getBackgroundImage());
        user.setBio(dto.getBio());
        user.setDateOfBirth(dto.getDateOfBirth());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setImage(dto.getImage());
        user.setLastName(dto.getLastName());
        user.setLocation(dto.getLocation());
        user.setNumberPhone(dto.getNumberPhone());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setReq_user(dto.isReq_user());
        user.setStatus(dto.getStatus());


        try {
            userRepository.save(user);
            logger.info("User created successfully: " + dto.getEmail());
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            logger.error("Error creating user: " + e.getMessage());
            throw new AppException(ErrorCode.UNCATEGORYZED_EXCEPTION);
        }
    }
}
