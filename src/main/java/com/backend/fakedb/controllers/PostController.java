package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.PostEntity;
import com.backend.fakedb.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = "/getInterval")
    public List<PostEntity> getInterval(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "skip", required = true) Integer skip,
            @RequestParam(name = "count", required = true) Integer count) {
        return postService.getInterval(auth_id, token, skip, count);
    }

    @GetMapping(path = "/getIntervalByProvider")
    public List<PostEntity> getIntervalByProvider(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "provider_id", required = true) Integer provider_id,
            @RequestParam(name = "skip", required = true) Integer skip,
            @RequestParam(name = "count", required = true) Integer count) {
        return postService.getIntervalByProvider(auth_id, token, provider_id, skip, count);
    }
}
