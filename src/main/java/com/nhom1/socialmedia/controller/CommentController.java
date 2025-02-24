package com.nhom1.socialmedia.controller;

import com.nhom1.socialmedia.model.dto.Requset.CommentRequestDto;
import com.nhom1.socialmedia.model.dto.Response.CommentResponseDto;
import com.nhom1.socialmedia.service.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> creat(@RequestBody CommentRequestDto dto){
        return commentService.creat(dto);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id){
        return commentService.delete(id);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody CommentResponseDto dto,
                                    Long id){
        return  commentService.update(dto,id);
    }

    @GetMapping("/getlist")
    public ResponseEntity<?> getlist(@RequestParam(required = false) Long id
//                                     @RequestParam(defaultValue = "1",required = false) int pageIdx,
//                                     @RequestParam(defaultValue = "5",required = false) int pageSize
                                     ){
        return commentService.getlist(id);
    }
}
