package com.nhom1.socialmedia.model.dto.Requset;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String content;
    private String image;
    private Long parent_comment_id;
    private Long userid;
    private Long postsId;

}
