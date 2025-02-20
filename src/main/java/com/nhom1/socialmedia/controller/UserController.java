package com.nhom1.socialmedia.controller;



import com.nhom1.socialmedia.model.dto.Requset.UserDto;
import com.nhom1.socialmedia.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
   @Autowired
   UserService userService;

    @PostMapping("/created")
    public ResponseEntity<?> create(@RequestBody UserDto dto){
        return userService.create(dto);
    }

}