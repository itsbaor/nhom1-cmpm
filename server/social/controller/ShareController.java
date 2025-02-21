package com.social.social.controller;

import com.social.social.entity.Share;
import com.social.social.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Optional<Share> share = shareService.getShareById(id);
        if(share.isPresent()){
            String shareUrl = "http://localhost:8080/api/shares" + id;
            return "Chia sẻ bài viết: " + shareUrl;
        } else {
            return "Bài viết không tồn tại";
        }
    }
}
