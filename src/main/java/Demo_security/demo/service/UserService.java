package Demo_security.demo.service;

import Demo_security.demo.dto.UserDto;
import Demo_security.demo.dto.UserUpdateDto;
import Demo_security.demo.entity.User;
import Demo_security.demo.exception.AppException;
import Demo_security.demo.exception.ErrorCode;
import Demo_security.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDto dto) {

        // Kiểm tra nếu username đã tồn tại
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);  // Ném lỗi nếu username đã tồn tại
        }

        // Kiểm tra trường hợp null cho các trường
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new AppException(ErrorCode.USERNAME_INVALID);  // Ném lỗi nếu username là null hoặc rỗng
        }

        if (dto.getPassword() == null || dto.getPassword().isEmpty()){
            throw new AppException(ErrorCode.PASSWORD_INVALID);
        }
        User user = new User();

        user.setFirstname(user.getFirstname());
        user.setLastname(user.getLastname());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setDateOfBirth(user.getDateOfBirth());
        user.setNumberPhone(user.getNumberPhone());
        user.setBio(user.getBio());

        //ma hoa password
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // Lưu vào cơ sở dữ liệu
        return userRepository.save(user);

    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public User getUser(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not font"));
    }

    public User updateUser(Long userId,UserUpdateDto dto){
        User user = getUser(userId);

        user.setFirstname(dto.getFirtname());
        user.setLastname(dto.getLastname());
        user.setPassword(dto.getPassword());

        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }


}