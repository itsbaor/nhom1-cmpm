package com.nhom1.socialmedia.service.service;


import com.nhom1.socialmedia.model.dto.Requset.LoginRequestDto;
import com.nhom1.socialmedia.model.dto.Response.LoginResponseDto;
import org.springframework.http.ResponseEntity;

public interface LoginService {
    ResponseEntity<LoginResponseDto>login(LoginRequestDto dto);
}
