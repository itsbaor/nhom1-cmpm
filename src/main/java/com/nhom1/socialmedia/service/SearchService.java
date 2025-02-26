package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.Posts;
import com.nhom1.socialmedia.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;

    public List<Posts> searchPosts(String keyword) {
        return searchRepository.searchPosts(keyword);
    }
}
