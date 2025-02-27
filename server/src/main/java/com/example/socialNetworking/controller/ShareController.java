package com.nhom1.socialmedia.controller;

import com.nhom1.socialmedia.model.Posts;
import com.nhom1.socialmedia.model.User;
import com.nhom1.socialmedia.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("share")
public class ShareController {

    @Autowired
    private ShareService shareService;
    //lay danh sach nguoi dung chia se bai viet
    @GetMapping("/{postId}/share-users")
    public ResponseEntity<List<User>> getShareUsersByPostId(@PathVariable long postId) {
        List<User> shareUsers = shareService.getShareUsersByPostId(postId);
        return ResponseEntity.ok(shareUsers);
    }
    //chia se bai viet
    @PostMapping("/{postId}/share/{userId}")
    public ResponseEntity<?> sharePost(@PathVariable long postId, @PathVariable long userId) {
        shareService.sharePost(postId, userId);
        return ResponseEntity.ok().build();
    }
}
