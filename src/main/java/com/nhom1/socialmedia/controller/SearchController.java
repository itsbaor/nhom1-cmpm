package com.nhom1.socialmedia.controller;

import com.nhom1.socialmedia.model.Posts;
import com.nhom1.socialmedia.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public List<Posts> searchPosts(@RequestParam String keyword) {
        return searchService.searchPosts(keyword);

    }

}


