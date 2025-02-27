package com.nhom1.socialmedia.controller;

import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.service.SavePostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class SavePostController {
    @Autowired
    private SavePostService savePostService;

    //Lưu bài viết vào bookmark
    @PostMapping("/{postId}/bookmark/userId")
//    @PathVariable biến đường dẫn
    public ResponseEntity<?> bookmarkPost(@PathVariable Long postId, @PathVariable Long userId) {
        savePostService.bookmarkPost(postId, userId);
        return ResponseEntity.ok().build();
    }

    //Xóa bài viết khỏi danh sach bookmark
    @DeleteMapping("/{postId}/bookmark/{userId}")
    public ResponseEntity<?> unbookmarkPost(@PathVariable Long postId, @PathVariable Long userId) {
        savePostService.unbookmarkPost(postId, userId);
        return ResponseEntity.ok().build();
    }

    //lấy danh sách người dùng đã lưu
    @GetMapping("/{postId}/bookmark-users")
    public ResponseEntity<List<User>> getBookmarkUsers(@PathVariable Long postId) {
        List<User> bookmarkUsers = savePostService.getBookmarkUsers(postId);
        return ResponseEntity.ok(bookmarkUsers);}
}