package com.nhom1.socialmedia.controller;


import com.nhom1.socialmedia.model.dto.Requset.LoginRequestDto;
import com.nhom1.socialmedia.model.dto.Response.LoginResponseDto;
import com.nhom1.socialmedia.service.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/loginuser")
public class LoginController {

@Autowired
LoginService loginService;

@PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto){
        return loginService.login(dto);
    }
}
