package com.nhom1.socialmedia.service;

import com.nhom1.socialmedia.model.Posts;
import com.nhom1.socialmedia.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShareService {
    @Autowired
    private ShareRepository shareRepository;

    //lay id theo bai viet
    public Optional<Posts> getPostById(Long id){
return shareRepository.findById(id);
    }
}
