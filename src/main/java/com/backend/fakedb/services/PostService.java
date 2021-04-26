package com.backend.fakedb.services;

import com.backend.fakedb.entities.PostEntity;
import com.backend.fakedb.repositories.PostRepository;
import com.backend.fakedb.utilities.IngestionLinker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    // Left for compatibility reasons
    private final PostRepository postRepository;
    private final IngestionLinker ingestionLinker;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
        ingestionLinker = new IngestionLinker();
    }

    /**
     * Public method for returning all posts from the repo.
     * @return a list of PostEntity objects
     * @deprecated Method no longer needed, used only for fake data from the repo.
     */
    @Deprecated
    public List<PostEntity> getAll() {
        return postRepository.findAll();
    }

    /**
     * Public method for returning a post by an ID (from the repo).
     * @param id the ID of the post to search for
     * @return the post if found, null otherwise
     * @deprecated Method no longer needed, used only for fake data from the repo.
     */
    @Deprecated
    public PostEntity getById(Integer id) {
        Optional<PostEntity> postEntity = postRepository.findById(id);
        if (postEntity.isEmpty()) {
            return null;
        }
        return postEntity.get();
    }

    /**
     * Public method for getting a list of posts from the ingestion system.
     * If the given parameters exceed the database number of existing rows, null objects will be added to the list.
     * @param s the amount of rows to skip (must be a number equal or greater than zero)
     * @param c the number of posts to return (must be a number greater than zero)
     * @return a list of size 'c' of PostEntity objects
     */
    public List<PostEntity> getInterval(int s, int c) {
        return ingestionLinker.getInterval(s, c);
    }

    /**
     * Public method which returns a list of post from a specific provider.
     * If the given parameters exceed the database number of existing rows, null objects will be added to the list.
     * @param provider_id the provider from which to get the posts
     * @param s the amount of rows to skip (must be a number equal or greater than zero)
     * @param c the number of posts to return (must be a number greater than zero)
     * @return a list containing the requested elements.
     */
    public List<PostEntity> getIntervalByProvider(int provider_id, int s, int c) {
        return ingestionLinker.getIntervalByProvider(provider_id, s, c);
//        Optional<PostEntity> optionalListById = postRepository.findById(provider_id);
//
//        if (optionalListById.isEmpty())
//            return null;
//
//        List<PostEntity> listById = optionalListById.stream().collect(Collectors.toList());
//
//        // The request skips all data
//        if (listById.size() < s) {
//            return null;
//        }
//
//        List<PostEntity> listToReturn = new ArrayList<>(c);
//        for (int i = s; i < s + c; i++) {
//            listToReturn.add(listById.get(i));
//        }
//        return listToReturn;
    }

    /**
     * Public method for adding a post into the repo
     * @param postEntity the post to be added
     * @deprecated Method no longer needed, used only for fake data.
     */
    @Deprecated
    public void add(PostEntity postEntity) {
        postRepository.save(postEntity);
    }

    /**
     * Public method for deleting a post from the repo
     * @param id ID of the post to be deleted
     * @deprecated Method no longer needed, used only for fake data.
     */
    @Deprecated
    public void delete(Integer id) {
        postRepository.deleteById(id);
    }

}
