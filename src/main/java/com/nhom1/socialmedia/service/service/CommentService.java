package com.nhom1.socialmedia.service.service;

import com.nhom1.socialmedia.model.dto.Requset.CommentRequestDto;
import com.nhom1.socialmedia.model.dto.Response.CommentResponseDto;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<?> creat (CommentRequestDto dto);
    ResponseEntity<?> delete(Long id);
    ResponseEntity<?> update(CommentResponseDto dto,Long id);
    ResponseEntity<?> getlist(Long id);
}
