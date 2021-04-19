package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.PostEntity;
import com.backend.fakedb.services.PostService;
import com.backend.fakedb.services.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

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

    @GetMapping(path = "/getById")
    public Optional<PostEntity> getById(@RequestParam(name = "id", required = true) Integer id) {
        return postService.getById(id);
    }

    @GetMapping(path = "/getInterval")
    public List<PostEntity> getByInterval(@RequestParam(name = "skip", required = true) Integer skip,
                                          @RequestParam(name = "count", required = true) Integer count) {
        return postService.getInterval(skip, count);
    }

    @PostMapping(path = "/add")
    public void add(@RequestBody PostEntity postEntity) {
        postService.add(postEntity);
    }

    @PostMapping(path = "/dummy")
    public void dummy() {
        for (int i = 1; i < 10; i++) {
            postService.add(new PostEntity(i, "thumbnail nice " + i, "title cool " + i * i, "description hello " + i, new Date(100L),  i * 10, "www.url.ro"));
        }
    }

    @DeleteMapping(path = "/delete")
    public void delete(@RequestParam(name = "id", required = true) Integer id) {
        postService.delete(id);
    }
}