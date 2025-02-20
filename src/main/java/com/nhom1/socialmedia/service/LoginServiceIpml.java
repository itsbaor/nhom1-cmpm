package com.nhom1.socialmedia.service;


import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.model.dto.Requset.LoginRequestDto;
import com.nhom1.socialmedia.model.dto.Response.LoginResponseDto;
import com.nhom1.socialmedia.repository.LoginRepository;
import com.nhom1.socialmedia.service.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceIpml implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceIpml.class);

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    UserServiceIpml userServiceIpml;

    @Override
    public ResponseEntity<LoginResponseDto>login(LoginRequestDto dto){
        //Kiem tra email co ton tai hay khong
        Optional<User> user = loginRepository.findByEmail(dto.getEmail());

        if(user.isEmpty()){
            logger.error("Email không tồn tại");
            return ResponseEntity.badRequest().body(new LoginResponseDto(false, "Email không chính xác", null));
        }

        //Kiem tra mat khau co trung khop khong
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean isMatch = passwordEncoder.matches(dto.getPassword(), user.get().getPassword());

        if (!isMatch) {
            logger.error("Mật khẩu không chính xác");
            return ResponseEntity.badRequest().body(new LoginResponseDto(false, "Mật khẩu không chính xác", null));
        }
        return ResponseEntity.ok(new LoginResponseDto(true, "Login successful", (User) user.get()));
    }
}
