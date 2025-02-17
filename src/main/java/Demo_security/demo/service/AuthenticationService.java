//package Demo_security.demo.service;
//
//import Demo_security.demo.dto.AuthenticationDto;
//import Demo_security.demo.exception.AppException;
//import Demo_security.demo.exception.ErrorCode;
//import Demo_security.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class AuthenticationService {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    public AuthenticationService() {
//        this.passwordEncoder = new BCryptPasswordEncoder(10);  // Cấu hình độ mạnh của mã hóa
//    }
//
//    public boolean authenticate(AuthenticationDto dto) {
//        // Tìm người dùng theo username
//        var user = userRepository.findByUsername(dto.getUsername())
//                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));
//
//        // So sánh mật khẩu người dùng nhập vào với mật khẩu đã mã hóa trong cơ sở dữ liệu
//        boolean isPasswordValid = passwordEncoder.matches(dto.getPassword(), user.getPassword());
//
//        // Trả về kết quả xác thực
//        return isPasswordValid;
//    }
//}
//
package Demo_security.demo.service;

import Demo_security.demo.dto.AuthenticationDto;
import Demo_security.demo.exception.AppException;
import Demo_security.demo.exception.ErrorCode;
import Demo_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(AuthenticationDto dto) {
        // Lấy người dùng từ cơ sở dữ liệu theo tên đăng nhập
        var user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST));

        // So sánh mật khẩu mã hóa trong cơ sở dữ liệu với mật khẩu người dùng nhập
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        // Trả về kết quả xác thực
        return passwordEncoder.matches(dto.getPassword(), user.getPassword());
    }
}
