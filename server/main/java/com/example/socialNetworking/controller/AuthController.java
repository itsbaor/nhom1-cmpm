package com.example.socialNetworking.controller;

import com.example.socialNetworking.config.jwt.JwtUtils;
import com.example.socialNetworking.dto.UserDto;
import com.example.socialNetworking.dto.mapper.UserMapper;
import com.example.socialNetworking.exception.RefreshTokenException;
import com.example.socialNetworking.exception.UserException;
import com.example.socialNetworking.model.RefreshToken;
import com.example.socialNetworking.model.User;
import com.example.socialNetworking.model.enumType.Status_User;
import com.example.socialNetworking.repository.UserRepository;
import com.example.socialNetworking.request.LoginRequest;
import com.example.socialNetworking.service.Impl.CustomUserDetailsService;
import com.example.socialNetworking.service.RefreshTokenService;
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
import org.springframework.web.bind.annotation.*;

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
    private final RefreshTokenService refreshTokenService;
    private final CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        //Tìm người dùng thông qua email
        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser != null){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists with another account");
        }
        //Mã hóa mật khẩu trước khi lưu vào csdl
        String password = user.getPassword();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setStatus(Status_User.OFFLINE);
        UserDto userDto = UserMapper.Instance.userToUserDto(userService.saveUser(user));

        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        try {
            // Kiểm tra xem người dùng có tồn tại không
            User findUser = userService.getUserByEmail(loginRequest.getEmail());

            // Xác thực người dùng
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            // Tạo access token
            String accessToken = jwtUtils.generateAccessToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tạo refresh token
            RefreshToken refreshToken = refreshTokenService.createRefreshToken(loginRequest.getEmail());

            // Trả về access token trong response body
            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", accessToken);

            // Tạo cookie chứa refresh token
            Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken.getToken());
            refreshTokenCookie.setHttpOnly(true);
//            refreshTokenCookie.setSecure(true); // Chỉ hoạt động trên HTTPS
            refreshTokenCookie.setPath("/"); // Áp dụng cho toàn bộ domain
            refreshTokenCookie.setMaxAge(7 * 24 * 60 * 60); // 7 ngày
            response.addCookie(refreshTokenCookie);

            return new ResponseEntity<>(tokens, HttpStatus.OK);
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

    @GetMapping("/logout")
    public ResponseEntity<?> logout(@CookieValue(value = "refreshToken", required = false) String refreshToken, HttpServletResponse response) {
        refreshTokenService.deleteByToken(refreshToken);

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(0); // Xóa ngay lập tức
        response.addCookie(refreshTokenCookie);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken( @CookieValue(name = "refreshToken", required = false) String refreshToken){
        try{
            if(!refreshTokenService.isValid(refreshToken)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your session has expired");
            }
            User user = refreshTokenService.findUserByRefreshToken(refreshToken);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getEmail());

            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),null,userDetails.getAuthorities());
            String newAccessToken = jwtUtils.generateAccessToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            Map<String, String> tokens = new HashMap<>();
            tokens.put("accessToken", newAccessToken);

            return new ResponseEntity<>(tokens, HttpStatus.OK);
        }catch (UserException | RefreshTokenException ex){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
