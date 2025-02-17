package Demo_security.demo.controller;

import Demo_security.demo.dto.ApiResponse;
import Demo_security.demo.dto.AuthenticationDto;
import Demo_security.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationDto> authenticate(@RequestBody AuthenticationDto dto) {
        // Gọi service để xác thực người dùng
        boolean result = authenticationService.authenticate(dto);

        // Tạo ApiResponse với kết quả xác thực
        return ApiResponse.<AuthenticationDto>builder()
                .result(AuthenticationDto.builder()
                        .username(dto.getUsername())  // Gửi lại tên người dùng
                        .authenticated(result)  // Trả về kết quả xác thực
                        .build())
                .build();
    }
}
