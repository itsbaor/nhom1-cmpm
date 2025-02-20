package com.nhom1.socialmedia.model.dto.Response;

import com.nhom1.socialmedia.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter

public class LoginResponseDto {
    private boolean success;
    private String message;
    private Long id;
    private String fullname;
    private String email;
    private String bio;

    public LoginResponseDto(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        if (user != null) {
            this.id = user.getId();
            this.email = user.getEmail();
            this.email = user.getEmail();
            this.bio = user.getBio();
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
