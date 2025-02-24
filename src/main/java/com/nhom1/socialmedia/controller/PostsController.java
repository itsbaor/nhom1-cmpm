package com.nhom1.socialmedia.controller;

import com.nhom1.socialmedia.model.dto.Requset.PostRequestDto;
import com.nhom1.socialmedia.service.service.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostsController {

    @Autowired
    PostsService postsService;

    @PostMapping("/creat")
    public ResponseEntity<?> posts(@RequestBody PostRequestDto dto){
        return postsService.posts(dto);
    }

    @PostMapping("/delete")
    public ResponseEntity<?>delete(@RequestParam Long id){
        return  postsService.delete(id);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody PostRequestDto dto,
                                    @RequestParam Long id){
        return postsService.update(dto,id);
    }

    @GetMapping("/getlist")
    public Object getlist(@RequestParam(required = false) Long id,
                          @RequestParam(defaultValue = "1",required = false) int pageIdx,
                          @RequestParam(defaultValue = "5",required = false) int pageSize,
                          @RequestParam(required = false) String content){
        return postsService.getlist(id,pageIdx -1,pageSize,content);
    }
}
