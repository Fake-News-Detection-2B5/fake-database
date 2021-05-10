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

    /**
     * Public method for getting all posts from the ingestion system
     * @return a list containing all the posts
     * @deprecated Method no longer needed, used only for fake data.
     */
    @Deprecated
    @GetMapping(path = "/public/getAll")
    public List<PostEntity> getAll() {
        return postService.getAll();
    }

    /**
     * Public method for getting (from the repo) a specific post based on an ID.
     * @param id the id of the post
     * @return the post if it is found, null otherwise.
     * @deprecated Method no longer needed
     */
    @Deprecated
    @GetMapping(path = "/private/getById")
    public PostEntity getById(@RequestParam(name = "id", required = true) Integer id) {
        return postService.getById(id);
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

    @PostMapping(path = "/add")
    public void add(@RequestBody PostEntity postEntity) {
        postService.add(postEntity);
    }

    @PostMapping(path = "/dummy")
    public void dummy() {
        for (int i = 1; i < 10; i++) {
            postService.add(new PostEntity(i, "thumbnail nice " + i, "title cool " + i * i, "description hello " + i, new Date(100L),  "true", "www.url.ro"));
        }
    }

    @DeleteMapping(path = "/delete")
    public void delete(@RequestParam(name = "id", required = true) Integer id) {
        postService.delete(id);
    }
}
