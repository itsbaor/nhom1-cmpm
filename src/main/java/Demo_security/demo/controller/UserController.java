package Demo_security.demo.controller;

import Demo_security.demo.dto.ApiResponse;
import Demo_security.demo.dto.UserDto;
import Demo_security.demo.dto.UserUpdateDto;
import Demo_security.demo.entity.User;
import Demo_security.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping
    ApiResponse<User> createUSer(@RequestBody @Valid UserDto dto){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUser(dto));
        return apiResponse;
    }

    @GetMapping
    List<User> getUSers(){
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    User getUser(@PathVariable("userId") Long userId){
        return  userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable @Valid Long userId,
                           @RequestBody UserUpdateDto dto) {
        return userService.updateUser(userId, dto);
    }

    @DeleteMapping("/{id}")
    String deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return "USer has been deleted";
    }
}