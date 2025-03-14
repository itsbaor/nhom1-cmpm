package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.UserDto;
import com.example.socialNetworking.dto.mapper.UserMapper;
import com.example.socialNetworking.exception.UserException;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.model.enumType.Status_User;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.request.LoginRequest;
import com.example.socialNetworking.service.Impl.CustomUserDetailsService;
import com.example.socialNetworking.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        //Tìm người dùng thông qua email
        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Amin already exists with another account");
        }
        //Mã hóa mật khẩu trước khi lưu vào csdl
        String password = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setStatus(Status_User.OFFLINE);
        UserDto userDto = UserMapper.Instance.userToUserDto(userService.saveUser(user));

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        try {
            // Kiểm tra xem người dùng có tồn tại không
            User findUser = userService.getUserByEmail(loginRequest.getEmail());

            // Xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            // Tạo access token
            String accessToken = jwtUtils.generateAccessToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return new ResponseEntity<>(accessToken, HttpStatus.OK);
        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This account does not exist");
        } catch (BadCredentialsException e) {
            // Bắt lỗi khi mật khẩu không đúng
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
        } catch (Exception e) {
            // Xử lý các lỗi khác
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }
}

