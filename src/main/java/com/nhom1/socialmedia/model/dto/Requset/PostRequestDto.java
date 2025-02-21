package com.nhom1.socialmedia.model.dto.Requset;

import lombok.Data;

@Data
public class PostRequestDto {
    private boolean success;
    private String message;
    private String content;
    private String image;

    public PostRequestDto(boolean content, String image, Long id) {
        this.content = String.valueOf(content);
        this.image = image;
    }


}
