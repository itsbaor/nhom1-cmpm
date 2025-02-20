package com.nhom1.socialmedia.service.service;

import com.nhom1.socialmedia.model.dto.Requset.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> create(UserDto dto);
}
