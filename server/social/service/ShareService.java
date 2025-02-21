package com.social.social.service;

import com.social.social.entity.Share;
import com.social.social.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShareService {
    @Autowired
    private ShareRepository shareRepository;

    //lay bai viet theo ID
    public Optional<Share> getShareById(Long id){
        return shareRepository.findById(id);
    }
}
