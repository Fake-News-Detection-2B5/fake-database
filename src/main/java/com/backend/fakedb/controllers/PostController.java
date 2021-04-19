package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.PostEntity;
import com.backend.fakedb.services.PostService;
import com.backend.fakedb.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "post")
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(path = "/getAll")
    public List<PostEntity> getAll() {
        return postService.getAll();
    }

    
}
