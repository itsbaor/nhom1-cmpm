package com.nhom1.socialmedia.model.dto.Response;

import com.nhom1.socialmedia.model.Posts;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.ContentHandler;
import java.util.HashMap;
import java.util.Map;

public class ResponseHelper{
    public static ResponseEntity<Object> getResponses(Object obj, long totalElements, Integer totalPage, HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("totalElements",totalElements);
        map.put("totalPage",totalPage);
        map.put("Status", 0 );
        map.put("Errors", "{ code:  " + 0 +", message: SUCCESS }");
        map.put("isOk", true);
        map.put("isError", false);
        map.put("Object", obj);
        return new ResponseEntity<Object>(map, status);
    }

    public static ResponseEntity<Object> getResponse(Object obj,Integer totalPage,HttpStatus status) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("totalPage",totalPage);
        map.put("Status", 0 );
        map.put("Errors", "{ code:  " + 0 +", message: SUCCESS }");
        map.put("isOk", true);
        map.put("isError", false);
        map.put("Object", obj);
        return new ResponseEntity<Object>(map, status);
    }
}