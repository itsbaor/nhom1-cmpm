package com.nhom1.socialmedia.model.dto.Requset;

import lombok.Data;

@Data
public class PostRequestDto {
    private boolean success;
    private String content;
    private String image;

    public PostRequestDto(String content, String image, Long id) {
        this.content = content;
        this.image = image;
    }


}
