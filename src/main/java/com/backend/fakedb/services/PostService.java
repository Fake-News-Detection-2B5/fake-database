package com.backend.fakedb.services;

import com.backend.fakedb.entities.PostEntity;
import com.backend.fakedb.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostEntity> getAll() {
        return postRepository.findAll();
    }

    public Optional<PostEntity> getById(Integer id) {
        return postRepository.findById(id);
    }

    public List<PostEntity> getInterval(int s, int c) {
        List<PostEntity> listAll = getAll();

        // The request skips all data
        if (listAll.size() < s) {
            return null;
        }

        List<PostEntity> listToReturn = new ArrayList<>(c);
        for (int i = s; i < s + c; i++) {
            listToReturn.add(listAll.get(i));
        }
        return listToReturn;
    }

    public void add(PostEntity postEntity) {
        postRepository.save(postEntity);
    }

    public void delete(Integer id) {
        postRepository.deleteById(id);
    }

    public List<PostEntity> getIntervalByProvider(int provider_id, int s, int c) {
        Optional<PostEntity> optionalListById = getById(provider_id);

        if (optionalListById.isEmpty())
            return null;

        List<PostEntity> listById = optionalListById.stream().collect(Collectors.toList());

        // The request skips all data
        if (listById.size() < s) {
            return null;
        }

        List<PostEntity> listToReturn = new ArrayList<>(c);
        for (int i = s; i < s + c; i++) {
            listToReturn.add(listById.get(i));
        }
        return listToReturn;
    }

    // get la ingestion service (ala de e deployed pe heroku DE PE PARTEA LOR: heroku.ingestion/get

}
