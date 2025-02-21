package com.nhom1.socialmedia.controller;

import com.nhom1.socialmedia.model.Posts;
import com.nhom1.socialmedia.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shares")
public class ShareController {

    @Autowired
    private ShareService shareService;

    //chia se bai viet
    @GetMapping("/{id}/share")
    public String sharePost(@PathVariable Long id){
        Optional<Posts> share = shareService.getPostById(id);
        if(share.isPresent()){
            String shareUrl = "http://localhost:8080/api/shares" + id;
            return "Chia sẻ bài viết: " + shareUrl;
        } else {
            return "Bài viết không tồn tại";
        }
    }
}
