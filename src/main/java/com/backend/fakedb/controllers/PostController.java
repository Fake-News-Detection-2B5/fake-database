package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.PostEntity;
import com.backend.fakedb.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
    public List<PostEntity> getInterval(@RequestParam(name = "skip", required = true) Integer skip,
                                          @RequestParam(name = "count", required = true) Integer count) {
        return postService.getInterval(skip, count);
    }

    @GetMapping(path = "/getIntervalByProvider")
    public List<PostEntity> getIntervalByProvider(@RequestParam(name = "provider_id", required = true) Integer provider_id,
                                                  @RequestParam(name = "skip", required = true) Integer skip,
                                                  @RequestParam(name = "count", required = true) Integer count) {
        return postService.getIntervalByProvider(provider_id, skip, count);
    }
}
