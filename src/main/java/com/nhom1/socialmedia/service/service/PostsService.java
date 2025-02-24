package com.nhom1.socialmedia.service.service;

import com.nhom1.socialmedia.model.dto.Requset.PostRequestDto;
import org.springframework.http.ResponseEntity;

public interface PostsService {
    ResponseEntity<?> posts (PostRequestDto dto);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> update(PostRequestDto dto,Long id);
    Object getlist(Long id, int pageIdx , int pageSize, String content);
}
